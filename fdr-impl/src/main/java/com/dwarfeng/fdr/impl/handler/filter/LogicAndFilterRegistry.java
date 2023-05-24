package com.dwarfeng.fdr.impl.handler.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.FilterHandler;
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
 * 逻辑与过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class LogicAndFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "logic_and_filter";

    private final ApplicationContext ctx;

    public LogicAndFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "逻辑与过滤器";
    }

    @Override
    public String provideDescription() {
        return "逻辑与过滤器，如果所有子过滤器都被过滤, 则被过滤。";
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
    public Filter makeFilter(String type, String param) throws FilterException {
        try {
            // 获取过滤器处理器，用于创建代理过滤器。
            FilterHandler filterHandler = ctx.getBean(FilterHandler.class);

            // 获取配置类。
            Config config = JSON.parseObject(param, Config.class);

            // 遍历配置类的 item，创建代理过滤器。
            List<Filter> filters = new ArrayList<>();
            for (Item item : config.getItems()) {
                filters.add(filterHandler.make(item.getType(), item.getParam()));
            }

            // 生成并返回过滤器。
            return ctx.getBean(LogicAndFilter.class, filters);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "LogicAndFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class LogicAndFilter extends AbstractFilter {

        private static final Logger LOGGER = LoggerFactory.getLogger(LogicAndFilter.class);

        private final List<Filter> filters;

        public LogicAndFilter(List<Filter> filters) {
            this.filters = filters;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) throws Exception {
            // 定义一个字符串列表，用于存储被过滤器的过滤结果的信息。
            List<String> messages = new ArrayList<>();

            // 如果 filters 为空，则不被过滤。
            if (filters.isEmpty()) {
                return TestResult.NOT_FILTERED;
            }

            // 遍历 filters，如果有一个不被过滤，则不被过滤。
            for (Filter filter : filters) {
                TestResult testResult = filter.test(testInfo);
                if (!testResult.isFiltered()) {
                    return TestResult.NOT_FILTERED;
                }
                messages.add(testResult.getMessage());
            }

            // 如果所有的过滤器都被过滤，则被过滤。
            // 过滤信息为 messages 转化为 JSON 字符串。
            String message = "测试信息 " + testInfo + " 所有代理过滤器都被过滤, 详细信息: " +
                    JSON.toJSONString(messages, false);
            LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
            return TestResult.filtered(message);
        }

        @Override
        public String toString() {
            return "LogicAndFilter{" +
                    "filters=" + filters +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -5519475495233697268L;

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

        private static final long serialVersionUID = 6918596763600911226L;

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
