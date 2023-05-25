package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.structure.Data;
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
    protected final Class<D> dataClazz;

    protected Bridge.Persister<D> persister;

    protected AbstractPersistHandler(List<Bridge> bridges, Class<D> dataClazz) {
        this.bridges = bridges;
        this.dataClazz = dataClazz;
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
        persister = bridge.getPersister(dataClazz);
    }

    @Override
    public boolean writeOnly() {
        return persister.writeOnly();
    }

    @Override
    public void record(D dataRecord) throws HandlerException {
        persister.record(dataRecord);
    }

    @Override
    public void record(List<D> dataRecords) throws HandlerException {
        persister.record(dataRecords);
    }

    @Override
    public List<QueryGuide> queryGuides() {
        return persister.queryGuides();
    }

    @Override
    public QueryResult<D> query(QueryInfo queryInfo) throws HandlerException {
        return persister.query(queryInfo);
    }

    @Override
    public String toString() {
        return "AbstractPersistHandler{" +
                "bridges=" + bridges +
                ", dataClazz=" + dataClazz +
                ", persister=" + persister +
                '}';
    }
}
