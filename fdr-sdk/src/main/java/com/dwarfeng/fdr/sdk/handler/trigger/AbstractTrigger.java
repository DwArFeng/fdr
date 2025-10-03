package com.dwarfeng.fdr.sdk.handler.trigger;

import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.exception.TriggerExecutionException;
import com.dwarfeng.fdr.stack.handler.Trigger;

/**
 * 触发器的抽象实现。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public abstract class AbstractTrigger implements Trigger {

    @Override
    public TestResult test(TestInfo testInfo) throws TriggerException {
        try {
            return doTest(testInfo);
        } catch (TriggerException e) {
            throw e;
        } catch (Exception e) {
            throw new TriggerExecutionException(e);
        }
    }

    protected abstract TestResult doTest(TestInfo testInfo) throws Exception;
}
