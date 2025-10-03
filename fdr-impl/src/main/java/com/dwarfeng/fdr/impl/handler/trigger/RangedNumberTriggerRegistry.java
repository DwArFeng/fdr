package com.dwarfeng.fdr.impl.handler.trigger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.handler.trigger.AbstractTrigger;
import com.dwarfeng.fdr.sdk.handler.trigger.AbstractTriggerRegistry;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.exception.TriggerMakeException;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 范围数值触发器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RangedNumberTriggerRegistry extends AbstractTriggerRegistry {

    public static final String TRIGGER_TYPE = "ranged_number_trigger";

    private final ApplicationContext ctx;

    public RangedNumberTriggerRegistry(ApplicationContext ctx) {
        super(TRIGGER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "范围数值触发器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是数值且数值在配置的范围之内，则被触发。\n" +
                "该触发器支持 Number 类型的数据值，所有的数据值都会被转换为 Double 类型进行比较。" +
                "因此该触发器适用于精度要求不高，且数据不越界的情况。\n" +
                "字符串将被尝试转换为数值，如果转换失败，则不被触发。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(-1.25, true, 0.5, false);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Trigger makeTrigger(String type, String param) throws TriggerException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(RangedNumberTrigger.class, config);
        } catch (Exception e) {
            throw new TriggerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "RangedNumberTriggerRegistry{" +
                "triggerType='" + triggerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class RangedNumberTrigger extends AbstractTrigger {

        private static final Logger LOGGER = LoggerFactory.getLogger(RangedNumberTrigger.class);

        private final Config config;

        public RangedNumberTrigger(Config config) {
            this.config = config;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) {
            // 获取 testInfo 中的数据值。
            Object value = testInfo.getValue();

            // 如果数据值是 null，则不被触发。
            if (value == null) {
                return TestResult.NOT_TRIGGERED;
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
                    return TestResult.NOT_TRIGGERED;
                }
            }
            // 如果数据值是其他类型，不被触发。
            else {
                return TestResult.NOT_TRIGGERED;
            }

            // 如果数据值小于最小值，则不被触发。
            if (config.isEqualsMinAllowed() && doubleValue < config.getMin()) {
                return TestResult.NOT_TRIGGERED;
            }
            if (!config.isEqualsMinAllowed() && doubleValue <= config.getMin()) {
                return TestResult.NOT_TRIGGERED;
            }

            // 如果数据值大于最大值，则不被触发。
            if (config.isEqualsMaxAllowed() && doubleValue > config.getMax()) {
                return TestResult.NOT_TRIGGERED;
            }
            if (!config.isEqualsMaxAllowed() && doubleValue >= config.getMax()) {
                return TestResult.NOT_TRIGGERED;
            }

            // 如果数据值在最小值和最大值之间，则被触发。
            String message = String.format(
                    "数据值 %f 在范围 %s%f, %f%s 之间",
                    doubleValue,
                    config.isEqualsMinAllowed() ? "[" : "(",
                    config.getMin(),
                    config.getMax(),
                    config.isEqualsMaxAllowed() ? "]" : ")"
            );
            LOGGER.debug("测试信息 {} 被触发, 原因: {}", testInfo, message);
            return TestResult.triggered(message);
        }

        @Override
        public String toString() {
            return "RangedNumberTrigger{" +
                    "config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -5967517482655717548L;

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
