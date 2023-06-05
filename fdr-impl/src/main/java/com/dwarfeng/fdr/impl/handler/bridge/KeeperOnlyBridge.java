package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Objects;

/**
 * 仅支持保持器的桥接器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class KeeperOnlyBridge extends AbstractBridge {

    public KeeperOnlyBridge() {
    }

    public KeeperOnlyBridge(String bridgeType) {
        super(bridgeType);
    }

    @Override
    public boolean supportKeeper() {
        return true;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public <D extends Data> Keeper<D> getKeeper(Class<D> clazz) throws HandlerException {
        try {
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
        } catch (Exception e) {
            throw new HandlerException(e);
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
        return false;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public <D extends Data> Persister<D> getPersister(Class<D> clazz) {
        throw new IllegalStateException("桥接器不支持持久器: " + bridgeType);
    }
}
