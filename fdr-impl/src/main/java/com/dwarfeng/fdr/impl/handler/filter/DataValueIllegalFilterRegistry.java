package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.fdr.sdk.handler.filter.AbstractFilter;
import com.dwarfeng.fdr.sdk.handler.filter.AbstractFilterRegistry;
import com.dwarfeng.fdr.sdk.util.Constants;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 数据值非法过滤器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DataValueIllegalFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "data_value_illegal_filter";

    private final ApplicationContext ctx;

    public DataValueIllegalFilterRegistry(ApplicationContext ctx) {
        super(FILTER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "数据值非法过滤器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值与 com.dwarfeng.fdr.sdk.util.Constants.DATA_VALUE_ILLEGAL 相等，则被过滤。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Filter makeFilter(String type, String param) throws FilterException {
        try {
            return ctx.getBean(DataValueIllegalFilter.class);
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "DataValueIllegalFilterRegistry{" +
                "filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class DataValueIllegalFilter extends AbstractFilter {

        @Override
        protected TestResult doTest(TestInfo testInfo) {
            if (Constants.DATA_VALUE_ILLEGAL.equals(testInfo.getValue())) {
                return TestResult.filtered("数据值与 com.dwarfeng.fdr.sdk.util.Constants.DATA_VALUE_ILLEGAL 相等");
            }
            return TestResult.NOT_FILTERED;
        }
    }
}
