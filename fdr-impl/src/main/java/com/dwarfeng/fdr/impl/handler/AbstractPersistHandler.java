package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.LookupNotSupportedException;
import com.dwarfeng.fdr.stack.exception.NativeQueryNotSupportedException;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 持久处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractPersistHandler<D extends Data> implements PersistHandler<D> {

    protected final List<Bridge> bridges;

    protected Bridge.Persister<D> persister;

    protected AbstractPersistHandler(
            List<Bridge> bridges
    ) {
        this.bridges = bridges;
    }

    /**
     * 初始化持久器。
     *
     * <p>
     * 该方法会从持久器列表中找到对应类型的桥接。
     *
     * <p>
     * 需要在子类构造完毕后调用该方法。
     */
    protected void init(String bridgeType) throws Exception {
        // 从持久器列表中找到对应类型的持久器。
        Bridge bridge = bridges.stream().filter(b -> b.supportType(bridgeType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 bridge 类型: " + bridgeType));

        // 如果桥接器不支持持久器，则抛出异常。
        if (!bridge.supportPersister()) {
            throw new IllegalStateException("桥接器不支持持久器, 请检查 bridge.properties 配置文件: " + bridgeType);
        }

        // 如果桥接器支持持久器，则获取持久器。
        persister = getPersisterFromBridge(bridge);
    }

    /**
     * 在指定的桥接器中获取持久器。
     *
     * @param bridge 指定的桥接器。
     * @return 指定的桥接器中的持久器。
     * @throws Exception 任何可能的异常。
     */
    protected abstract Bridge.Persister<D> getPersisterFromBridge(Bridge bridge) throws Exception;

    @Override
    public boolean writeOnly() {
        return persister.writeOnly();
    }

    @Override
    public void record(D data) throws HandlerException {
        persister.record(data);
    }

    @Override
    public void record(List<D> datas) throws HandlerException {
        persister.record(datas);
    }

    @Override
    public LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException {
        return internalLookup(lookupInfo);
    }

    private LookupResult<D> internalLookup(LookupInfo lookupInfo) throws HandlerException {
        if (persister.writeOnly()) {
            throw new LookupNotSupportedException();
        }
        return persister.lookup(lookupInfo);
    }

    @Override
    public List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        return internalLookup(lookupInfos);
    }

    private List<LookupResult<D>> internalLookup(List<LookupInfo> lookupInfos) throws HandlerException {
        if (persister.writeOnly()) {
            throw new LookupNotSupportedException();
        }
        return persister.lookup(lookupInfos);
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        return internalNativeQuery(queryInfo);
    }

    private QueryResult internalNativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        if (persister.writeOnly()) {
            throw new NativeQueryNotSupportedException();
        }
        return persister.nativeQuery(queryInfo);
    }

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        return internalNativeQuery(queryInfos);
    }

    private List<QueryResult> internalNativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        if (persister.writeOnly()) {
            throw new NativeQueryNotSupportedException();
        }
        return persister.nativeQuery(queryInfos);
    }

    @Override
    public String toString() {
        return "AbstractPersistHandler{" +
                "bridges=" + bridges +
                ", persister=" + persister +
                '}';
    }
}
