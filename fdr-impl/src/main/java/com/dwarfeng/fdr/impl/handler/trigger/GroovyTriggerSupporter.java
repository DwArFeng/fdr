package com.dwarfeng.fdr.impl.handler.trigger;

import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.fdr.impl.handler.TriggerSupporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 使用Groovy脚本的触发器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
@Component
public class GroovyTriggerSupporter implements TriggerSupporter {

    public static final String SUPPORT_TYPE = "groovy_trigger";
    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyTriggerSupporter.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public String provideType() {
        return SUPPORT_TYPE;
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
            Resource resource = applicationContext.getResource("classpath:groovy/ExampleTriggerProcessor.groovy");
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
}
