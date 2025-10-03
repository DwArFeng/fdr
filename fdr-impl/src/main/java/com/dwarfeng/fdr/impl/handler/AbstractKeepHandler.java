package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.handler.Bridge;
import com.dwarfeng.fdr.stack.exception.LatestException;
import com.dwarfeng.fdr.stack.exception.UpdateException;
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

    protected Bridge.Keeper<D> keeper;

    protected AbstractKeepHandler(List<Bridge> bridges) {
        this.bridges = Optional.ofNullable(bridges).orElse(Collections.emptyList());
    }

    /**
     * 初始化保持器。
     *
     * <p>
     * 该方法会从保持器列表中找到对应类型的桥接。
     *
     * <p>
     * 需要在子类构造完毕后调用该方法。
     *
     * @param bridgeType 指定的桥接类型。
     * @throws Exception 初始化过程中发生的任何异常。
     */
    protected void init(String bridgeType) throws Exception {
        // 从保持器列表中找到对应类型的保持器。
        Bridge bridge = bridges.stream().filter(b -> b.supportType(bridgeType)).findAny()
                .orElseThrow(() -> new HandlerException("未知的 bridge 类型: " + bridgeType));

        // 如果桥接器支持保持器，则获取保持器。
        keeper = getKeeperFromBridge(bridge);
    }

    /**
     * 在指定的桥接器中获取保持器。
     *
     * @param bridge 指定的桥接器。
     * @return 指定的桥接器中的保持器。
     * @throws Exception 任何可能的异常。
     */
    protected abstract Bridge.Keeper<D> getKeeperFromBridge(Bridge bridge) throws Exception;

    @Override
    public void update(D data) throws HandlerException {
        try {
            keeper.update(data);
        } catch (UpdateException e) {
            throw e;
        } catch (Exception e) {
            throw new UpdateException(e, Collections.singletonList(data));
        }
    }

    @Override
    public void update(List<D> datas) throws HandlerException {
        try {
            keeper.update(datas);
        } catch (UpdateException e) {
            throw e;
        } catch (Exception e) {
            throw new UpdateException(e, datas);
        }
    }

    @Override
    public D latest(LongIdKey pointKey) throws HandlerException {
        try {
            return keeper.latest(pointKey);
        } catch (LatestException e) {
            throw e;
        } catch (Exception e) {
            throw new LatestException(e, Collections.singletonList(pointKey));
        }
    }

    @Override
    public List<D> latest(List<LongIdKey> pointKeys) throws HandlerException {
        try {
            return keeper.latest(pointKeys);
        } catch (LatestException e) {
            throw e;
        } catch (Exception e) {
            throw new LatestException(e, pointKeys);
        }
    }

    @Override
    public String toString() {
        return "AbstractKeepHandler{" +
                "bridges=" + bridges +
                ", keeper=" + keeper +
                '}';
    }
}
