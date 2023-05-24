package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 本征映射器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class IdentifyMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "identify_mapper";

    private final ApplicationContext ctx;

    public IdentifyMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "本征映射器";
    }

    @Override
    public String provideDescription() {
        return "不对数据进行任何处理，直接返回原始数据。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(IdentifyMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "IdentifyMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class IdentifyMapper extends AbstractMapper {

        @Override
        protected List<Sequence> doMap(MapParam mapParam, List<Sequence> sequences) {
            return sequences;
        }

        @Override
        public String toString() {
            return "IdentifyMapper{}";
        }
    }
}
