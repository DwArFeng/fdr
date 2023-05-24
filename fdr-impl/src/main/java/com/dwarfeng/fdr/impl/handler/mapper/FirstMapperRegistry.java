package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 首元素映射器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class FirstMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "first_mapper";

    private final ApplicationContext ctx;

    public FirstMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "首元素映射器";
    }

    @Override
    public String provideDescription() {
        return "对序列中的所有数据条目进行操作，取出第一个数据条目作为新的序列中的数据条目列表中的元素。\n" +
                "取出的数据条目的发生时间为该序列的起始时间与结束时间的中间值，数据点主键的值为该序列的数据点主键的值。\n" +
                "该映射器是聚合映射器。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(FirstMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "FirstMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class FirstMapper extends AggregateMapper {

        @Override
        protected Object doAggregate(MapParam mapParam, List<Item> items, Date startDate, Date endDate) {
            // 如果列表为空，则返回 VOID 值。
            if (items.isEmpty()) {
                return VOID;
            }

            // 排序以及过滤数据。
            items = MapperUtil.sortAndFilterItems(items, startDate, endDate, false);

            // 如果列表为空，则返回 VOID 值，否则返回列表中的第一个元素的值。
            if (items.isEmpty()) {
                return VOID;
            } else {
                return items.get(0).getValue();
            }
        }
    }
}
