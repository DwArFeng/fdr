package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.fdr.impl.handler.Consumer;
import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.sdk.exception.HandlerExceptionHelper;
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
public abstract class KeepConsumer<D extends Data> implements Consumer<D> {

    private static final Logger LOGGER = LoggerFactory.getLogger(KeepConsumer.class);

    protected final KeepHandler<D> keepHandler;
    protected final PushHandler pushHandler;

    protected final CuratorFramework curatorFramework;

    protected final String latchPath;

    public KeepConsumer(
            KeepHandler<D> keepHandler, PushHandler pushHandler, CuratorFramework curatorFramework, String latchPath
    ) {
        this.keepHandler = keepHandler;
        this.pushHandler = pushHandler;
        this.curatorFramework = curatorFramework;
        this.latchPath = latchPath;
    }

    @Override
    public void consume(List<D> datas) throws HandlerException {
        try {
            internalConsume(datas);
        } catch (Exception e) {
            throw HandlerExceptionHelper.parse(e);
        }
    }

    private void internalConsume(List<D> datas) throws Exception {
        // 由于数据的保持操作与数据的处理顺序有关，因此使用分布式锁，保证同一时间内只有一个消费者在处理数据。
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, latchPath);

        // 数据去重。
        // 遍历所有的数据，按照 Record.getPointKey() 的值进行分组，每组数据只保留最后一条数据。
        Map<LongIdKey, D> localMap = datas.stream().collect(
                Collectors.toMap(Data::getPointKey, Function.identity(), (a, b) -> b)
        );

        // 分布式锁。
        lock.acquire();
        try {
            // 将 localMap 中的所有值转换为列表，并赋值给 datas。
            datas = new ArrayList<>(localMap.values());

            // 更新数据，并将更新成功的数据数据组成的列表赋值给 datas。
            datas = updateRecords(datas);
        } finally {
            lock.release();
        }

        // 推送数据。
        pushRecords(datas);
    }

    /**
     * 更新数据。
     *
     * @param datas 数据组成的列表。
     * @return 更新成功的数据组成的列表。
     */
    private List<D> updateRecords(List<D> datas) {
        // 优先尝试批量更新数据，如果批量更新失败，则尝试逐条更新数据。
        try {
            keepHandler.update(datas);
            return datas;
        } catch (Exception e) {
            LOGGER.error("数据更新失败, 试图使用不同的策略进行推送: 逐条更新", e);
        }

        // 定义列表，分别用于存放更新成功和更新失败的数据。
        List<D> successList = new ArrayList<>();
        List<D> failedList = new ArrayList<>();

        // 遍历 datas 中的所有数据，逐条更新数据。
        for (D record : datas) {
            try {
                keepHandler.update(record);
                successList.add(record);
            } catch (Exception e) {
                LOGGER.error("数据更新失败, 放弃对该数据的更新: {}", record, e);
                failedList.add(record);
            }
        }

        // 如果有更新失败的数据，则记录日志。
        if (!failedList.isEmpty()) {
            LOGGER.error("推送数据时发生异常, 最多 {} 个数据信息丢失", failedList.size());
            failedList.forEach(record -> LOGGER.debug(Objects.toString(record)));
        }

        // 返回更新成功的数据组成的列表。
        return successList;
    }

    /**
     * 推送数据。
     *
     * @param datas 数据组成的列表。
     */
    @SuppressWarnings("DuplicatedCode")
    private void pushRecords(List<D> datas) {
        // 优先尝试批量推送数据，如果批量推送失败，则尝试逐条推送数据。
        try {
            doPush(datas);
        } catch (Exception e) {
            LOGGER.error("数据推送失败, 试图使用不同的策略进行推送: 逐条推送", e);
        }

        // 定义列表，用于存放推送失败的数据。
        List<D> failedList = new ArrayList<>();

        // 遍历 datas 中的所有数据，逐条推送数据。
        for (D record : datas) {
            try {
                doPush(record);
            } catch (Exception e) {
                LOGGER.error("数据推送失败, 放弃对该数据的推送: {}", record, e);
                failedList.add(record);
            }
        }

        // 如果有推送失败的数据，则记录日志。
        if (!failedList.isEmpty()) {
            LOGGER.error("推送数据时发生异常, 最多 {} 个数据信息丢失", failedList.size());
            failedList.forEach(record -> LOGGER.debug(Objects.toString(record)));
        }
    }

    /**
     * 推送数据。
     *
     * @param datas 数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    protected abstract void doPush(List<D> datas) throws HandlerException;

    /**
     * 推送数据。
     *
     * @param data 数据。
     * @throws HandlerException 处理器异常。
     */
    protected abstract void doPush(D data) throws HandlerException;

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
