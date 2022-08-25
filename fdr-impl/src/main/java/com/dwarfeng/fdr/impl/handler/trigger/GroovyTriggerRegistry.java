package com.dwarfeng.fdr.impl.handler.trigger;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.exception.TriggerMakeException;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import groovy.lang.GroovyClassLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ApplicationContext ctx;

    public GroovyTriggerRegistry() {
        super(TRIGGER_TYPE);
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
    public String provideExampleContent() {
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
    public Trigger makeTrigger(TriggerInfo triggerInfo) throws TriggerException {
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            // 通过Groovy脚本生成处理器。
            Class<?> aClass = classLoader.parseClass(triggerInfo.getContent());
            Processor processor = (Processor) aClass.newInstance();
            // 构建触发器对象。
            GroovyTrigger trigger = ctx.getBean(GroovyTrigger.class);
            trigger.setPointKey(triggerInfo.getPointKey());
            trigger.setTriggerInfoKey(triggerInfo.getKey());
            trigger.setProcessor(processor);
            return trigger;
        } catch (Exception e) {
            throw new TriggerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyTriggerRegistry{" +
                "ctx=" + ctx +
                ", triggerType='" + triggerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GroovyTrigger implements Trigger {

        private LongIdKey pointKey;
        private LongIdKey triggerInfoKey;
        private Processor processor;

        public GroovyTrigger() {
        }

        @Override
        public TriggeredValue test(DataInfo dataInfo) throws TriggerException {
            try {
                return processor.test(pointKey, triggerInfoKey, dataInfo);
            } catch (TriggerException e) {
                throw e;
            } catch (Exception e) {
                throw new TriggerException(e);
            }
        }

        public LongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(LongIdKey pointKey) {
            this.pointKey = pointKey;
        }

        public LongIdKey getTriggerInfoKey() {
            return triggerInfoKey;
        }

        public void setTriggerInfoKey(LongIdKey triggerInfoKey) {
            this.triggerInfoKey = triggerInfoKey;
        }

        public Processor getProcessor() {
            return processor;
        }

        public void setProcessor(Processor processor) {
            this.processor = processor;
        }

        @Override
        public String toString() {
            return "GroovyTrigger{" +
                    "pointKey=" + pointKey +
                    ", triggerInfoKey=" + triggerInfoKey +
                    ", groovyProcessor=" + processor +
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
         * 测试一个数据是否能通过触发器。
         *
         * <p> 如果指定的数据不能通过触发器，则返回被过滤的数据值;否则返回 null。</>
         *
         * @param pointIdKey   数据点的主键。
         * @param triggerIdKey 触发器的主键。
         * @param dataInfo     指定的数据。
         * @return 被过滤的数据值，其主键为 null 即可。
         * @throws TriggerException 触发器异常。
         */
        TriggeredValue test(LongIdKey pointIdKey, LongIdKey triggerIdKey, DataInfo dataInfo) throws TriggerException;
    }
}
