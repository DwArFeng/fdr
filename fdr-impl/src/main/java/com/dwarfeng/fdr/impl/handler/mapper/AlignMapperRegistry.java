package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.fdr.sdk.handler.mapper.AbstractMapperRegistry;
import com.dwarfeng.fdr.sdk.handler.mapper.OneToOneMapper;
import com.dwarfeng.fdr.sdk.util.MapperUtil;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 对齐映射器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class AlignMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "align_mapper";

    private final ApplicationContext ctx;

    public AlignMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "对齐映射器";
    }

    @Override
    public String provideDescription() {
        return "对序列中的所有数据条目进行对齐操作，修改序列中数据条目的发生时间，" +
                "使其对齐到序列的起始时间与结束时间之间的某个时间。\n" +
                "该映射器的参数是一个介于 0 与 1 之间的浮点数，表示对齐的位置。\n" +
                "0 代表对齐到序列的起始时间；1 代表对齐到序列的结束时间。";
    }

    @Override
    public String provideExampleParam() {
        return "0.5(对齐到序列的中间时间)";
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(AlignMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "AlignMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class AlignMapper extends OneToOneMapper {

        @Override
        protected Sequence doOneToOneMap(MapParam mapParam, Sequence sequence) {
            // 获取参数，并转换为比率浮点数。
            double ratio = Float.parseFloat(mapParam.getParam());

            long startNanos = MapperUtil.toEpochNanos(sequence.getStartDate(), sequence.getStartDateNanoOffset());
            long endNanos = MapperUtil.toEpochNanos(sequence.getEndDate(), sequence.getEndDateNanoOffset());

            long alignNanos;
            if (ratio == 1) {
                alignNanos = endNanos;
            } else if (ratio == 0) {
                alignNanos = startNanos;
            } else if (ratio == 0.5) {
                alignNanos = (startNanos + endNanos) / 2;
            } else {
                alignNanos = (long) (startNanos + (endNanos - startNanos) * ratio);
            }
            Instant alignInstant = MapperUtil.instantFromEpochNanos(alignNanos);
            Date alignDate = TimeUtil.toDate(alignInstant);
            int alignDateNanoOffset = TimeUtil.toNanoOffset(alignInstant);

            // 定义数据条目列表。
            List<Item> items;
            // 对序列中的每个数据条目进行映射，修改其发生时间。
            items = sequence.getItems().stream().map(
                    item -> new Item(item.getPointKey(), item.getValue(), alignDate, alignDateNanoOffset)
            ).collect(Collectors.toList());

            // 返回新的序列。
            return new Sequence(
                    sequence.getPointKey(), items, sequence.getStartDate(), sequence.getStartDateNanoOffset(),
                    sequence.getEndDate(), sequence.getEndDateNanoOffset()
            );
        }

        @Override
        public String toString() {
            return "AlignMapper{}";
        }
    }
}
