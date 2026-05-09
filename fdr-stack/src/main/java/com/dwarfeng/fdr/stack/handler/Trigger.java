package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.struct.RecordMemory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import java.util.Date;
import java.util.List;

/**
 * 触发器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Trigger {

    /**
     * 初始化触发器。
     *
     * <p>
     * 该方法会在触发器初始化后调用，请将 context 存放在触发器的字段中。<br>
     * 当触发器被触发后，执行上下文中的相应方法即可。
     *
     * @param context 触发器的上下文。
     * @since 2.5.0
     */
    void init(Context context);

    /**
     * 测试一个数据是否能通过触发器。
     *
     * <p>
     * 如果指定的数据被触发，则返回的触发器结果的字段 {@link TestResult#isTriggered()} 应该为 <code>true</code>，
     * 此时信息字段 {@link TestResult#getMessage()} 可以作为该数据被触发的原因。<br>
     * 如果指定的数据没有被触发，则返回的触发器结果的字段 {@link TestResult#isTriggered()} 应该为 <code>false</code>，
     * 此时信息字段不应该被使用。
     *
     * <p>
     * 如果指定的数据没有被触发，返回触发结果也可以为 <code>null</code>，这主要是考虑兼容性，不推荐新的触发器这样做。
     *
     * <p>
     * 生成触发结果时，可以使用 {@link TestResult#NOT_TRIGGERED}, {@link TestResult#triggered(String)} 等快捷方法。
     *
     * @param testInfo 测试信息。
     * @return 测试结果。
     * @throws TriggerException 触发器异常。
     */
    TestResult test(TestInfo testInfo) throws TriggerException;

    /**
     * 触发器上下文。
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
     * 测试信息。
     *
     * <p>
     * 在 3.0.0 版本中，新增了数据发生时间在毫秒内的纳秒偏移。
     * 数据发生时间由 {@link #happenedDate} 和 {@link #happenedDateNanoOffset} 共同唯一确定。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    final class TestInfo {

        private final LongIdKey pointKey;
        private final Object value;
        private final Date happenedDate;

        /**
         * @since 3.0.0
         */
        private final int happenedDateNanoOffset;

        public TestInfo(LongIdKey pointKey, Object value, Date happenedDate) {
            this(pointKey, value, happenedDate, 0);
        }

        /**
         * @since 3.0.0
         */
        public TestInfo(LongIdKey pointKey, Object value, Date happenedDate, int happenedDateNanoOffset) {
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
            return "TestInfo{" +
                    "pointKey=" + pointKey +
                    ", value=" + value +
                    ", happenedDate=" + happenedDate +
                    ", happenedDateNanoOffset=" + happenedDateNanoOffset +
                    '}';
        }
    }

    /**
     * 测试结果。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    final class TestResult {

        /**
         * 未被触发的测试结果。
         */
        public static final TestResult NOT_TRIGGERED = new TestResult(false, "");

        /**
         * 生成一个被触发的测试结果。
         *
         * @param message 触发的原因。
         * @return 被触发的测试结果。
         */
        public static TestResult triggered(@Nonnull String message) {
            return new TestResult(true, message);
        }

        private final boolean triggered;
        private final String message;

        public TestResult(boolean triggered, @Nonnull String message) {
            this.triggered = triggered;
            this.message = message;
        }

        public boolean isTriggered() {
            return triggered;
        }

        @Nonnull
        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "TestResult{" +
                    "triggered=" + triggered +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
