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

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 高通映射器注册。
 *
 * <p>
 * 保留高于阈值的数据。
 *
 * @author mooyuan
 * @since 2.0.0
 */
@Component
public class HighPassMapperRegistry extends AbstractMapperRegistry {
    public static final String MAPPER_TYPE = "high_pass_mapper";

    private final ApplicationContext ctx;

    public HighPassMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "高通映射器";
    }

    @Override
    public String provideDescription() {
        return "用于保留高于阈值的数据: \n" +
                "invert 用于控制计算的是低于阈值的数据还是高于阈值的数据: \n" +
                "  false：过滤的是高于阈值的数据 \n" +
                "  true：过滤的是低于阈值的数据 \n" +
                "threshold用于过滤的阈值 \n" +
                "can_equal是否包含等于阈值的数据";
    }

    @Override
    public String provideExampleParam() {
        return JSON.toJSONString(new Config(0.00, true, false));
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(HighPassMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "HighPassMapper{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class HighPassMapper extends OneToOneMapper {

        @Override
        protected Sequence doOneToOneMap(MapParam mapParam, Sequence sequence) {
            return highPass(mapParam, sequence);
        }

        @SuppressWarnings("DuplicatedCode")
        public static Sequence highPass(MapParam mapParam, Sequence sequence) {
            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            double threshold = config.getThreshold();

            boolean canEqual = config.getCanEqual();

            boolean invert = config.getInvert();

            // 定义数据条目列表。
            List<Item> items;

            items = doFilter(sequence, threshold, canEqual, invert);

            return new Sequence(sequence.getPointKey(), items, sequence.getStartDate(), sequence.getEndDate());
        }

        // 为了保证代码的可读性，此处代码不做简化。
        @SuppressWarnings("ConstantValue")
        private static List<Item> doFilter(Sequence sequence, double threshold, boolean canEqual, boolean invert) {
            List<Item> items;
            if (invert && canEqual) {
                items = sequence.getItems().stream().filter(
                        item -> (Objects.isNull(item.getValue()) ? 0.00 : (double) item.getValue()) <= threshold
                ).collect(Collectors.toList());
            } else if (invert && !canEqual) {
                items = sequence.getItems().stream().filter(
                        item -> (Objects.isNull(item.getValue()) ? 0.00 : (double) item.getValue()) < threshold
                ).collect(Collectors.toList());
            } else if (!invert && canEqual) {
                items = sequence.getItems().stream().filter(
                        item -> (Objects.isNull(item.getValue()) ? 0.00 : (double) item.getValue()) >= threshold
                ).collect(Collectors.toList());
            } else {
                items = sequence.getItems().stream().filter(
                        item -> (Objects.isNull(item.getValue()) ? 0.00 : (double) item.getValue()) > threshold
                ).collect(Collectors.toList());
            }
            return items;
        }

        @Override
        public String toString() {
            return "HighPassMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -3275585723620360134L;

        @JSONField(name = "threshold", ordinal = 1)
        private double threshold;

        @JSONField(name = "#threshold", ordinal = 2, deserialize = false)
        private String thresholdRem = "阈值，对数据进行筛选的标准";

        @JSONField(name = "can_equal", ordinal = 3)
        private boolean canEqual;

        @JSONField(name = "#can_equal", ordinal = 4, deserialize = false)
        private String canEqualRem = "true：包含阈值，false：不包含阈值";

        @JSONField(name = "invert", ordinal = 5)
        private boolean invert;

        @JSONField(name = "#invert", ordinal = 6, deserialize = false)
        private String invertRem = "true：过滤掉高于阈值的数据，false：过滤掉低于阈值的数据";

        public Config() {
        }

        public Config(double threshold, boolean canEqual, boolean invert) {
            this.threshold = threshold;
            this.canEqual = canEqual;
            this.invert = invert;
        }

        public double getThreshold() {
            return threshold;
        }

        public void setThreshold(double threshold) {
            this.threshold = threshold;
        }

        public String getThresholdRem() {
            return thresholdRem;
        }

        public void setThresholdRem(String thresholdRem) {
            this.thresholdRem = thresholdRem;
        }

        public boolean getCanEqual() {
            return canEqual;
        }

        public void setCanEqual(boolean canEqual) {
            this.canEqual = canEqual;
        }

        public String getCanEqualRem() {
            return canEqualRem;
        }

        public void setCanEqualRem(String canEqualRem) {
            this.canEqualRem = canEqualRem;
        }

        public boolean getInvert() {
            return invert;
        }

        public void setInvert(boolean invert) {
            this.invert = invert;
        }

        public String getInvertRem() {
            return invertRem;
        }

        public void setInvertRem(String invertRem) {
            this.invertRem = invertRem;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "threshold=" + threshold +
                    ", thresholdRem='" + thresholdRem + '\'' +
                    ", canEqual=" + canEqual +
                    ", canEqualRem='" + canEqualRem + '\'' +
                    ", invert=" + invert +
                    ", invertRem='" + invertRem + '\'' +
                    '}';
        }
    }
}
