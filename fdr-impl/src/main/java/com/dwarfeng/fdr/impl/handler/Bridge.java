package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.*;
import com.dwarfeng.fdr.stack.handler.KeepHandler;
import com.dwarfeng.fdr.stack.handler.PersistHandler;
import com.dwarfeng.fdr.stack.struct.Data;
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
 * 需要注意的是，不是所有的桥接器都支持所有的数据类型，因此如果<code>getXXXDataKeeper</code> 或
 * <code>getXXXDataPersist</code> 被调用时，如果桥接器不支持持久器/保持器，则可以抛出 {@link HandlerException} 异常。
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
     * 返回桥接器是否支持保持器。
     *
     * @return 桥接器是否支持保持器。
     */
    boolean supportKeeper();

    /**
     * 获取桥接器的一般数据保持器。
     *
     * @return 桥接器的一般数据保持器。
     */
    Keeper<NormalData> getNormalDataKeeper() throws HandlerException;

    /**
     * 获取桥接器的被过滤器数据保持器。
     *
     * @return 桥接器的被过滤器数据保持器。
     */
    Keeper<FilteredData> getFilteredDataKeeper() throws HandlerException;

    /**
     * 获取桥接器的被触发器数据保持器。
     *
     * @return 桥接器的被触发器数据保持器。
     */
    Keeper<TriggeredData> getTriggeredDataKeeper() throws HandlerException;

    /**
     * 返回桥接器是否支持持久器。
     *
     * @return 桥接器是否支持持久器。
     */
    boolean supportPersister();

    /**
     * 获取桥接器的一般数据持久器。
     *
     * @return 桥接器的一般数据持久器。
     */
    Persister<NormalData> getNormalDataPersister() throws HandlerException;

    /**
     * 获取桥接器的被过滤器数据持久器。
     *
     * @return 桥接器的被过滤器数据持久器。
     */
    Persister<FilteredData> getFilteredDataPersister() throws HandlerException;

    /**
     * 获取桥接器的被触发器数据持久器。
     *
     * @return 桥接器的被触发器数据持久器。
     */
    Persister<TriggeredData> getTriggeredDataPersister() throws HandlerException;

    /**
     * 保持器。
     *
     * @author DwArFeng
     * @see KeepHandler
     * @since 2.0.0
     */
    interface Keeper<D extends Data> {

        /**
         * 获取该处理器是否为只写处理器。
         *
         * @return 该处理器是否为只写处理器。
         * @see PersistHandler#writeOnly()
         */
        boolean writeOnly();

        /**
         * 更新数据。
         *
         * @param data 数据。
         * @throws HandlerException 处理器异常。
         * @see KeepHandler#update(Data)
         */
        void update(D data) throws HandlerException;

        /**
         * 更新数据。
         *
         * @param datas 数据组成的列表。
         * @throws HandlerException 处理器异常。
         * @see KeepHandler#update(List)
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
         * @see KeepHandler#latest(LongIdKey)
         */
        D latest(LongIdKey pointKey) throws HandlerException;

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
         * @see KeepHandler#latest(List)
         */
        List<D> latest(List<LongIdKey> pointKeys) throws HandlerException;
    }

    /**
     * 持久器。
     *
     * @author DwArFeng
     * @see PersistHandler
     * @since 2.0.0
     */
    interface Persister<D extends Data> {

        /**
         * 获取该处理器是否为只写处理器。
         *
         * @return 该处理器是否为只写处理器。
         * @see PersistHandler#writeOnly()
         */
        boolean writeOnly();

        /**
         * 记录数据。
         *
         * @param data 数据。
         * @throws HandlerException 处理器异常。
         * @see PersistHandler#record(Data)
         */
        void record(D data) throws HandlerException;

        /**
         * 记录数据。
         *
         * @param datas 数据组成的列表。
         * @throws HandlerException 处理器异常。
         * @see PersistHandler#record(List)
         */
        void record(List<D> datas) throws HandlerException;

        /**
         * 查看。
         *
         * @param lookupInfo 查看信息。
         * @return 查看结果。
         * @throws HandlerException 处理器异常。
         * @see PersistHandler#lookup(LookupInfo)
         */
        LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException;

        /**
         * 查看。
         *
         * @param lookupInfos 查看信息组成的列表。
         * @return 查看结果组成的列表。
         * @throws HandlerException 处理器异常。
         */
        List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException;
    }
}
