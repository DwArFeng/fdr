package com.dwarfeng.fdr.impl.handler.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
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
 * 正则表达式过滤器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
@Component
public class RegexFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "regex_filter";

    private final ApplicationContext ctx;

    public RegexFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "正则表达式过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值的类型是字符串，并且能够匹配指定的正则表达式，则不被过滤。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config("^\\d+$");
        return JSON.toJSONString(config, true);
    }

    @Override
    public Filter makeFilter(String type, String param) throws FilterException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(RegexFilter.class, config);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "RegexFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class RegexFilter extends AbstractFilter {

        private static final Logger LOGGER = LoggerFactory.getLogger(RegexFilter.class);

        private final Config config;

        public RegexFilter(Config config) {
            this.config = config;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) {
            // 获取 testInfo 的值。
            Object value = testInfo.getValue();

            // 如果值为 null，显然无法匹配正则表达式，因此被过滤。
            if (value == null) {
                String message = "数据值为 null, 被过滤";
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 如果值不是字符串，显然无法匹配正则表达式，因此被过滤。
            if (!(value instanceof String)) {
                String message = "数据值不是字符串, 被过滤";
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 判断值是否能够匹配正则表达式，如果无法匹配，则被过滤。
            if (!((String) value).matches(config.getPattern())) {
                String message = "数据值无法匹配正则表达式, 被过滤";
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 如果能够匹配正则表达式，则不被过滤。
            return TestResult.NOT_FILTERED;
        }

        @Override
        public String toString() {
            return "RegexFilter{" +
                    "config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 1367732196407928740L;

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
