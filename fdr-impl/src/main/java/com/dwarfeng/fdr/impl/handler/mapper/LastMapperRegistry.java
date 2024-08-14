package com.dwarfeng.fdr.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 末元素映射器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class LastMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "last_mapper";

    private static final int EMPTY_ITEMS_STRATEGY_VOID = 0;
    private static final int EMPTY_ITEMS_STRATEGY_NULL = 1;

    private final ApplicationContext ctx;

    public LastMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "末元素映射器";
    }

    @Override
    public String provideDescription() {
        return "对序列中的所有数据条目进行操作，取出最后一个数据条目作为新的序列中的数据条目列表中的元素。\n" +
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
            return ctx.getBean(LastMapper.class);
        } catch (Exception e) {
            throw new MapperException(e);
        }
    }

    @Override
    public String toString() {
        return "LastMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class LastMapper extends AggregateMapper {

        @Override
        protected Object doAggregate(MapParam mapParam, List<Item> items, Date startDate, Date endDate) {
            // 如果列表为空，则返回 VOID 值。
            if (items.isEmpty()) {
                return returnOnEmptyItems(mapParam);
            }

            // 排序以及过滤数据。
            items = MapperUtil.sortAndFilterItems(items, startDate, endDate, false);

            // 如果列表为空，则返回 VOID 值，否则返回列表中的最后一个元素的值。
            if (items.isEmpty()) {
                return returnOnEmptyItems(mapParam);
            } else {
                return items.get(items.size() - 1).getValue();
            }
        }

        @SuppressWarnings("DuplicatedCode")
        private Object returnOnEmptyItems(MapParam mapParam) {
            // 展开参数。
            String param = mapParam.getParam();

            // 旧版本兼容性处理：如果参数为空，则返回 VOID。
            if (StringUtils.isBlank(param)) {
                return VOID;
            }

            // 解析参数。
            Config config = JSON.parseObject(param, Config.class);
            int emptyItemsStrategy = config.getEmptyItemsStrategy();

            // 根据参数返回不同的值。
            switch (emptyItemsStrategy) {
                case EMPTY_ITEMS_STRATEGY_VOID:
                    return VOID;
                case EMPTY_ITEMS_STRATEGY_NULL:
                    return null;
                default:
                    throw new IllegalArgumentException("未知的 emptyItemsStrategy 值: " + emptyItemsStrategy);
            }
        }

        @Override
        public String toString() {
            return "LastMapper{}";
        }
    }

    /**
     * 配置。
     *
     * @author DwArFeng
     * @since 2.2.0
     */
    public static class Config implements Bean {

        private static final long serialVersionUID = 6011924801667367071L;

        @JSONField(name = "#empty_items_strategy", ordinal = 1, deserialize = false)
        private String emptyItemsStrategyRem = EMPTY_ITEMS_STRATEGY_VOID + ": 返回 VOID, " +
                EMPTY_ITEMS_STRATEGY_NULL + ": 返回 null";

        @JSONField(name = "empty_items_strategy", ordinal = 1)
        private int emptyItemsStrategy;

        public Config() {
        }

        public Config(int emptyItemsStrategy) {
            this.emptyItemsStrategy = emptyItemsStrategy;
        }

        public String getEmptyItemsStrategyRem() {
            return emptyItemsStrategyRem;
        }

        public void setEmptyItemsStrategyRem(String emptyItemsStrategyRem) {
            this.emptyItemsStrategyRem = emptyItemsStrategyRem;
        }

        public int getEmptyItemsStrategy() {
            return emptyItemsStrategy;
        }

        public void setEmptyItemsStrategy(int emptyItemsStrategy) {
            this.emptyItemsStrategy = emptyItemsStrategy;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "emptyItemsStrategyRem='" + emptyItemsStrategyRem + '\'' +
                    ", emptyItemsStrategy=" + emptyItemsStrategy +
                    '}';
        }
    }
}
