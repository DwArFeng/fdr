package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

/**
 * 数据源。
 *
 * <p>
 * 有关数据源的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
public interface Source {

    /**
     * 初始化数据源。
     *
     * <p>
     * 该方法会在数据源初始化后调用，请将 context 存放在数据源的字段中。<br>
     * 当数据源被触发后，执行上下文中的相应方法即可。
     *
     * @param context 数据源的上下文。
     */
    void init(Context context);

    /**
     * 数据源是否上线。
     *
     * @return 数据源是否上线。
     * @throws HandlerException 处理器异常。
     */
    boolean isOnline() throws HandlerException;

    /**
     * 数据源上线。
     * <p>该方法被连续调用时不应该抛出异常。
     *
     * @throws HandlerException 处理器异常。
     */
    void online() throws HandlerException;

    /**
     * 数据源下线。
     * <p>该方法被连续调用时不应该抛出异常。
     *
     * @throws HandlerException 处理器异常。
     */
    void offline() throws HandlerException;

    /**
     * 数据源上下文。
     *
     * @author DwArFeng
     * @since 1.11.0
     */
    interface Context {

        /**
         * 向程序中记录数据。
         *
         * @param recordInfo 指定的记录信息。
         * @throws Exception 记录数据的过程中出现的任何异常。
         */
        void record(RecordInfo recordInfo) throws Exception;
    }
}
