package com.dwarfeng.fdr.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.handler.mapper.AbstractMapperRegistry;
import com.dwarfeng.fdr.sdk.handler.mapper.OneToOneMapper;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 转换为双精度浮点数的映射器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class ToDoubleMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "to_double_mapper";

    private final ApplicationContext ctx;

    public ToDoubleMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "转换为双精度浮点数的映射器";
    }

    @Override
    public String provideDescription() {
        return "保留数据表中所有序列的开始时间和结束时间。对每个序列的数据条目做映射处理: \n" +
                "1. 如果数据条目的值的类型是 Number，那么获得其双精度浮点数值。 \n" +
                "2. 如果数据条目的值的类型是 String，那么尝试将其转换为双精度浮点数。 \n" +
                "3. 如果数据条目的值的类型是布尔值，那么根据配置对布尔值进行映射。 \n" +
                "4. 对于其它的情况，可以进行策略配置，映射为默认值、或 null、或忽略该数据条目。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(1.0, 0.0, 0, 0.0);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(ToDoubleMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "ToDoubleMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ToDoubleMapper extends OneToOneMapper {

        @Override
        protected Sequence doOneToOneMap(MapParam mapParam, Sequence sequence) {
            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            // 遍历序列中的每个数据条目。
            List<Item> items = new ArrayList<>(sequence.getItems().size());
            for (Item item : sequence.getItems()) {
                Optional.ofNullable(mapItem(config, item)).ifPresent(items::add);
            }

            // 返回映射后的序列。
            return new Sequence(sequence.getPointKey(), items, sequence.getStartDate(), sequence.getEndDate());
        }

        private Item mapItem(Config config, Item item) {
            // 如果数据条目的值的类型是 Number，那么获得其双精度浮点数值。
            if (item.getValue() instanceof Number) {
                return new Item(item.getPointKey(), ((Number) item.getValue()).doubleValue(), item.getHappenedDate());
            }

            // 如果数据条目的值的类型是 String，那么尝试将其转换为双精度浮点数。
            if (item.getValue() instanceof String) {
                try {
                    return new Item(
                            item.getPointKey(), Double.parseDouble((String) item.getValue()), item.getHappenedDate()
                    );
                } catch (Exception e) {
                    // 什么都不做。
                }
            }

            // 如果数据条目的值的类型是布尔值，那么根据配置对布尔值进行映射。
            if (item.getValue() instanceof Boolean) {
                return new Item(
                        item.getPointKey(),
                        (Boolean) item.getValue() ? config.getBooleanTrueValue() : config.getBooleanFalseValue(),
                        item.getHappenedDate()
                );
            }

            // 对于其它的情况，可以进行策略配置，映射为默认值、或 null、或忽略该数据条目。
            switch (config.getOtherTypeStrategy()) {
                case 0:
                    return new Item(item.getPointKey(), config.getOtherTypeDefaultValue(), item.getHappenedDate());
                case 1:
                    return new Item(item.getPointKey(), null, item.getHappenedDate());
                case 2:
                    return null;
                default:
                    throw new IllegalStateException("未知的其它类型策略: " + config.getOtherTypeStrategy());
            }
        }

        @Override
        public String toString() {
            return "ToDoubleMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -2653631531920274799L;

        @JSONField(name = "boolean_true_value", ordinal = 1)
        private double booleanTrueValue;

        @JSONField(name = "boolean_false_value", ordinal = 2)
        private double booleanFalseValue;

        @JSONField(name = "#other_type_strategy", ordinal = 3, deserialize = false)
        private String otherTypeStrategyRem = "0: 默认值, 1: null, 2: 忽略该数据条目。";

        @JSONField(name = "other_type_strategy", ordinal = 4)
        private int otherTypeStrategy;

        @JSONField(name = "other_type_default_value", ordinal = 5)
        private Double otherTypeDefaultValue;

        public Config() {
        }

        public Config(
                double booleanTrueValue, double booleanFalseValue, int otherTypeStrategy, Double otherTypeDefaultValue
        ) {
            this.booleanTrueValue = booleanTrueValue;
            this.booleanFalseValue = booleanFalseValue;
            this.otherTypeStrategy = otherTypeStrategy;
            this.otherTypeDefaultValue = otherTypeDefaultValue;
        }

        public double getBooleanTrueValue() {
            return booleanTrueValue;
        }

        public void setBooleanTrueValue(double booleanTrueValue) {
            this.booleanTrueValue = booleanTrueValue;
        }

        public double getBooleanFalseValue() {
            return booleanFalseValue;
        }

        public void setBooleanFalseValue(double booleanFalseValue) {
            this.booleanFalseValue = booleanFalseValue;
        }

        public String getOtherTypeStrategyRem() {
            return otherTypeStrategyRem;
        }

        public void setOtherTypeStrategyRem(String otherTypeStrategyRem) {
            this.otherTypeStrategyRem = otherTypeStrategyRem;
        }

        public int getOtherTypeStrategy() {
            return otherTypeStrategy;
        }

        public void setOtherTypeStrategy(int otherTypeStrategy) {
            this.otherTypeStrategy = otherTypeStrategy;
        }

        public Double getOtherTypeDefaultValue() {
            return otherTypeDefaultValue;
        }

        public void setOtherTypeDefaultValue(Double otherTypeDefaultValue) {
            this.otherTypeDefaultValue = otherTypeDefaultValue;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "booleanTrueValue=" + booleanTrueValue +
                    ", booleanFalseValue=" + booleanFalseValue +
                    ", otherTypeStrategyRem='" + otherTypeStrategyRem + '\'' +
                    ", otherTypeStrategy=" + otherTypeStrategy +
                    ", otherTypeDefaultValue=" + otherTypeDefaultValue +
                    '}';
        }
    }
}
