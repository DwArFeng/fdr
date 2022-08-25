package com.dwarfeng.fdr.impl.handler.filter;

import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
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
 * 使用Groovy脚本的过滤器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
@Component
public class GroovyFilterRegistry extends AbstractFilterRegistry {

    public static final String FILTER_TYPE = "groovy_filter";

    private static final Logger LOGGER = LoggerFactory.getLogger(GroovyFilterRegistry.class);

    @Autowired
    private ApplicationContext ctx;

    public GroovyFilterRegistry() {
        super(FILTER_TYPE);
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
            Resource resource = ctx.getResource("classpath:groovy/ExampleFilterProcessor.groovy");
            String example;
            try (InputStream sin = resource.getInputStream();
                 StringOutputStream sout = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(sin, sout, 4096);
                sout.flush();
                example = sout.toString();
            }
            return example;
        } catch (Exception e) {
            LOGGER.warn("读取文件 classpath:groovy/ExampleFilterProcessor.groovy 时出现异常", e);
            return "";
        }
    }

    @Override
    public Filter makeFilter(FilterInfo filterInfo) throws FilterException {
        try (GroovyClassLoader classLoader = new GroovyClassLoader()) {
            // 通过Groovy脚本生成处理器。
            Class<?> aClass = classLoader.parseClass(filterInfo.getContent());
            Processor processor = (Processor) aClass.newInstance();
            // 构建过滤器对象。
            GroovyFilter filter = ctx.getBean(GroovyFilter.class);
            filter.setPointKey(filterInfo.getPointKey());
            filter.setFilterInfoKey(filterInfo.getKey());
            filter.setProcessor(processor);
            return filter;
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "GroovyFilterRegistry{" +
                "ctx=" + ctx +
                ", filterType='" + filterType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GroovyFilter implements Filter {

        private LongIdKey pointKey;
        private LongIdKey filterInfoKey;
        private Processor processor;

        public GroovyFilter() {
        }

        @Override
        public FilteredValue test(DataInfo dataInfo) throws FilterException {
            try {
                return processor.test(pointKey, filterInfoKey, dataInfo);
            } catch (FilterException e) {
                throw e;
            } catch (Exception e) {
                throw new FilterException(e);
            }
        }

        public LongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(LongIdKey pointKey) {
            this.pointKey = pointKey;
        }

        public LongIdKey getFilterInfoKey() {
            return filterInfoKey;
        }

        public void setFilterInfoKey(LongIdKey filterInfoKey) {
            this.filterInfoKey = filterInfoKey;
        }

        public Processor getProcessor() {
            return processor;
        }

        public void setProcessor(Processor processor) {
            this.processor = processor;
        }

        @Override
        public String toString() {
            return "GroovyFilter{" +
                    "pointKey=" + pointKey +
                    ", filterInfoKey=" + filterInfoKey +
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
         * 测试一个数据是否能通过过滤器。
         *
         * <p> 如果指定的数据不能通过过滤器，则返回被过滤的数据值;否则返回 null。</>
         *
         * @param pointIdKey  数据点的主键。
         * @param filterIdKey 过滤器的主键。
         * @param dataInfo    指定的数据。
         * @return 被过滤的数据值，其主键为 null 即可。
         * @throws FilterException 过滤器异常。
         */
        FilteredValue test(LongIdKey pointIdKey, LongIdKey filterIdKey, DataInfo dataInfo) throws FilterException;
    }
}
