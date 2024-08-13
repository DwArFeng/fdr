package com.dwarfeng.fdr.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 转换为布尔值的映射器注册。
 *
 * @author mooyuan
 * @since 2.0.0
 */
@Component
public class ToBooleanMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "to_boolean_mapper";

    private final ApplicationContext ctx;

    public ToBooleanMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "转换为布尔值的映射器";
    }

    @Override
    public String provideDescription() {
        return "保留数据表中所有序列的开始时间和结束时间。对每个序列的数据条目做映射处理: \n" +
                "是否为严格模式： \n" +
                "  是：只有严格匹配真值或者假值的数据才会被转换为布尔类型的数据，如果序列中存在其它值的则抛出 IllegalArgumentException 异常 \n" +
                "    字符串真值：true \n" +
                "    字符串假值:false \n" +
                "    数字真值：1.0 \n" +
                "    数字假值：0.0 \n" +
                "  否：数据匹配真值时，会被转换为布尔类型的真值，其余任何值都会被转换为布尔类型的假值 \n";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(false, true);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(ToDoubleMapperRegistry.ToDoubleMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "ToBooleanMapperRegistry{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ToBooleanMapper extends OneToOneMapper {

        @Override
        protected Sequence doOneToOneMap(MapParam mapParam, Sequence sequence) {

            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            // 遍历序列中的每个数据条目。
            List<Item> items = new ArrayList<>(sequence.getItems().size());
            for (Item item : sequence.getItems()) {
                items.add(mapItem(config, item));
            }

            // 返回映射后的序列。
            return new Sequence(sequence.getPointKey(), items, sequence.getStartDate(), sequence.getEndDate());
        }

        // 处理具体的每条数据
        private Item mapItem(Config config, Item item) {

            // 是否忽略大小写
            boolean stringIgnoreCase = config.isStringIgnoreCase();
            boolean itemValue = false;
            // 是否启用严格模式
            if (config.isStrict()) {

                if (!(item.getValue() instanceof String) && !(item.getValue() instanceof Number) && !(item.getValue() instanceof Boolean)) {
                    throw new IllegalStateException("严格模式：传入值格式不正确，请传入字符串、数值或者布尔");
                }

                if (item.getValue() instanceof String) {

                    if ((stringIgnoreCase && "true".equalsIgnoreCase((String) item.getValue()))
                            || (!stringIgnoreCase && "true".equals(item.getValue()))) {
                        itemValue = true;
                    } else if ((stringIgnoreCase && !"false".equalsIgnoreCase((String) item.getValue()))
                            || (!stringIgnoreCase && !"false".equals(item.getValue()))) {
                        throw new IllegalStateException("严格模式：字符串格式不正确，请输入true或者false");
                    }
                }

                if (item.getValue() instanceof Number) {
                    if (BigDecimal.ONE.compareTo(BigDecimal.valueOf(((Number) item.getValue()).doubleValue())) == 0) {
                        itemValue = true;
                    } else if (BigDecimal.ZERO.compareTo(BigDecimal.valueOf(((Number) item.getValue()).doubleValue())) != 0) {
                        throw new IllegalStateException("严格模式：数值格式不正确，请输入1.0或者0.0");
                    }
                }

            } else {

                if (item.getValue() instanceof String) {
                    if ((stringIgnoreCase && "true".equalsIgnoreCase((String) item.getValue()))
                            || (!stringIgnoreCase && "true".equals(item.getValue()))) {
                        itemValue = true;
                    }
                }
                if (item.getValue() instanceof Number) {
                    if (BigDecimal.ONE.compareTo(BigDecimal.valueOf(((Number) item.getValue()).doubleValue())) == 0) {
                        itemValue = true;
                    }
                }
            }
            if (item.getValue() instanceof Boolean) {
                itemValue = (Boolean) item.getValue();
            }

            return new Item(
                    item.getPointKey(),
                    itemValue,
                    item.getHappenedDate()
            );
        }

        @Override
        public String toString() {
            return "ToBooleanMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 3399669781973928969L;

        @JSONField(name = "#strict", ordinal = 1, deserialize = false)
        private String strictRem = "true：启用严格模式，不符合直接抛出异常; false：不启用严格模式，不符合转为false";

        @JSONField(name = "strict", ordinal = 2)
        private boolean strict;

        @JSONField(name = "#string_ignore_case", ordinal = 3, deserialize = false)
        private String stringIgnoreCaseRem = "true：忽略字符串大小写，false：不忽略大小写";

        @JSONField(name = "string_ignore_case", ordinal = 4)
        private boolean stringIgnoreCase;

        @JSONField(name = "#specification", ordinal = 5, deserialize = false)
        private String specificationRem =
                "    字符串真值：true \n" +
                        "    字符串假值:false \n" +
                        "    数字真值：1.0 \n" +
                        "    数字假值：0.0 \n";

        public Config() {
        }

        public Config(boolean strict, boolean stringIgnoreCase) {
            this.strict = strict;
            this.stringIgnoreCase = stringIgnoreCase;
        }

        public String getStrictRem() {
            return strictRem;
        }

        public void setStrictRem(String strictRem) {
            this.strictRem = strictRem;
        }

        public boolean isStrict() {
            return strict;
        }

        public void setStrict(boolean strict) {
            this.strict = strict;
        }

        public String getStringIgnoreCaseRem() {
            return stringIgnoreCaseRem;
        }

        public void setStringIgnoreCaseRem(String stringIgnoreCaseRem) {
            this.stringIgnoreCaseRem = stringIgnoreCaseRem;
        }

        public boolean isStringIgnoreCase() {
            return stringIgnoreCase;
        }

        public void setStringIgnoreCase(boolean stringIgnoreCase) {
            this.stringIgnoreCase = stringIgnoreCase;
        }

        public String getSpecificationRem() {
            return specificationRem;
        }

        public void setSpecificationRem(String specificationRem) {
            this.specificationRem = specificationRem;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "strictRem='" + strictRem + '\'' +
                    ", strict=" + strict +
                    ", stringIgnoreCaseRem='" + stringIgnoreCaseRem + '\'' +
                    ", stringIgnoreCase=" + stringIgnoreCase +
                    ", specificationRem='" + specificationRem + '\'' +
                    '}';
        }
    }
}
