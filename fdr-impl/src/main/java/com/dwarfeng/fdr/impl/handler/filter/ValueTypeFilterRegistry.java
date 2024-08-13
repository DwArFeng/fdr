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

import java.util.Arrays;
import java.util.List;

/**
 * 值类型过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class ValueTypeFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "value_type_filter";

    private final ApplicationContext ctx;

    public ValueTypeFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "值类型过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是指定的类型之一，则不被过滤。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(
                Arrays.asList(
                        "java.lang.Integer", "java.lang.Long", "java.lang.Float", "java.lang.Double",
                        "java.lang.Byte", "java.lang.Short"
                )
        );
        return JSON.toJSONString(config, true);
    }

    @Override
    public Filter makeFilter(String type, String param) throws FilterException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(ValueTypeFilter.class, config);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "ValueTypeFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ValueTypeFilter extends AbstractFilter {

        private static final Logger LOGGER = LoggerFactory.getLogger(ValueTypeFilter.class);

        private final Config config;

        public ValueTypeFilter(Config config) {
            this.config = config;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) throws Exception {
            // 获取 testInfo 的值的类型。
            Class<?> valueType = testInfo.getValue().getClass();
            // 获取 config 中的全限定名列表。
            List<String> canonicalNames = config.getCanonicalNames();

            // 遍历全限定名列表，如果值的类型是列表中任意一个类的子类，则不被过滤。
            for (String canonicalName : canonicalNames) {
                Class<?> clazz = Class.forName(canonicalName);
                if (clazz.isAssignableFrom(valueType)) {
                    return TestResult.NOT_FILTERED;
                }
            }

            // 如果值的类型不是列表中任意一个类的子类，则被过滤。
            String message = "值类型不匹配: " + valueType.getCanonicalName();
            LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
            return TestResult.filtered(message);
        }

        @Override
        public String toString() {
            return "ValueTypeFilter{" +
                    "config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -4561948369022196472L;

        @JSONField(name = "#canonical_names", ordinal = 1, deserialize = false)
        private String canonicalNamesRem =
                "在上述列表中填写类的全限定名，如果对象值的类型是列表中任意一个类的子类，则不被过滤。";

        @JSONField(name = "canonical_names", ordinal = 2)
        private List<String> canonicalNames;

        public Config() {
        }

        public Config(List<String> canonicalNames) {
            this.canonicalNames = canonicalNames;
        }

        public String getCanonicalNamesRem() {
            return canonicalNamesRem;
        }

        public void setCanonicalNamesRem(String canonicalNamesRem) {
            this.canonicalNamesRem = canonicalNamesRem;
        }

        public List<String> getCanonicalNames() {
            return canonicalNames;
        }

        public void setCanonicalNames(List<String> canonicalNames) {
            this.canonicalNames = canonicalNames;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "canonicalNamesRem='" + canonicalNamesRem + '\'' +
                    ", canonicalNames=" + canonicalNames +
                    '}';
        }
    }
}
