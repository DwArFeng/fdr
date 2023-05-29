package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.handler.PersistHandler.QueryGuide;
import com.dwarfeng.fdr.stack.structure.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 桥接器。
 *
 * <p>
 * 用于提供保持器和持久器。
 *
 * <p>
 * 需要注意的是，不是所有的桥接器都支持所有的数据类型，因此如果 {@link #getKeeper(Class)} 和 {@link #getPersister(Class)}
 * 被调用时，如果桥接器不支持指定的数据类型，则可以抛出 {@link HandlerException} 异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface Bridge {

    /**
     * 返回桥接器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 桥接器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 获取指定类型的保持器。
     *
     * @param clazz 指定的类型。
     * @return 指定类型的保持器。
     * @throws HandlerException 处理器异常。
     */
    <D extends Data> Keeper<D> getKeeper(Class<D> clazz) throws HandlerException;

    /**
     * 获取指定类型的持久器。
     *
     * @param clazz 指定的类型。
     * @return 指定类型的持久器。
     * @throws HandlerException 处理器异常。
     */
    <D extends Data> Persister<D> getPersister(Class<D> clazz) throws HandlerException;

    /**
     * 保持器。
     *
     * @author DwArFeng
     * @see com.dwarfeng.fdr.stack.handler.KeepHandler
     * @since 2.0.0
     */
    interface Keeper<D extends Data> {

        /**
         * 获取该处理器是否为只写处理器。
         *
         * @return 该处理器是否为只写处理器。
         */
        boolean writeOnly();

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
         * 查询数据。
         *
         * <p>
         * 如果数据点主键对应的数据不存在，则返回的查询结果为 null。
         *
         * @param pointKey 数据点主键。
         * @return 查询结果。
         * @throws HandlerException 处理器异常。
         */
        D inspect(LongIdKey pointKey) throws HandlerException;

        /**
         * 查询数据。
         *
         * <p>
         * 如果数据点主键组成的列表中的某个索引处的数据点主键对应的数据不存在，
         * 则返回的查询结果组成的列表该处索引对应的查询结果为 null。
         *
         * @param pointKeys 数据点主键组成的列表。
         * @return 查询结果。
         * @throws HandlerException 处理器异常。
         */
        List<D> inspect(List<LongIdKey> pointKeys) throws HandlerException;
    }

    /**
     * 持久器。
     *
     * @author DwArFeng
     * @see com.dwarfeng.fdr.stack.handler.PersistHandler
     * @since 2.0.0
     */
    interface Persister<D extends Data> {

        /**
         * 获取该处理器是否为只写处理器。
         *
         * @return 该处理器是否为只写处理器。
         */
        boolean writeOnly();

        /**
         * 记录数据。
         *
         * @param data 数据。
         * @throws HandlerException 处理器异常。
         */
        void record(D data) throws HandlerException;

        /**
         * 记录数据。
         *
         * @param datas 数据组成的列表。
         * @throws HandlerException 处理器异常。
         */
        void record(List<D> datas) throws HandlerException;

        /**
         * 获取该持久处理器的查询指导。
         *
         * <p>
         * 需要注意的是，返回的列表中，{@link QueryGuide#getPreset()} 方法返回的字符串应该是唯一的。
         *
         * @return 查询指导组成的列表。
         */
        List<QueryGuide> queryGuides();

        /**
         * 查询。
         *
         * @param queryInfo 查询信息。
         * @return 查询结果。
         * @throws HandlerException 处理器异常。
         */
        QueryResult<D> query(QueryInfo queryInfo) throws HandlerException;
    }
}
