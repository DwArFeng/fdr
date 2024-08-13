package com.dwarfeng.fdr.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 低通计数映射器
 *
 * <p>
 * 计算低于阈值的数据的个数。
 *
 * @author mooyuan
 * @since 2.0.0
 */
@Component
public class LowPassCounterMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "low_pass_counter_mapper";

    private final ApplicationContext ctx;

    public LowPassCounterMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "低通计数映射器";
    }

    @Override
    public String provideDescription() {
        return "用于计算低于阈值的数据的个数: \n" +
                "invert 用于控制计算的是低于阈值的数据还是高于阈值的数据: \n" +
                "  false：过滤的是高于阈值的数据 \n" +
                "  true：过滤的是低于阈值的数据 \n" +
                "threshold用于过滤的阈值 \n" +
                "can_equal是否包含等于阈值的数据";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config(0.00, true, false);
        return JSON.toJSONString(config, true);
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(LowPassCounterMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "LowPassCounterMapper{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class LowPassCounterMapper extends AggregateMapper {

        @Override
        protected Object doAggregate(MapParam mapParam, List<Item> items, Date startDate, Date endDate) {
            Sequence s = LowPassMapperRegistry.LowPassMapper.lowPass(
                    mapParam, new Sequence(new LongIdKey(0L), items, startDate, endDate)
            );
            return s.getItems().size();
        }

        @Override
        public String toString() {
            return "LowPassCounterMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = -3589980759613295700L;

        @JSONField(name = "#threshold", ordinal = 1, deserialize = false)
        private String thresholdRem = "阈值，对数据进行筛选的标准";

        @JSONField(name = "threshold", ordinal = 2)
        private double threshold;

        @JSONField(name = "#can_equal", ordinal = 3, deserialize = false)
        private String canEqualRem = "true：包含阈值，false：不包含阈值";

        @JSONField(name = "can_equal", ordinal = 4)
        private boolean canEqual;

        @JSONField(name = "#invert", ordinal = 5, deserialize = false)
        private String invertRem = "true：过滤掉低于阈值的数据，false：过滤掉高于阈值的数据";

        @JSONField(name = "invert", ordinal = 6)
        private boolean invert;

        public Config() {
        }

        public Config(double threshold, boolean canEqual, boolean invert) {
            this.threshold = threshold;
            this.canEqual = canEqual;
            this.invert = invert;
        }

        public String getThresholdRem() {
            return thresholdRem;
        }

        public void setThresholdRem(String thresholdRem) {
            this.thresholdRem = thresholdRem;
        }

        public double getThreshold() {
            return threshold;
        }

        public void setThreshold(double threshold) {
            this.threshold = threshold;
        }

        public String getCanEqualRem() {
            return canEqualRem;
        }

        public void setCanEqualRem(String canEqualRem) {
            this.canEqualRem = canEqualRem;
        }

        public boolean isCanEqual() {
            return canEqual;
        }

        public void setCanEqual(boolean canEqual) {
            this.canEqual = canEqual;
        }

        public String getInvertRem() {
            return invertRem;
        }

        public void setInvertRem(String invertRem) {
            this.invertRem = invertRem;
        }

        public boolean isInvert() {
            return invert;
        }

        public void setInvert(boolean invert) {
            this.invert = invert;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "thresholdRem='" + thresholdRem + '\'' +
                    ", threshold=" + threshold +
                    ", canEqualRem='" + canEqualRem + '\'' +
                    ", canEqual=" + canEqual +
                    ", invertRem='" + invertRem + '\'' +
                    ", invert=" + invert +
                    '}';
        }
    }
}
