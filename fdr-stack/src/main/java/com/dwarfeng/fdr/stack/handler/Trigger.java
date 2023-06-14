package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * 触发器。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Trigger {

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
     * 测试信息。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    final class TestInfo {

        private final LongIdKey pointKey;
        private final Object value;
        private final Date happenedDate;

        public TestInfo(LongIdKey pointKey, Object value, Date happenedDate) {
            this.pointKey = pointKey;
            this.value = value;
            this.happenedDate = happenedDate;
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

        @Override
        public String toString() {
            return "TestInfo{" +
                    "pointKey=" + pointKey +
                    ", value=" + value +
                    ", happenedDate=" + happenedDate +
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
