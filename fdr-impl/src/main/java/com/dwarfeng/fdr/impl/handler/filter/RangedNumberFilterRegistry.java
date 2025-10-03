package com.dwarfeng.fdr.impl.handler.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.handler.filter.AbstractFilter;
import com.dwarfeng.fdr.sdk.handler.filter.AbstractFilterRegistry;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 范围数值过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RangedNumberFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "ranged_number_filter";

    private final ApplicationContext ctx;

    public RangedNumberFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "范围数值过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是数值且数值在配置的范围之内，则不被过滤。\n" +
                "该过滤器支持 Number 类型的数据值，所有的数据值都会被转换为 Double 类型进行比较。" +
                "因此该过滤器适用于精度要求不高，且数据不越界的情况。\n" +
                "字符串将被尝试转换为数值，如果转换失败，则被过滤。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(-1.25, true, 0.5, false);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Filter makeFilter(String type, String param) throws FilterException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(RangedNumberFilter.class, config);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "RangedNumberFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class RangedNumberFilter extends AbstractFilter {

        private static final Logger LOGGER = LoggerFactory.getLogger(RangedNumberFilter.class);

        private final Config config;

        public RangedNumberFilter(Config config) {
            this.config = config;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) {
            // 获取 testInfo 中的数据值。
            Object value = testInfo.getValue();

            // 如果数据值是 null，则被过滤。
            if (value == null) {
                String message = "数据值为 null";
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 定义 doubleValue。
            double doubleValue;

            // 如果数据值是 Number 类型，则直接转换为 double。
            if (value instanceof Number) {
                doubleValue = ((Number) value).doubleValue();
            }
            // 如果数据值是 String 类型，则尝试转换为 double。
            else if (value instanceof String) {
                try {
                    doubleValue = Double.parseDouble((String) value);
                } catch (NumberFormatException e) {
                    String message = "数据值 " + value + " 无法转换为数值";
                    LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                    return TestResult.filtered(message);
                }
            }
            // 如果数据值是其他类型，则被过滤。
            else {
                String message = "数据值 " + value + " 类型不是 Number 或 String";
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 如果数据值小于最小值，则被过滤。
            if (config.isEqualsMinAllowed() && doubleValue < config.getMin()) {
                String message = "数据值 " + value + " 小于最小值 " + config.getMin();
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }
            if (!config.isEqualsMinAllowed() && doubleValue <= config.getMin()) {
                String message = "数据值 " + value + " 小于等于最小值 " + config.getMin();
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 如果数据值大于最大值，则被过滤。
            if (config.isEqualsMaxAllowed() && doubleValue > config.getMax()) {
                String message = "数据值 " + value + " 大于最大值 " + config.getMax();
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }
            if (!config.isEqualsMaxAllowed() && doubleValue >= config.getMax()) {
                String message = "数据值 " + value + " 大于等于最大值 " + config.getMax();
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 如果数据值在最小值和最大值之间，则不被过滤。
            return TestResult.NOT_FILTERED;
        }

        @Override
        public String toString() {
            return "RangedNumberFilter{" +
                    "config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -756662488764019190L;

        @JSONField(name = "min")
        private double min = 0.0;

        @JSONField(name = "equals_min_allowed")
        private boolean equalsMinAllowed;

        @JSONField(name = "max")
        private double max = 0.0;

        @JSONField(name = "equals_max_allowed")
        private boolean equalsMaxAllowed;

        public Config() {
        }

        public Config(double min, boolean equalsMinAllowed, double max, boolean equalsMaxAllowed) {
            this.min = min;
            this.equalsMinAllowed = equalsMinAllowed;
            this.max = max;
            this.equalsMaxAllowed = equalsMaxAllowed;
        }

        public double getMin() {
            return min;
        }

        public void setMin(double min) {
            this.min = min;
        }

        public boolean isEqualsMinAllowed() {
            return equalsMinAllowed;
        }

        public void setEqualsMinAllowed(boolean equalsMinAllowed) {
            this.equalsMinAllowed = equalsMinAllowed;
        }

        public double getMax() {
            return max;
        }

        public void setMax(double max) {
            this.max = max;
        }

        public boolean isEqualsMaxAllowed() {
            return equalsMaxAllowed;
        }

        public void setEqualsMaxAllowed(boolean equalsMaxAllowed) {
            this.equalsMaxAllowed = equalsMaxAllowed;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "min=" + min +
                    ", equalsMinAllowed=" + equalsMinAllowed +
                    ", max=" + max +
                    ", equalsMaxAllowed=" + equalsMaxAllowed +
                    '}';
        }
    }
}
