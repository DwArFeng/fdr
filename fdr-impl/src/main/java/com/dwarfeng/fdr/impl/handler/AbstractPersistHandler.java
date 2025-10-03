package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.handler.Bridge;
import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.bean.dto.NativeQueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.LookupException;
import com.dwarfeng.fdr.stack.exception.NativeQueryException;
import com.dwarfeng.fdr.stack.exception.RecordException;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Collections;
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
     *
     * @param bridgeType 指定的桥接类型。
     * @throws Exception 初始化过程中发生的任何异常。
     */
    protected void init(String bridgeType) throws Exception {
        // 从持久器列表中找到对应类型的持久器。
        Bridge bridge = bridges.stream().filter(b -> b.supportType(bridgeType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 bridge 类型: " + bridgeType));

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
    public void record(D data) throws HandlerException {
        try {
            persister.record(data);
        } catch (RecordException e) {
            throw e;
        } catch (Exception e) {
            throw new RecordException(e, Collections.singletonList(data));
        }
    }

    @Override
    public void record(List<D> datas) throws HandlerException {
        try {
            persister.record(datas);
        } catch (RecordException e) {
            throw e;
        } catch (Exception e) {
            throw new RecordException(e, datas);
        }
    }

    @Override
    public LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException {
        try {
            return persister.lookup(lookupInfo);
        } catch (LookupException e) {
            throw e;
        } catch (Exception e) {
            throw new LookupException(e, Collections.singletonList(lookupInfo));
        }
    }

    @Override
    public List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException {
        try {
            return persister.lookup(lookupInfos);
        } catch (LookupException e) {
            throw e;
        } catch (Exception e) {
            throw new LookupException(e, lookupInfos);
        }
    }

    @Override
    public QueryResult nativeQuery(NativeQueryInfo queryInfo) throws HandlerException {
        try {
            return persister.nativeQuery(queryInfo);
        } catch (NativeQueryException e) {
            throw e;
        } catch (Exception e) {
            throw new NativeQueryException(e, Collections.singletonList(queryInfo));
        }
    }

    @Override
    public List<QueryResult> nativeQuery(List<NativeQueryInfo> queryInfos) throws HandlerException {
        try {
            return persister.nativeQuery(queryInfos);
        } catch (NativeQueryException e) {
            throw e;
        } catch (Exception e) {
            throw new NativeQueryException(e, queryInfos);
        }
    }

    @Override
    public String toString() {
        return "AbstractPersistHandler{" +
                "bridges=" + bridges +
                ", persister=" + persister +
                '}';
    }
}
