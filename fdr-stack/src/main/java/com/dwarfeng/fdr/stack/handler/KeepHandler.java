package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.LatestNotSupportedException;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 保持处理器。
 *
 * <p>
 * 该处理器用于保持数据，实现类似于实时数据的查询与更新。
 *
 * <p>
 * 保持处理器为每个数据点维护一个最新数据，该数据可以被查询和更新。
 *
 * <p>
 * 部分保持处理器可能只支持写入，不支持查询。<br>
 * 对于只写的保持处理器，其 {@link #latest(LongIdKey)} 和 {@link #latest(List)} 方法应该抛出
 * {@link LatestNotSupportedException} 异常。
 *
 * <p>
 * 有关保持的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface KeepHandler<D extends Data> extends Handler {

    /**
     * 更新数据。
     *
     * @param data 数据。
     * @throws HandlerException 处理器异常。
     */
    void update(D data) throws HandlerException;

    /**
     * 更新数据。
     *
     * @param datas 数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void update(List<D> datas) throws HandlerException;

    /**
     * 查询数据点的最新数据。
     *
     * <p>
     * 如果数据点主键对应的数据不存在，则返回的查询结果为 null。
     *
     * @param pointKey 指定的数据点对应的主键。
     * @return 指定的数据点的最新数据。
     * @throws HandlerException 处理器异常。
     */
    D latest(LongIdKey pointKey) throws HandlerException;

    /**
     * 查询数据点的最新数据。
     *
     * <p>
     * 如果数据点主键组成的列表中的某个索引处的数据点主键对应的数据不存在，
     * 则返回的查询结果组成的列表该处索引对应的查询结果为 null。
     *
     * @param pointKeys 指定的数据点对应的主键组成的列表。
     * @return 指定的数据点的最新数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<D> latest(List<LongIdKey> pointKeys) throws HandlerException;
}
