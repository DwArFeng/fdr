package com.dwarfeng.fdr.impl.handler.trigger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.exception.TriggerMakeException;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.fdr.stack.handler.TriggerHandler;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 逻辑非触发器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class LogicNotTriggerRegistry extends AbstractTriggerRegistry {

    public static final String TRIGGER_TYPE = "logic_not_trigger";

    private final ApplicationContext ctx;

    public LogicNotTriggerRegistry(ApplicationContext ctx) {
        super(TRIGGER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "逻辑非触发器";
    }

    @Override
    public String provideDescription() {
        return "逻辑非触发器，如果子触发器被触发, 则不被触发，否则被触发。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config("delegate_type", "delegate_param");
        return JSON.toJSONString(config, true);
    }

    @Override
    public Trigger makeTrigger(String type, String param) throws TriggerException {
        try {
            // 获取触发器处理器，用于创建代理触发器。
            TriggerHandler triggerHandler = ctx.getBean(TriggerHandler.class);

            // 获取配置类。
            Config config = JSON.parseObject(param, Config.class);

            // 创建代理触发器。
            Trigger delegate = triggerHandler.make(config.getType(), config.getParam());

            // 生成并返回触发器。
            return ctx.getBean(LogicNotTrigger.class, delegate);
        } catch (Exception e) {
            throw new TriggerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "LogicNotTriggerRegistry{" +
                "triggerType='" + triggerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class LogicNotTrigger extends AbstractTrigger {

        private static final Logger LOGGER = LoggerFactory.getLogger(LogicNotTrigger.class);

        private final Trigger delegate;

        public LogicNotTrigger(Trigger delegate) {
            this.delegate = delegate;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) throws Exception {
            // 获取代理触发器的测试结果。
            TestResult testResult = delegate.test(testInfo);

            // 如果代理触发器被触发，则返回不被触发的测试结果。
            if (testResult.isTriggered()) {
                return TestResult.NOT_TRIGGERED;
            }

            // 如果代理触发器没有被触发，则返回被触发的测试结果。
            String message = "代理触发器没有触发测试信息: " + testInfo;
            LOGGER.debug("测试信息 {} 被触发, 原因: {}", testInfo, message);
            return TestResult.triggered(message);
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -1992057768659419659L;

        @JSONField(name = "type", ordinal = 1)
        private String type;

        @JSONField(name = "param", ordinal = 2)
        private String param;

        public Config() {
        }

        public Config(String type, String param) {
            this.type = type;
            this.param = param;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "type='" + type + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
