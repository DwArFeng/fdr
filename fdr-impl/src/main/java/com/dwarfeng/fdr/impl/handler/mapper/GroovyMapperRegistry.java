package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.handler.Mapper.MapData;
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
import java.util.List;

/**
 * 使用Groovy脚本的过滤器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
@Component
public class GroovyMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "groovy_mapper";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyMapperRegistry.class);

    @Autowired
    private ApplicationContext ctx;

    public GroovyMapperRegistry() {
        super(MAPPER_TYPE);
    }

    @Override
    public String provideLabel() {
        return "Groovy映射器";
    }

    @Override
    public String provideDescription() {
        return "通过自定义的groovy脚本，实现对带有时间数据的映射";
    }

    @Override
    public String provideArgsIllustrate() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("元素0: String, Groovy代码, 需要实现 GroovyMapperRegistry.Processor 示例如下。\n");
            Resource resource = ctx.getResource("classpath:groovy/ExampleMapperProcessor.groovy");
            try (InputStream in = resource.getInputStream();
                 StringOutputStream out = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(in, out, 4096);
                out.flush();
                sb.append(out.toString().replaceAll("(.*)", "    $1"));
            }
            sb.append("\n");
            sb.append("元素1-n: GroovyMapperRegistry.Processor.map 方法中 mapData 的参数, 元素 n 对应 mapData 中第 n-1 个参数。");
            return sb.toString();
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleFilterProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(GroovyMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyMapperRegistry{" +
                "ctx=" + ctx +
                ", mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GroovyMapper implements Mapper {

        @Override
        public List<TimedValue> map(MapData mapData) throws MapperException {
            try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
                // 构建 args。
                Object[] mapperArgs = mapData.getArgs();
                Object[] processorArgs;
                if (mapperArgs.length <= 1) {
                    processorArgs = new Object[0];
                } else {
                    processorArgs = new Object[mapperArgs.length - 1];
                    System.arraycopy(mapperArgs, 1, processorArgs, 0, mapperArgs.length - 1);
                }
                // 通过Groovy脚本生成处理器。
                Class<?> aClass = classLoader.parseClass((String) mapperArgs[0]);
                Processor processor = (Processor) aClass.newInstance();
                // 映射数据值。
                mapData.setArgs(processorArgs);
                return processor.map(mapData);
            } catch (MapperException e) {
                throw e;
            } catch (Exception e) {
                throw new MapperException(e);
            }
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
         * 映射拥有发生时间的数据值。
         *
         * @param mapData 待映射的数据。
         * @return 映射后的拥有发生时间的数据值。
         * @throws MapperException 映射器异常。
         */
        List<TimedValue> map(MapData mapData) throws MapperException;
    }
}
