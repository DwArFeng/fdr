package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * 保持处理器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractKeepHandler<D extends Data> implements KeepHandler<D> {

    protected final List<Bridge> bridges;
    protected final Class<D> dataClazz;

    protected Bridge.Keeper<D> keeper;

    protected AbstractKeepHandler(List<Bridge> bridges, Class<D> dataClazz) {
        this.bridges = Optional.ofNullable(bridges).orElse(Collections.emptyList());
        this.dataClazz = dataClazz;
    }

    /**
     * 初始化保持器。
     *
     * <p>
     * 该方法会从保持器列表中找到对应类型的桥接。
     *
     * <p>
     * 需要在子类构造完毕后调用该方法。
     */
    protected void init(String bridgeType) throws Exception {
        // 从保持器列表中找到对应类型的保持器。
        Bridge bridge = bridges.stream().filter(b -> b.supportType(bridgeType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 bridge 类型: " + bridgeType));
        keeper = bridge.getKeeper(dataClazz);
    }

    @Override
    public boolean writeOnly() {
        return keeper.writeOnly();
    }

    @Override
    public void update(D data) throws HandlerException {
        keeper.update(data);
    }

    @Override
    public void update(List<D> datas) throws HandlerException {
        keeper.update(datas);
    }

    @Override
    public D inspect(LongIdKey pointKey) throws HandlerException {
        return keeper.inspect(pointKey);
    }

    @Override
    public List<D> inspect(List<LongIdKey> pointKeys) throws HandlerException {
        return keeper.inspect(pointKeys);
    }

    @Override
    public String toString() {
        return "AbstractKeepHandler{" +
                "bridges=" + bridges +
                ", dataClazz=" + dataClazz +
                ", keeper=" + keeper +
                '}';
    }
}
