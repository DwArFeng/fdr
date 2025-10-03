package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.fdr.sdk.handler.mapper.AbstractMapperRegistry;
import com.dwarfeng.fdr.sdk.handler.mapper.AggregateMapper;
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
 * 平均值映射器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class AvgMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "avg_mapper";

    private final ApplicationContext ctx;

    public AvgMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "平均值映射器";
    }

    @Override
    public String provideDescription() {
        return "对序列中的所有数据条目进行平均值计算，" +
                "得到的值作为新的序列中的数据条目。新的数据条目的发生时间为序列开始时间和结束时间的中间值，" +
                "并且数据条目的数据点主键会被替换为序列的数据点主键。\n" +
                "如果序列中没有数据条目，则平均值为 0。\n" +
                "如果序列的数据点主键为 null，则抛出异常。\n" +
                "如果序列中有任何一个数据条目的值是 null，或其类型不是 Number，则抛出异常。\n" +
                "该映射器将数据转换为 double 类型，因此，该映射器适用于精度要求不高且数据不越界的情况。\n" +
                "该映射器是聚合映射器。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(AvgMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "AvgMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class AvgMapper extends AggregateMapper {

        @Override
        protected Object doAggregate(MapParam mapParam, List<Item> items, Date startDate, Date endDate) {
            // 排序以及过滤数据。
            items = MapperUtil.sortAndFilterItems(items, startDate, endDate, false);

            // 定义总和变量。
            double sum = 0;

            // 遍历序列中的所有数据条目，计算总和。
            for (Item item : items) {
                // 如果数据条目的值为 null，或其类型不是 Number，则抛出异常。
                if (item.getValue() == null || !(item.getValue() instanceof Number)) {
                    throw new IllegalArgumentException("数据条目的值为 null，或其类型不是 Number");
                }

                // 计算总和。
                sum += ((Number) item.getValue()).doubleValue();
            }

            // 定义平均值变量，计算并返回。
            return sum / items.size();
        }

        @Override
        public String toString() {
            return "AvgMapper{}";
        }
    }
}
