package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * 过滤器。
 *
 * <p>
 * 过滤器用于测试一个数据是否能通过过滤器。
 *
 * <p>
 * 有关过滤的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public interface Filter {

    /**
     * 测试一个数据是否能通过过滤器。
     *
     * <p>
     * 如果指定的数据被过滤，则返回的过滤器结果的字段 {@link TestResult#isFiltered()} 应该为 <code>true</code>，
     * 此时信息字段 {@link TestResult#getMessage()} 可以作为该数据被过滤的原因。<br>
     * 如果指定的数据没有被过滤，则返回的过滤器结果的字段 {@link TestResult#isFiltered()} 应该为 <code>false</code>，
     * 此时信息字段不应该被使用。
     *
     * <p>
     * 如果指定的数据没有被过滤，返回过滤结果也可以为 <code>null</code>，这主要是考虑兼容性，不推荐新的过滤器这样做。
     *
     * <p>
     * 生成过滤结果时，可以使用 {@link TestResult#NOT_FILTERED}, {@link TestResult#filtered(String)} 等快捷方法。
     *
     * @param testInfo 测试信息。
     * @return 测试结果。
     * @throws FilterException 过滤器异常。
     */
    TestResult test(TestInfo testInfo) throws FilterException;

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
         * 未被过滤的测试结果。
         */
        public static final TestResult NOT_FILTERED = new TestResult(false, "");

        /**
         * 生成一个被过滤的测试结果。
         *
         * @return 被过滤的测试结果。
         */
        public static TestResult filtered(@Nonnull String message) {
            return new TestResult(true, message);
        }

        private final boolean filtered;
        private final String message;

        public TestResult(boolean filtered, @Nonnull String message) {
            this.filtered = filtered;
            this.message = message;
        }

        public boolean isFiltered() {
            return filtered;
        }

        @Nonnull
        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return "TestResult{" +
                    "filtered=" + filtered +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
