package com.dwarfeng.fdr.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.util.CompareUtil;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.structure.Data;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 排序映射器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class SortMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "sort_mapper";
    public static final int HAPPENED_DATE_ORDER_ASC = 0;
    public static final int HAPPENED_DATE_ORDER_DESC = 1;

    private final ApplicationContext ctx;

    public SortMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "排序映射器";
    }

    @Override
    public String provideDescription() {
        return "对序列中的所有数据条目按照指定的规则进行排序。";
    }

    @Override
    public String provideExampleParam() {
        return JSON.toJSONString(new Config(HAPPENED_DATE_ORDER_ASC));
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(SortMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "SortMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class SortMapper extends OneToOneMapper {

        @Override
        protected Sequence doOneToOneMap(MapParam mapParam, Sequence sequence) {
            // 获得配置，并根据配置获得比较器。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);
            Comparator<Data> dataComparator;
            switch (config.getHappenedDateOrder()) {
                case HAPPENED_DATE_ORDER_ASC:
                    dataComparator = CompareUtil.DATA_HAPPENED_DATE_ASC_COMPARATOR;
                    break;
                case HAPPENED_DATE_ORDER_DESC:
                    dataComparator = CompareUtil.DATA_HAPPENED_DATE_DESC_COMPARATOR;
                    break;
                default:
                    throw new IllegalArgumentException("未知的 happenedDateOrder: " + config.getHappenedDateOrder());
            }

            // 对序列中的数据条目进行排序。
            List<Item> items = new ArrayList<>(sequence.getItems());
            items.sort(dataComparator);

            // 返回新的序列。
            return new Sequence(sequence.getPointKey(), items, sequence.getStartDate(), sequence.getEndDate());
        }

        @Override
        public String toString() {
            return "SortMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -1774891252697915728L;

        @JSONField(name = "happened_date_order", ordinal = 1)
        private int happenedDateOrder;

        @JSONField(name = "#happened_date_order", ordinal = 2, deserialize = false)
        private String happenedDateOrderRem = String.format(
                "%d: 升序, %d: 降序", HAPPENED_DATE_ORDER_ASC, HAPPENED_DATE_ORDER_DESC
        );

        public Config() {
        }

        public Config(int happenedDateOrder) {
            this.happenedDateOrder = happenedDateOrder;
        }

        public int getHappenedDateOrder() {
            return happenedDateOrder;
        }

        public void setHappenedDateOrder(int happenedDateOrder) {
            this.happenedDateOrder = happenedDateOrder;
        }

        public String getHappenedDateOrderRem() {
            return happenedDateOrderRem;
        }

        public void setHappenedDateOrderRem(String happenedDateOrderRem) {
            this.happenedDateOrderRem = happenedDateOrderRem;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "happenedDateOrder=" + happenedDateOrder +
                    ", happenedDateOrderRem='" + happenedDateOrderRem + '\'' +
                    '}';
        }
    }
}
