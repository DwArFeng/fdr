package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.fdr.sdk.handler.filter.AbstractFilter;
import com.dwarfeng.fdr.sdk.handler.filter.AbstractFilterRegistry;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 数值字符串过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class NumberStringFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "number_string_filter";

    private final ApplicationContext ctx;

    public NumberStringFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "数值字符串过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是数值字符串，则不被过滤。\n" +
                "过滤器执行测试方法时，会将数据值转换为 Double 类型，如果转换失败，则被过滤。\n" +
                "因此该过滤器适用于精度要求不高，且数据不越界的情况。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Filter makeFilter(String type, String param) throws FilterException {
        try {
            return ctx.getBean(NumberStringFilter.class);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "NumberStringFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class NumberStringFilter extends AbstractFilter {

        private static final Logger LOGGER = LoggerFactory.getLogger(NumberStringFilter.class);

        @Override
        protected TestResult doTest(TestInfo testInfo) {
            // 获取 testInfo 中的数据值。
            Object value = testInfo.getValue();

            // 如果数据值为 null，则被过滤。
            if (value == null) {
                String message = "数据值为 null";
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 如果数据值不是字符串，则被过滤。
            if (!(value instanceof String)) {
                String message = "数据值不是字符串";
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 尝试将数据值转换为 Double 类型，如果转换失败，则被过滤。
            try {
                Double.parseDouble((String) value);
            } catch (NumberFormatException e) {
                String message = "数据值不是数值字符串";
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 不被过滤。
            return TestResult.NOT_FILTERED;
        }
    }
}
