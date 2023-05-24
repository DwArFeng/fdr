package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 非空过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class NonNullFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "non_null_filter";

    private final ApplicationContext ctx;

    public NonNullFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "非空过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值不为空，则不被过滤。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Filter makeFilter(String type, String param) throws FilterException {
        try {
            return ctx.getBean(NonNullFilter.class);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "NonNullFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class NonNullFilter extends AbstractFilter {

        private static final Logger LOGGER = LoggerFactory.getLogger(NonNullFilter.class);

        @Override
        protected TestResult doTest(TestInfo testInfo) {
            // 获取数据值。
            Object value = testInfo.getValue();

            // 如果数据值为空，则被过滤。
            if (Objects.isNull(value)) {
                String message = "数据值为 null";
                LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message);
                return TestResult.filtered(message);
            }

            // 如果数据值不为空，则不被过滤。
            return TestResult.NOT_FILTERED;
        }

        @Override
        public String toString() {
            return "NonNullFilter{}";
        }
    }
}
