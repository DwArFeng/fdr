package com.dwarfeng.fdr.impl.handler.trigger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
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

import java.util.Objects;

/**
 * 布尔值触发器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class BooleanTriggerRegistry extends AbstractTriggerRegistry {

    public static final String TRIGGER_TYPE = "boolean_trigger";

    private final ApplicationContext ctx;

    public BooleanTriggerRegistry(ApplicationContext ctx) {
        super(TRIGGER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "布尔值触发器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值的类型是布尔值，并且能够匹配指定的布尔值，则被触发。";
    }

    @Override
    public String provideExampleParam() {
        return JSON.toJSONString(new Config(true), true);
    }

    @Override
    public Trigger makeTrigger(String type, String param) throws TriggerException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(BooleanTrigger.class, config);
        } catch (Exception e) {
            throw new TriggerMakeException(e);
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class BooleanTrigger extends AbstractTrigger {

        private static final Logger LOGGER = LoggerFactory.getLogger(BooleanTrigger.class);

        private final Config config;

        public BooleanTrigger(Config config) {
            this.config = config;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) {
            // 获取 testInfo 的值。
            Object value = testInfo.getValue();

            // 如果值是 null，显然无法匹配布尔值，因此不被触发。
            if (value == null) {
                return TestResult.NOT_TRIGGERED;
            }

            // 如果值不是布尔值，显然无法匹配布尔值，因此不被触发。
            if (!(value instanceof Boolean)) {
                return TestResult.NOT_TRIGGERED;
            }

            // 如果值不能匹配布尔值，则不被触发。
            if (Objects.equals(value, config.isValue())) {
                return TestResult.NOT_TRIGGERED;
            }

            // 如果值能匹配布尔值，则被触发。
            String message = "数据值匹配布尔值, 被触发";
            LOGGER.debug("测试信息 {} 被触发, 原因: {}", testInfo, message);
            return TestResult.triggered(message);
        }

        @Override
        public String toString() {
            return "BooleanTrigger{" +
                    "config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 4257632653515674812L;

        @JSONField(name = "value")
        private boolean value;

        public Config() {
        }

        public Config(boolean value) {
            this.value = value;
        }

        public boolean isValue() {
            return value;
        }

        public void setValue(boolean value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "value=" + value +
                    '}';
        }
    }
}
