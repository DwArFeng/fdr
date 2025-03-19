package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.fdr.impl.handler.Consumer;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.handler.PushHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 持久消费者的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class PersistConsumer<D extends Data> implements Consumer<D> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistConsumer.class);

    protected final PersistHandler<D> persistHandler;
    protected final PushHandler pushHandler;

    protected PersistConsumer(PersistHandler<D> persistHandler, PushHandler pushHandler) {
        this.persistHandler = persistHandler;
        this.pushHandler = pushHandler;
    }

    @Override
    public void consume(List<D> datas) throws HandlerException {
        // 记录数据，并将记录成功的数据组成的列表赋值给 datas。
        datas = recordRecords(datas);

        // 推送数据。
        pushRecords(datas);
    }

    /**
     * 记录数据。
     *
     * @param datas 数据组成的列表。
     * @return 记录成功的数据组成的列表。
     */
    protected List<D> recordRecords(List<D> datas) {
        // 优先尝试批量记录数据，如果批量记录失败，则尝试逐条记录数据。
        try {
            persistHandler.record(datas);
            return datas;
        } catch (HandlerException e) {
            LOGGER.error("数据记录失败, 试图使用不同的策略进行记录: 逐条记录", e);
        }

        // 定义列表，分别用于存放记录成功和记录失败的数据。
        List<D> successList = new ArrayList<>();
        List<D> failedList = new ArrayList<>();

        // 遍历 datas 中的所有数据，逐条记录数据。
        for (D data : datas) {
            try {
                persistHandler.record(data);
                successList.add(data);
            } catch (Exception e) {
                LOGGER.error("数据记录失败, 放弃对该数据的记录: {}", data, e);
                failedList.add(data);
            }
        }

        // 如果有记录失败的数据，则记录日志。
        if (!failedList.isEmpty()) {
            LOGGER.error("记录数据时发生异常, 最多 {} 个数据信息丢失", failedList.size());
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
        return "PersistConsumer{" +
                "persistHandler=" + persistHandler +
                ", pushHandler=" + pushHandler +
                '}';
    }
}
