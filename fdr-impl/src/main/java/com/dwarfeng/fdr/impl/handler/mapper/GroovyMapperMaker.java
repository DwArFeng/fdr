package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.impl.handler.MapperMaker;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import groovy.lang.GroovyClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 使用Groovy脚本的过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.6.1
 */
@Component
public class GroovyMapperMaker implements MapperMaker {

    public static final String SUPPORT_TYPE = "groovy_mapper";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Mapper makeMapper(Object[] args) throws MapperException {
        try {
            // 构建 args。
            Object[] finalArgs;
            if (args.length <= 1) {
                finalArgs = new Object[0];
            } else {
                finalArgs = new Object[args.length - 1];
                System.arraycopy(args, 1, finalArgs, 0, args.length - 1);
            }
            // 通过Groovy脚本生成处理器。
            GroovyClassLoader classLoader = new GroovyClassLoader();
            Class<?> aClass = classLoader.parseClass((String) args[0]);
            Processor processor = (Processor) aClass.newInstance();
            // 构建过滤器对象。
            GroovyMapper mapper = ctx.getBean(GroovyMapper.class);
            mapper.setArgs(finalArgs);
            mapper.setProcessor(processor);
            return mapper;
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class GroovyMapper implements Mapper {

        private Object[] args;
        private Processor processor;

        public GroovyMapper() {
        }

        @Override
        public List<TimedValue> map(List<TimedValue> timedValues) throws MapperException {
            try {
                return processor.map(timedValues, args);
            } catch (MapperException e) {
                throw e;
            } catch (Exception e) {
                throw new MapperException(e);
            }
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        public Processor getProcessor() {
            return processor;
        }

        public void setProcessor(Processor processor) {
            this.processor = processor;
        }

        @Override
        public String toString() {
            return "GroovyMapper{" +
                    "args=" + Arrays.toString(args) +
                    ", processor=" + processor +
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
         * 映射一组带时间的数据。
         *
         * @param timedValues 指定的带时间的数据组成的列表。
         * @param args        映射参数。
         * @return 映射后的带时间的数据组成的列表。
         * @throws MapperException 映射器异常。
         */
        List<TimedValue> map(List<TimedValue> timedValues, Object[] args) throws MapperException;
    }
}
