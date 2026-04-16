package com.dwarfeng.fdr.impl.handler.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.handler.filter.AbstractFilter;
import com.dwarfeng.fdr.sdk.handler.filter.AbstractFilterRegistry;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.struct.RecordMemory;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 死区过滤器注册。
 *
 * <p>
 * 与 OPC UA Part 4 — 7.22.2 DataChangeFilter 中的 AbsoluteDeadband / PercentDeadband 对齐：<br>
 * 当数值变化未超过死区阈值时，样本可被过滤（等价于死区内不产生 DataChange 通知）。<br>
 * 基准值取自 {@link Filter.Context#lookupRecordMemory}：
 * 列表按时间从新到旧排列，自索引 <code>0</code> 起顺序查找第一条 <code>passed</code> 为 <code>true</code>，
 * 且 <code>value</code> 可解析为数值的{@link RecordMemory}，将其值作为“上次有效通过值”。<br>
 * “上次有效通过值”与“当前值”比较，根据配置的死区类型和死区值判断是否过滤当前样本。
 *
 * <p>
 * 冷启动（无可用历史通过值、或量程无效等无法计算死区时）不应用死区过滤，直接通过，避免无法上报。
 *
 * @author DwArFeng
 * @since 2.5.0
 */
@Component
public class DeadbandFilterRegistry extends AbstractFilterRegistry {

    /**
     * 过滤器类型标识，与配置中的 type 字段对应。
     */
    public static final String FILTER_TYPE = "deadband_filter";

    private final ApplicationContext ctx;

    public DeadbandFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "OPC UA 语义死区过滤器";
    }

    @Override
    public String provideDescription() {
        return "与 OPC UA DataChangeFilter 死区（Deadband）语义对齐：当数值相对“最近一次已通过管线的记录记忆”" +
                "的变化量未超过死区阈值时，样本被过滤。\n" +
                "deadband_type：" +
                "0 不应用死区；" +
                "1 绝对死区（AbsoluteDeadband）；" +
                "2 百分比死区（PercentDeadband，阈值 = (deadband_value/100)*(eu_high - eu_low)）。\n" +
                "基准值自 lookupRecordMemory 从新到旧扫描，取第一条 passed 且 value 可解析为数值的记录；" +
                "无可用基准时为冷启动，直接通过。\n" +
                "百分比模式需配置有效量程 eu_low < eu_high，否则样本被过滤并说明原因。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(1, 0.5, 0.0, 100.0);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Filter makeFilter(String type, String param) throws FilterException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(DeadbandFilter.class, config);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "DeadbandFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    /**
     * 死区过滤器实现。
     */
    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class DeadbandFilter extends AbstractFilter {

        private static final Logger LOGGER = LoggerFactory.getLogger(DeadbandFilter.class);

        private final Config config;

        public DeadbandFilter(Config config) {
            this.config = config;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) throws Exception {
            int deadbandType = config.getDeadbandType();
            if (Objects.equals(deadbandType, 0)) {
                return TestResult.NOT_FILTERED;
            }

            Object raw = testInfo.getValue();
            Double current = parseNumeric(raw);
            if (Objects.isNull(current)) {
                String message = "数据值无法解析为数值，死区过滤器仅适用于 Number 或可解析的 String";
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            Double last = resolveLastPassedNumeric(context, testInfo.getPointKey());
            if (Objects.isNull(last)) {
                return TestResult.NOT_FILTERED;
            }

            if (Objects.equals(deadbandType, 1)) {
                return testAbsolute(current, last);
            }
            if (Objects.equals(deadbandType, 2)) {
                return testPercent(current, last);
            }

            String message = "不支持的 deadband_type: " + deadbandType;
            LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
            return TestResult.filtered(message);
        }

        private TestResult testAbsolute(double current, double last) {
            double band = config.getDeadbandValue();
            if (Math.abs(current - last) <= band) {
                String format = "绝对死区: |当前值 - 上次有效值| = %.17g <= 阈值 %.17g (AbsoluteDeadband)";
                String message = String.format(format, Math.abs(current - last), band);
                LOGGER.debug("过滤: {}", message);
                return TestResult.filtered(message);
            }
            return TestResult.NOT_FILTERED;
        }

        private TestResult testPercent(double current, double last) {
            double low = config.getEuLow();
            double high = config.getEuHigh();
            if (!(high > low)) {
                String format = "百分比死区需要有效量程 eu_low < eu_high，当前 [%.17g, %.17g]";
                String message = String.format(format, low, high);
                LOGGER.debug("过滤: {}", message);
                return TestResult.filtered(message);
            }
            double span = high - low;
            double threshold = (config.getDeadbandValue() / 100.0) * span;
            if (Math.abs(current - last) <= threshold) {
                String format = "百分比死区: |当前值 - 上次有效值| = %.17g <= 动态阈值 %.17g " +
                        "(PercentDeadband, EURange [%.17g, %.17g], %.17g%%)";
                String message = String.format(
                        format, Math.abs(current - last), threshold, low, high, config.getDeadbandValue()
                );
                LOGGER.debug("过滤: {}", message);
                return TestResult.filtered(message);
            }
            return TestResult.NOT_FILTERED;
        }

        /**
         * 自索引 0 起向尾部查找第一条 passed 且 value 可解析为数值的记录。
         *
         * @return 解析后的数值，若无则返回 {@code null}（冷启动或无可比历史）。
         */
        private Double resolveLastPassedNumeric(Filter.Context context, LongIdKey pointKey) throws Exception {
            List<RecordMemory> list = context.lookupRecordMemory(pointKey);
            if (Objects.isNull(list) || list.isEmpty()) {
                return null;
            }
            for (RecordMemory rm : list) {
                if (!rm.isPassed()) {
                    continue;
                }
                Double v = parseNumeric(rm.getValue());
                if (Objects.nonNull(v)) {
                    return v;
                }
            }
            return null;
        }

        private static Double parseNumeric(Object value) {
            if (Objects.isNull(value)) {
                return null;
            }
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            if (value instanceof String) {
                try {
                    return Double.parseDouble((String) value);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
            return null;
        }

        @Override
        public String toString() {
            return "DeadbandFilter{" +
                    "config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 9115711772247618975L;

        @JSONField(name = "#deadband_type", ordinal = 1, deserialize = false)
        private String deadbandTypeRem =
                "0：不应用死区；1：绝对死区（AbsoluteDeadband）；2：百分比死区（PercentDeadband）。";

        @JSONField(name = "deadband_type", ordinal = 2)
        private int deadbandType;

        @JSONField(name = "#deadband_value", ordinal = 3, deserialize = false)
        private String deadbandValueRem =
                "与 OPC UA deadbandValue 对应。绝对死区：与“上次有效通过值”之差的绝对值 ≤ 本值时过滤；"
                        + "百分比死区：本值为百分数，动态阈值 = (本值/100)×(eu_high−eu_low)，差值 ≤ 阈值时过滤。";

        @JSONField(name = "deadband_value", ordinal = 4)
        private double deadbandValue;

        @JSONField(name = "#eu_low", ordinal = 5, deserialize = false)
        private String euLowRem =
                "仅百分比死区使用：量程下限（等价 AnalogItem EURange 低位），须小于 eu_high；绝对死区可忽略。";

        @JSONField(name = "eu_low", ordinal = 6)
        private double euLow;

        @JSONField(name = "#eu_high", ordinal = 7, deserialize = false)
        private String euHighRem =
                "仅百分比死区使用：量程上限（等价 AnalogItem EURange 高位）。绝对死区可忽略。";

        @JSONField(name = "eu_high", ordinal = 8)
        private double euHigh;

        public Config() {
        }

        public Config(int deadbandType, double deadbandValue, double euLow, double euHigh) {
            this.deadbandType = deadbandType;
            this.deadbandValue = deadbandValue;
            this.euLow = euLow;
            this.euHigh = euHigh;
        }

        public String getDeadbandTypeRem() {
            return deadbandTypeRem;
        }

        public void setDeadbandTypeRem(String deadbandTypeRem) {
            this.deadbandTypeRem = deadbandTypeRem;
        }

        public int getDeadbandType() {
            return deadbandType;
        }

        public void setDeadbandType(int deadbandType) {
            this.deadbandType = deadbandType;
        }

        public String getDeadbandValueRem() {
            return deadbandValueRem;
        }

        public void setDeadbandValueRem(String deadbandValueRem) {
            this.deadbandValueRem = deadbandValueRem;
        }

        public double getDeadbandValue() {
            return deadbandValue;
        }

        public void setDeadbandValue(double deadbandValue) {
            this.deadbandValue = deadbandValue;
        }

        public String getEuLowRem() {
            return euLowRem;
        }

        public void setEuLowRem(String euLowRem) {
            this.euLowRem = euLowRem;
        }

        public double getEuLow() {
            return euLow;
        }

        public void setEuLow(double euLow) {
            this.euLow = euLow;
        }

        public String getEuHighRem() {
            return euHighRem;
        }

        public void setEuHighRem(String euHighRem) {
            this.euHighRem = euHighRem;
        }

        public double getEuHigh() {
            return euHigh;
        }

        public void setEuHigh(double euHigh) {
            this.euHigh = euHigh;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "deadbandTypeRem='" + deadbandTypeRem + '\'' +
                    ", deadbandType=" + deadbandType +
                    ", deadbandValueRem='" + deadbandValueRem + '\'' +
                    ", deadbandValue=" + deadbandValue +
                    ", euLowRem='" + euLowRem + '\'' +
                    ", euLow=" + euLow +
                    ", euHighRem='" + euHighRem + '\'' +
                    ", euHigh=" + euHigh +
                    '}';
        }
    }
}
