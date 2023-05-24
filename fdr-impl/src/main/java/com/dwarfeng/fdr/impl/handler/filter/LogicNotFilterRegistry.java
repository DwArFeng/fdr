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

/**
 * 逻辑非过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class LogicNotFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "logic_not_filter";

    private final ApplicationContext ctx;

    public LogicNotFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "逻辑非过滤器";
    }

    @Override
    public String provideDescription() {
        return "逻辑非过滤器，如果子过滤器被过滤, 则不被过滤，否则被过滤。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config("delegate_type", "delegate_param");
        return JSON.toJSONString(config);
    }

    @Override
    public Filter makeFilter(String type, String param) throws FilterException {
        try {
            // 获取过滤器处理器，用于创建代理过滤器。
            FilterHandler filterHandler = ctx.getBean(FilterHandler.class);

            // 获取配置类。
            Config config = JSON.parseObject(param, Config.class);

            // 创建代理过滤器。
            Filter delegate = filterHandler.make(config.getType(), config.getParam());

            // 生成并返回过滤器。
            return ctx.getBean(LogicNotFilter.class, delegate);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "LogicNotFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class LogicNotFilter extends AbstractFilter {

        private static final Logger LOGGER = LoggerFactory.getLogger(LogicNotFilter.class);

        private final Filter delegate;

        public LogicNotFilter(Filter delegate) {
            this.delegate = delegate;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) throws Exception {
            // 获取代理过滤器的测试结果。
            TestResult testResult = delegate.test(testInfo);

            // 如果代理过滤器被过滤，则返回不被过滤的测试结果。
            if (testResult.isFiltered()) {
                return TestResult.NOT_FILTERED;
            }

            // 如果代理过滤器没有被过滤，则返回被过滤的测试结果。
            String message = "代理过滤器没有过滤测试信息: " + testInfo;
            LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
            return TestResult.filtered(message);
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -8017558136977033649L;

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
