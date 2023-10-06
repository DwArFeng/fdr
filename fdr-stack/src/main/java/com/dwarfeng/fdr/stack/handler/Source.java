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
     *
     * <p>
     * 该方法被连续调用时不应该抛出异常。
     *
     * @throws HandlerException 处理器异常。
     */
    void online() throws HandlerException;

    /**
     * 数据源下线。
     *
     * <p>
     * 该方法被连续调用时不应该抛出异常。
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
         * <p>
         * 该方法只有在数据源上线的情况下能够被调用。
         *
         * <p>
         * 从数据源的业务逻辑上来说，数据源接收数据的过程应该是时间递增的，即接收到的数据永远是最新的。<br>
         * 因此调用该方法时，应尽量保证 {@link RecordInfo#getPointKey()} 相同的记录信息的
         * {@link RecordInfo#getHappenedDate()} 是递增的。<br>
         * 例如基于 kafka 的数据源，{@link RecordInfo#getPointKey()} 相同的记录信息应该交由同一个 partition 来处理。
         * 否则可能会出现旧的数据先于新的数据被记录的情况。同理，其它的数据源实现也应该避免类似的情况。<br>
         * 在特殊情况下，数据源可能会接收到时间不递增的数据，如数据源所在的服务器系统时间回拨。
         * 在这种情况下，数据源可以接受时间不递增的数据。对于时间不递增的数据，不同的保持器的处理方式不同，
         * 可能会选择覆盖最新数据，或者旧的数据，或者抛出异常。
         *
         * @param recordInfo 指定的记录信息。
         * @throws Exception 记录数据的过程中出现的任何异常。
         */
        void record(RecordInfo recordInfo) throws Exception;
    }
}
