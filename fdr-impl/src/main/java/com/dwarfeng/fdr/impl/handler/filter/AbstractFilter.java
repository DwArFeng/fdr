package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterExecutionException;
import com.dwarfeng.fdr.stack.handler.Filter;

/**
 * 过滤器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractFilter implements Filter {

    @Override
    public TestResult test(TestInfo testInfo) throws FilterException {
        try {
            return doTest(testInfo);
        } catch (FilterException e) {
            throw e;
        } catch (Exception e) {
            throw new FilterExecutionException(e);
        }
    }

    protected abstract TestResult doTest(TestInfo testInfo) throws Exception;
}
