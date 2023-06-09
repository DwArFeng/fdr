package com.dwarfeng.fdr.impl.handler.washer;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.fdr.stack.exception.WasherException;
import com.dwarfeng.fdr.stack.exception.WasherMakeException;
import com.dwarfeng.fdr.stack.handler.Washer;
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
 * 使用 Groovy 脚本的清洗器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class GroovyWasherRegistry extends AbstractWasherRegistry {

    public static final String WASHER_TYPE = "groovy_washer";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyWasherRegistry.class);

    private final ApplicationContext ctx;

    public GroovyWasherRegistry(ApplicationContext ctx) {
        super(WASHER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "Groovy清洗器";
    }

    @Override
    public String provideDescription() {
        return "通过自定义的groovy脚本，判断数据点是否不被清洗";
    }

    @Override
    public String provideExampleParam() {
        try {
            Resource resource = ctx.getResource("classpath:groovy/ExampleWasherProcessor.groovy");
            String example;
            try (InputStream sin = resource.getInputStream();
                 StringOutputStream sout = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(sin, sout, 4096);
                sout.flush();
                example = sout.toString();
            }
            return example;
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleWasherProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Washer makeWasher(String type, String param) throws WasherException {
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            // 通过Groovy脚本生成处理器。
            Class<?> aClass = classLoader.parseClass(param);
            Processor processor = (Processor) aClass.newInstance();
            // 生成并返回清洗器。
            return ctx.getBean(GroovyWasher.class, processor);
        } catch (Exception e) {
            throw new WasherMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyWasherRegistry{" +
                "washerType='" + washerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GroovyWasher extends AbstractWasher {

        private final Processor processor;

        public GroovyWasher(Processor processor) {
            this.processor = processor;
        }


        @Override
        protected Object doWash(Object rawValue) throws Exception {
            // 返回 processor 的处理结果。
            return processor.wash(rawValue);
        }

        @Override
        public String toString() {
            return "GroovyWasher{" +
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
         * 清洗指定的数据对象，并返回清洗后的数据对象。
         *
         * @param rawValue 指定的数据对象。
         * @return 清洗后的数据对象。
         * @throws Exception 处理器执行过程中发生的任何异常。
         */
        Object wash(Object rawValue) throws Exception;
    }
}
