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

/**
 * 正则表达式触发器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
@Component
public class RegexTriggerRegistry extends AbstractTriggerRegistry {

    public static final String TRIGGER_TYPE = "regex_trigger";

    private final ApplicationContext ctx;

    public RegexTriggerRegistry(ApplicationContext ctx) {
        super(TRIGGER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "正则表达式触发器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值的类型是字符串，并且能够匹配指定的正则表达式，则被触发。";
    }

    @Override
    public String provideExampleParam() {
        return JSON.toJSONString(new Config("^\\d+$"), true);
    }

    @Override
    public Trigger makeTrigger(String type, String param) throws TriggerException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(RegexTrigger.class, config);
        } catch (Exception e) {
            throw new TriggerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "RegexTriggerRegistry{" +
                "triggerType='" + triggerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class RegexTrigger extends AbstractTrigger {

        private static final Logger LOGGER = LoggerFactory.getLogger(RegexTrigger.class);

        private final Config config;

        public RegexTrigger(Config config) {
            this.config = config;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) {
            // 获取 testInfo 的值。
            Object value = testInfo.getValue();

            // 如果值为 null，显然无法匹配正则表达式，因此不被触发。
            if (value == null) {
                return TestResult.NOT_TRIGGERED;
            }

            // 如果值不是字符串，显然无法匹配正则表达式，因此不被触发。
            if (!(value instanceof String)) {
                return TestResult.NOT_TRIGGERED;
            }

            // 判断值是否能够匹配正则表达式，如果不匹配，则不被触发。
            if (!((String) value).matches(config.getPattern())) {
                return TestResult.NOT_TRIGGERED;
            }

            // 如果能够匹配正则表达式，则被触发。
            String message = "数据值匹配正则表达式, 被触发";
            LOGGER.debug("测试信息 {} 被触发, 原因: {}", testInfo, message);
            return TestResult.triggered(message);
        }

        @Override
        public String toString() {
            return "RegexTrigger{" +
                    "config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -2096707580093634673L;

        @JSONField(name = "pattern")
        private String pattern;

        public Config() {
        }

        public Config(String pattern) {
            this.pattern = pattern;
        }

        public String getPattern() {
            return pattern;
        }

        public void setPattern(String pattern) {
            this.pattern = pattern;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "pattern='" + pattern + '\'' +
                    '}';
        }
    }
}
