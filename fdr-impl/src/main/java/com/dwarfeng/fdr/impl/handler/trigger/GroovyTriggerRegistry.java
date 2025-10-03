package com.dwarfeng.fdr.impl.handler.trigger;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.fdr.sdk.handler.trigger.AbstractTrigger;
import com.dwarfeng.fdr.sdk.handler.trigger.AbstractTriggerRegistry;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.exception.TriggerMakeException;
import com.dwarfeng.fdr.stack.handler.Trigger;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 使用Groovy脚本的触发器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
@Component
public class GroovyTriggerRegistry extends AbstractTriggerRegistry {

    public static final String TRIGGER_TYPE = "groovy_trigger";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyTriggerRegistry.class);

    private final ApplicationContext ctx;

    public GroovyTriggerRegistry(ApplicationContext ctx) {
        super(TRIGGER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Groovy过滤器";
    }

    @Override
    public String provideDescription() {
        return "通过自定义的groovy脚本，判断数据点是否通过过滤";
    }

    @Override
    public String provideExampleParam() {
        try {
            Resource resource = ctx.getResource("classpath:groovy/ExampleTriggerProcessor.groovy");
            String example;
            try (InputStream sin = resource.getInputStream();
                 StringOutputStream sout = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(sin, sout, 4096);
                sout.flush();
                example = sout.toString();
            }
            return example;
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleTriggerProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Trigger makeTrigger(String type, String param) throws TriggerException {
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            // 通过Groovy脚本生成处理器。
            Class<?> aClass = classLoader.parseClass(param);
            Processor processor = (Processor) aClass.newInstance();
            // 生成并返回触发器。
            return ctx.getBean(GroovyTrigger.class, processor);
        } catch (Exception e) {
            throw new TriggerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyTriggerRegistry{" +
                "triggerType='" + triggerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GroovyTrigger extends AbstractTrigger {

        private final Processor processor;

        public GroovyTrigger(Processor processor) {
            this.processor = processor;
        }

        @Override
        protected TestResult doTest(TestInfo testInfo) throws Exception {
            // 返回 processor 的测试结果。
            return processor.test(testInfo);
        }

        @Override
        public String toString() {
            return "GroovyTrigger{" +
                    "processor=" + processor +
                    '}';
        }
    }

    /**
     * Groovy处理器。
     *
     * @author DwArFeng
     * @since 1.5.2
     */
    public interface Processor {

        /**
         * 测试一个数据是否能不被触发器。
         *
         * @param testInfo 测试信息。
         * @return 测试结果。
         * @throws Exception 处理器执行过程中发生的任何异常。
         */
        Trigger.TestResult test(Trigger.TestInfo testInfo) throws Exception;
    }
}
