package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.fdr.stack.exception.WasherException;
import com.dwarfeng.fdr.stack.struct.RecordMemory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.Date;
import java.util.List;

/**
 * 清洗器。
 *
 * <p>
 * 清洗器用将指定的数据对象进行清洗，并获得一个新的数据对象。<br>
 * 通常情况下，数据清洗可以用于数据检查，包括检查数据一致性，处理无效值和缺失值等。
 *
 * <p>
 * 清洗器有两种类型，分别是过滤前清洗器和过滤后清洗器。<br>
 * 过滤前清洗器在数据过滤处理之前进行清洗，过滤后清洗器在数据过滤处理之后进行清洗。<br>
 * 过滤前清洗器通常用于数据检查，过滤后清洗器通常用于数据修正。<br>
 * 通过 {@link WasherInfo#setPreFilter(boolean)} 方法可以设置清洗器的类型。
 *
 * <p>
 * 需要注意的是，清洗器的作用仅限于数据清洗，不应该用于数据过滤。因此对于不合法的数据，
 * 不应该抛出异常（因为这样会中断整个数据处理流程，并触发记录失败相关的调度），而是应该返回一个特殊的值，
 * 例如 <code>null</code>，
 * 或是该项目的 sdk 模块提供的一个特殊的值 <code>com.dwarfeng.fdr.sdk.util.Constants#DATA_VALUE_ILLEGAL</code>。<br>
 * 随后配置对应的过滤器，识别特殊值，从而拒绝该数据。<br>
 * 与此对应的是，如果无法完成数据的清洗流程，利用无法调用外部服务等原因，应该抛出异常。
 *
 * <p>
 * 有关清洗的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface Washer {

    /**
     * 初始化清洗器。
     *
     * <p>
     * 该方法会在清洗器初始化后调用，请将 context 存放在清洗器的字段中。<br>
     * 当清洗器被触发后，执行上下文中的相应方法即可。
     *
     * @param context 清洗器的上下文。
     * @since 2.5.0
     */
    void init(Context context);

    /**
     * 清洗指定的数据对象，并返回清洗结果。
     *
     * @param washInfo 清洗信息。
     * @return 清洗结果。
     * @throws WasherException 清洗器异常。
     * @since 2.5.0
     */
    WashResult wash(WashInfo washInfo) throws WasherException;

    /**
     * 清洗指定的数据对象，并返回清洗后的数据对象。
     *
     * @param rawValue 指定的数据对象。
     * @return 清洗后的数据对象。
     * @throws WasherException 清洗器异常。
     * @deprecated 该方法已被废弃，请改为使用 {@link #wash(WashInfo)}。
     */
    @Deprecated
    Object wash(Object rawValue) throws WasherException;

    /**
     * 清洗器上下文。
     *
     * @author DwArFeng
     * @since 2.5.0
     */
    interface Context {

        /**
         * 查询指定点位的记录记忆。
         *
         * <p>
         * 返回列表中的记录记忆按时间从新到旧排列，索引 <code>0</code> 对应最新的记录记忆。
         *
         * <p>
         * 调用者有义务仅对返回结果进行查看操作，不应对其进行任何修改。
         *
         * @param pointKey 点位主键。
         * @return 指定点位的记录记忆。
         * @throws Exception 查询记录记忆时抛出的任何异常。
         * @since 2.5.0
         */
        List<RecordMemory> lookupRecordMemory(LongIdKey pointKey) throws Exception;
    }

    /**
     * 清洗信息。
     *
     * <p>
     * 在 3.0.0 版本中，新增了数据发生时间在毫秒内的纳秒偏移。
     * 数据发生时间由 {@link #happenedDate} 和 {@link #happenedDateNanoOffset} 共同唯一确定。
     *
     * @author DwArFeng
     * @since 2.5.0
     */
    final class WashInfo {

        private final LongIdKey pointKey;
        private final Object value;
        private final Date happenedDate;

        /**
         * @since 3.0.0
         */
        private final int happenedDateNanoOffset;

        public WashInfo(LongIdKey pointKey, Object value, Date happenedDate) {
            this(pointKey, value, happenedDate, 0);
        }

        /**
         * @since 3.0.0
         */
        public WashInfo(LongIdKey pointKey, Object value, Date happenedDate, int happenedDateNanoOffset) {
            this.pointKey = pointKey;
            this.value = value;
            this.happenedDate = happenedDate;
            this.happenedDateNanoOffset = happenedDateNanoOffset;
        }

        public LongIdKey getPointKey() {
            return pointKey;
        }

        public Object getValue() {
            return value;
        }

        public Date getHappenedDate() {
            return happenedDate;
        }

        public int getHappenedDateNanoOffset() {
            return happenedDateNanoOffset;
        }

        @Override
        public String toString() {
            return "WashInfo{" +
                    "pointKey=" + pointKey +
                    ", value=" + value +
                    ", happenedDate=" + happenedDate +
                    ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                    '}';
        }
    }

    /**
     * 清洗结果。
     *
     * @author DwArFeng
     * @since 2.5.0
     */
    final class WashResult {

        public static WashResult of(Object value) {
            return new WashResult(value);
        }

        private final Object value;

        public WashResult(Object value) {
            this.value = value;
        }

        public Object getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "WashResult{" +
                    "value=" + value +
                    '}';
        }
    }
}
