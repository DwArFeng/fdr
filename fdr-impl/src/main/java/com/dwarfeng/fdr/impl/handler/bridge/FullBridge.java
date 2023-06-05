package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.struct.Data;

import java.util.Objects;

/**
 * 完整桥接器。
 *
 * <p>
 * 完整桥接器是指同时支持保持器和持久器的桥接器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class FullBridge extends AbstractBridge {

    public FullBridge() {
    }

    public FullBridge(String bridgeType) {
        super(bridgeType);
    }

    @Override
    public boolean supportKeeper() {
        return true;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public <D extends Data> Keeper<D> getKeeper(Class<D> clazz) {
        if (Objects.equals(clazz, NormalData.class)) {
            @SuppressWarnings("unchecked")
            Keeper<D> result = (Keeper<D>) getNormalDataKeeper();
            return result;
        } else if (Objects.equals(clazz, FilteredData.class)) {
            @SuppressWarnings("unchecked")
            Keeper<D> result = (Keeper<D>) getFilteredDataKeeper();
            return result;
        } else if (Objects.equals(clazz, TriggeredData.class)) {
            @SuppressWarnings("unchecked")
            Keeper<D> result = (Keeper<D>) getTriggeredDataKeeper();
            return result;
        } else {
            throw new IllegalStateException("未知的数据类型: " + clazz.getCanonicalName());
        }
    }

    /**
     * 获取一般数据的保持器。
     *
     * @return 一般数据的保持器。
     */
    protected abstract Keeper<NormalData> getNormalDataKeeper();

    /**
     * 获取被过滤数据的保持器。
     *
     * @return 被过滤数据的保持器。
     */
    protected abstract Keeper<FilteredData> getFilteredDataKeeper();

    /**
     * 获取被触发数据的保持器。
     *
     * @return 被触发数据的保持器。
     */
    protected abstract Keeper<TriggeredData> getTriggeredDataKeeper();

    @Override
    public boolean supportPersister() {
        return true;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public <D extends Data> Persister<D> getPersister(Class<D> clazz) {
        if (Objects.equals(clazz, NormalData.class)) {
            @SuppressWarnings("unchecked")
            Persister<D> result = (Persister<D>) getNormalDataPersister();
            return result;
        } else if (Objects.equals(clazz, FilteredData.class)) {
            @SuppressWarnings("unchecked")
            Persister<D> result = (Persister<D>) getFilteredDataPersister();
            return result;
        } else if (Objects.equals(clazz, TriggeredData.class)) {
            @SuppressWarnings("unchecked")
            Persister<D> result = (Persister<D>) getTriggeredDataPersister();
            return result;
        } else {
            throw new IllegalStateException("未知的数据类型: " + clazz.getCanonicalName());
        }
    }

    /**
     * 获取一般数据的持久器。
     *
     * @return 一般数据的持久器。
     */
    protected abstract Persister<NormalData> getNormalDataPersister();

    /**
     * 获取被过滤数据的持久器。
     *
     * @return 被过滤数据的持久器。
     */
    protected abstract Persister<FilteredData> getFilteredDataPersister();

    /**
     * 获取被触发数据的持久器。
     *
     * @return 被触发数据的持久器。
     */
    protected abstract Persister<TriggeredData> getTriggeredDataPersister();
}
