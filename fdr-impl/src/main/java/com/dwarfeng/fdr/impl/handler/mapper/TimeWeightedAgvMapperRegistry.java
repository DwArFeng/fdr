package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.fdr.sdk.handler.mapper.AbstractMapperRegistry;
import com.dwarfeng.fdr.sdk.handler.mapper.AggregateMapper;
import com.dwarfeng.fdr.sdk.util.MapperUtil;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 时间加权平均映射器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class TimeWeightedAgvMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "time_weighted_agv_mapper";

    private final ApplicationContext ctx;

    public TimeWeightedAgvMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "时间加权平均映射器";
    }

    @Override
    public String provideDescription() {
        return "对序列中的所有数据条目进行加权平均值计算，" +
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
            return ctx.getBean(TimeWeightedAgvMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "TimeWeightedAgvMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class TimeWeightedAgvMapper extends AggregateMapper {

        @Override
        protected Object doAggregate(
                MapParam mapParam, List<Item> items,
                Date startDate, int startDateNanoOffset, Date endDate, int endDateNanoOffset
        ) {
            // 特殊情况判断结果。
            SpecialCaseAssertResult specialCaseAssertResult;

            // 判断特殊情况。
            specialCaseAssertResult = assertSpecialCase(items);
            if (specialCaseAssertResult.isSpecialCase()) {
                return specialCaseAssertResult.getValue();
            }

            // 排序以及过滤数据。
            items = MapperUtil.sortAndFilterItems(
                    items, startDate, startDateNanoOffset, endDate, endDateNanoOffset, true
            );

            // 再次判断特殊情况。
            specialCaseAssertResult = assertSpecialCase(items);
            if (specialCaseAssertResult.isSpecialCase()) {
                return specialCaseAssertResult.getValue();
            }

            // 如果第一个数据条目的发生时间小于 startDate，则将其发生时间设置为 startDate。
            Item oldItem;
            if (TimeUtil.compare(
                    (oldItem = items.get(0)).getHappenedDate(), oldItem.getHappenedDateNanoOffset(),
                    startDate, startDateNanoOffset
            ) < 0) {
                items.set(
                        0,
                        new Item(oldItem.getPointKey(), oldItem.getValue(), startDate, startDateNanoOffset)
                );
            }
            // 如果最后一个数据条目的发生时间大于 endDate，则将其发生时间设置为 endDate。
            if (TimeUtil.compare(
                    (oldItem = items.get(items.size() - 1)).getHappenedDate(),
                    oldItem.getHappenedDateNanoOffset(),
                    endDate, endDateNanoOffset
            ) > 0) {
                items.set(
                        items.size() - 1,
                        new Item(oldItem.getPointKey(), oldItem.getValue(), endDate, endDateNanoOffset)
                );
            }

            // 定义总和变量。
            double sum = 0;

            // 遍历序列中的所有数据条目，计算时间加权总和。
            for (int i = 0; i < items.size() - 1; i++) {
                Item item = items.get(i);
                Item nextItem = items.get(i + 1);

                // 获取当前数据条目的值。
                Object value = item.getValue();
                double doubleValue = Optional.ofNullable(value).map(v -> ((Number) v).doubleValue()).orElse(0.0);

                // 计算时间加权总和。
                long durationNanos = MapperUtil.nanosAfter(item, nextItem);
                sum += doubleValue * durationNanos;
            }

            // 计算并返回加权平均值：加权总和 / 首尾时间间隔（纳秒）。
            long totalNanos = MapperUtil.nanosAfter(items.get(0), items.get(items.size() - 1));
            if (totalNanos <= 0) {
                return Optional.ofNullable(items.get(0).getValue())
                        .map(v -> ((Number) v).doubleValue()).orElse(0.0);
            }
            return sum / totalNanos;
        }

        private SpecialCaseAssertResult assertSpecialCase(List<Item> items) {
            if (items.isEmpty()) {
                return new SpecialCaseAssertResult(true, 0.0);
            }
            if (items.size() == 1) {
                Item item = items.get(0);
                if (item.getValue() == null || !(item.getValue() instanceof Number)) {
                    throw new IllegalArgumentException("数据条目的值为 null，或其类型不是 Number");
                }
                return new SpecialCaseAssertResult(true, ((Number) item.getValue()).doubleValue());
            }
            return new SpecialCaseAssertResult(false, 0.0);
        }
    }

    private static final class SpecialCaseAssertResult {

        private final boolean specialCase;
        private final double value;

        public SpecialCaseAssertResult(boolean specialCase, double value) {
            this.specialCase = specialCase;
            this.value = value;
        }

        public boolean isSpecialCase() {
            return specialCase;
        }

        public double getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "SpecialCaseAssertResult{" +
                    "specialCase=" + specialCase +
                    ", value=" + value +
                    '}';
        }
    }
}
