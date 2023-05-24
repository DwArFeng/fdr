package com.dwarfeng.fdr.impl.handler.bridge;

import com.dwarfeng.fdr.impl.handler.Bridge;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.structure.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Objects;

/**
 * 桥接器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractBridge implements Bridge {

    protected String bridgeType;

    public AbstractBridge() {
    }

    public AbstractBridge(String bridgeType) {
        this.bridgeType = bridgeType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(bridgeType, type);
    }

    @Override
    public <D extends Data> Keeper<D> getKeeper(Class<D> clazz) throws HandlerException {
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
            throw new HandlerException("未知的数据类型: " + clazz.getCanonicalName());
        }
    }

    protected Keeper<NormalData> getNormalDataKeeper() throws HandlerException {
        throw new HandlerException("不支持的项目: 一般数据保持器");
    }

    protected Keeper<FilteredData> getFilteredDataKeeper() throws HandlerException {
        throw new HandlerException("不支持的项目: 被过滤数据保持器");
    }

    protected Keeper<TriggeredData> getTriggeredDataKeeper() throws HandlerException {
        throw new HandlerException("不支持的项目: 被触发数据保持器");
    }

    @Override
    public <D extends Data> Persister<D> getPersister(Class<D> clazz) throws HandlerException {
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
            throw new HandlerException("未知的数据类型: " + clazz.getCanonicalName());
        }
    }

    protected Persister<NormalData> getNormalDataPersister() throws HandlerException {
        throw new HandlerException("不支持的项目: 一般数据持久器");
    }

    protected Persister<FilteredData> getFilteredDataPersister() throws HandlerException {
        throw new HandlerException("不支持的项目: 被过滤数据持久器");
    }

    protected Persister<TriggeredData> getTriggeredDataPersister() throws HandlerException {
        throw new HandlerException("不支持的项目: 被触发数据持久器");
    }
}
