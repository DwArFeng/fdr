package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.fdr.impl.handler.Consumer;
import com.dwarfeng.fdr.sdk.util.CompareUtil;
import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 保持消费者的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class KeepConsumer<R extends Data> implements Consumer<R> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeepConsumer.class);

    protected final KeepHandler<R> keepHandler;
    protected final PushHandler pushHandler;

    protected final CuratorFramework curatorFramework;

    protected final String latchPath;

    public KeepConsumer(
            KeepHandler<R> keepHandler, PushHandler pushHandler, CuratorFramework curatorFramework, String latchPath
    ) {
        this.keepHandler = keepHandler;
        this.pushHandler = pushHandler;
        this.curatorFramework = curatorFramework;
        this.latchPath = latchPath;
    }

    @Override
    public void consume(List<R> records) throws HandlerException {
        try {
            internalConsume(records);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private void internalConsume(List<R> records) throws Exception {
        // 由于数据的保持操作与数据的处理顺序有关，因此使用分布式锁，保证同一时间内只有一个消费者在处理数据。
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, latchPath);

        // 遍历所有的记录，按照 Record.getPointKey() 的值进行分组，
        // 每组数据使用比较器 Comparators.RECORD_HAPPENED_DATE_COMPARATOR 进行比较，取最大的值。
        Map<LongIdKey, R> localMap = records.stream().collect(Collectors.toMap(
                Data::getPointKey,
                Function.identity(),
                (r1, r2) -> CompareUtil.DATA_HAPPENED_DATE_ASC_COMPARATOR.compare(r1, r2) > 0 ? r1 : r2
        ));

        // 分布式锁。
        lock.acquire();
        try {
            // 将 localMap 中的所有值转换为列表，并赋值给 records。
            records = new ArrayList<>(localMap.values());

            // 更新数据，并将更新成功的数据记录组成的列表赋值给 records。
            records = updateRecords(records);
        } finally {
            lock.release();
        }

        // 推送数据。
        pushRecords(records);
    }

    /**
     * 更新数据。
     *
     * @param records 数据记录组成的列表。
     * @return 更新成功的数据记录组成的列表。
     */
    private List<R> updateRecords(List<R> records) {
        // 优先尝试批量更新数据，如果批量更新失败，则尝试逐条更新数据。
        try {
            keepHandler.update(records);
            return records;
        } catch (Exception e) {
            LOGGER.error("数据更新失败, 试图使用不同的策略进行推送: 逐条更新", e);
        }

        // 定义列表，分别用于存放更新成功和更新失败的数据记录。
        List<R> successList = new ArrayList<>();
        List<R> failedList = new ArrayList<>();

        // 遍历 records 中的所有数据记录，逐条更新数据。
        for (R record : records) {
            try {
                keepHandler.update(record);
                successList.add(record);
            } catch (Exception e) {
                LOGGER.error("数据更新失败, 放弃对该数据的更新: " + record, e);
                failedList.add(record);
            }
        }

        // 如果有更新失败的数据记录，则记录日志。
        if (!failedList.isEmpty()) {
            LOGGER.error("推送数据时发生异常, 最多 " + failedList.size() + " 个数据信息丢失");
            failedList.forEach(record -> LOGGER.debug(Objects.toString(record)));
        }

        // 返回更新成功的数据记录组成的列表。
        return successList;
    }

    /**
     * 推送数据。
     *
     * @param records 数据记录组成的列表。
     */
    private void pushRecords(List<R> records) {
        // 优先尝试批量推送数据，如果批量推送失败，则尝试逐条推送数据。
        try {
            doPush(records);
        } catch (Exception e) {
            LOGGER.error("数据推送失败, 试图使用不同的策略进行推送: 逐条推送", e);
        }

        // 定义列表，用于存放推送失败的数据记录。
        List<R> failedList = new ArrayList<>();

        // 遍历 records 中的所有数据记录，逐条推送数据。
        for (R record : records) {
            try {
                doPush(record);
            } catch (Exception e) {
                LOGGER.error("数据推送失败, 放弃对该数据的推送: " + record, e);
                failedList.add(record);
            }
        }

        // 如果有推送失败的数据记录，则记录日志。
        if (!failedList.isEmpty()) {
            LOGGER.error("推送数据时发生异常, 最多 " + failedList.size() + " 个数据信息丢失");
            failedList.forEach(record -> LOGGER.debug(Objects.toString(record)));
        }
    }

    /**
     * 推送数据。
     *
     * @param records 数据记录组成的列表。
     * @throws HandlerException 处理器异常。
     */
    protected abstract void doPush(List<R> records) throws HandlerException;

    /**
     * 推送数据。
     *
     * @param record 数据记录。
     * @throws HandlerException 处理器异常。
     */
    protected abstract void doPush(R record) throws HandlerException;

    @Override
    public String toString() {
        return "KeepConsumer{" +
                "keepHandler=" + keepHandler +
                ", pushHandler=" + pushHandler +
                ", curatorFramework=" + curatorFramework +
                ", latchPath='" + latchPath + '\'' +
                '}';
    }
}
