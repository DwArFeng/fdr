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

import java.util.ArrayList;
import java.util.List;

/**
 * 逻辑与触发器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class LogicAndTriggerRegistry extends AbstractTriggerRegistry {

    public static final String TRIGGER_TYPE = "logic_and_trigger";

    private final ApplicationContext ctx;

    public LogicAndTriggerRegistry(ApplicationContext ctx) {
        super(TRIGGER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "逻辑与触发器";
    }

    @Override
    public String provideDescription() {
        return "逻辑与触发器，如果所有子触发器都被触发, 则被触发。";
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public String provideExampleParam() {
        List<Item> items = new ArrayList<>();
        items.add(new Item("delegate_type_1", "delegate_param_1"));
        items.add(new Item("delegate_type_2", "delegate_param_2"));
        items.add(new Item("delegate_type_3", "delegate_param_3"));

        return JSON.toJSONString(new Config(items));
    }

    @Override
    public Trigger makeTrigger(String type, String param) throws TriggerException {
        try {
            // 获取触发器处理器，用于创建代理触发器。
            TriggerHandler triggerHandler = ctx.getBean(TriggerHandler.class);

            // 获取配置类。
            Config config = JSON.parseObject(param, Config.class);

            // 遍历配置类的 item，创建代理触发器。
            List<Trigger> triggers = new ArrayList<>();
            for (Item item : config.getItems()) {
                triggers.add(triggerHandler.make(item.getType(), item.getParam()));
            }

            // 生成并返回触发器。
            return ctx.getBean(LogicAndTrigger.class, triggers);
        } catch (Exception e) {
            throw new TriggerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "LogicAndTriggerRegistry{" +
                "triggerType='" + triggerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class LogicAndTrigger extends AbstractTrigger {

        private static final Logger LOGGER = LoggerFactory.getLogger(LogicAndTrigger.class);

        private final List<Trigger> triggers;

        public LogicAndTrigger(List<Trigger> triggers) {
            this.triggers = triggers;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) throws Exception {
            // 定义一个字符串列表，用于存储被触发器的触发结果的信息。
            List<String> messages = new ArrayList<>();

            // 如果 triggers 为空，则不被触发。
            if (triggers.isEmpty()) {
                return TestResult.NOT_TRIGGERED;
            }

            // 遍历 triggers，如果有一个不被触发，则不被触发。
            for (Trigger trigger : triggers) {
                TestResult testResult = trigger.test(testInfo);
                if (!testResult.isTriggered()) {
                    return TestResult.NOT_TRIGGERED;
                }
                messages.add(testResult.getMessage());
            }

            // 如果所有的触发器都被触发，则被触发。
            // 触发信息为 messages 转化为 JSON 字符串。
            String message = "测试信息 " + testInfo + " 所有代理触发器都被触发, 详细信息: " +
                    JSON.toJSONString(messages, false);
            LOGGER.debug("测试信息 {} 被触发, 原因: {}", testInfo, message);
            return TestResult.triggered(message);
        }

        @Override
        public String toString() {
            return "LogicAndTrigger{" +
                    "triggers=" + triggers +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -8093746555525200107L;

        @JSONField(name = "items", ordinal = 1)
        private List<Item> items;

        public Config() {
        }

        public Config(List<Item> items) {
            this.items = items;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "items=" + items +
                    '}';
        }
    }

    public static class Item implements Bean {

        private static final long serialVersionUID = 794994979053931197L;

        @JSONField(name = "type", ordinal = 1)
        private String type;

        @JSONField(name = "param", ordinal = 2)
        private String param;

        public Item() {
        }

        public Item(String type, String param) {
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
            return "Item{" +
                    "type='" + type + '\'' +
                    ", param='" + param + '\'' +
                    '}';
        }
    }
}
