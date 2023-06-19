package com.dwarfeng.fdr.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.util.CompareUtil;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

/**
 * 使能比例映射器注册。
 *
 * @author mooyuan
 * @since 2.0.0
 */
@Component
public class EnableRatioMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "enable_ratio_mapper";

    private final ApplicationContext ctx;

    public EnableRatioMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "使能比例映射器";
    }

    @Override
    public String provideDescription() {
        return "用于计算布尔类型数据的使能比例，即数据中真值的占用时间与序列的总时间的比值: \n" +
                "invert 用于控制计算的是真值的比例还是假值的比例： \n" +
                "  false：计算的是真值的比例 \n" +
                "  true：计算的是假值的比例 \n";
    }

    @Override
    public String provideExampleParam() {
        return JSON.toJSONString(new Config(false));
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(EnableRatioMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "EnableRatioMapper{" +
                "mapperType='" + mapperType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class EnableRatioMapper extends AggregateMapper {

        @Override
        protected Object doAggregate(MapParam mapParam, List<Item> items, Date startDate, Date endDate) {

            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            boolean invert = config.getInvert();

            // 对数据点进行时间排序(正序)
            items.sort(CompareUtil.DATA_HAPPENED_DATE_ASC_COMPARATOR);

            //更新开始时间，去掉真空期
            startDate = items.get(0).getHappenedDate();

            // 判断中间存在不为boolean类型的数据抛出异常
            for (Item item : items) {
                if (!(item.getValue() instanceof Boolean)) {
                    throw new IllegalStateException("存在数据点值不为boolean类型");
                }
            }

            // 计算占比
            return calRatioByItems(items, startDate, endDate, invert);
        }

        /**
         * 计算占比
         *
         * @param items     排完序的数据点数组
         * @param startDate 序列开始时间
         * @param endDate   序列结束时间
         * @param invert    true 计算false的占比、false 计算true的占比
         * @return 获取占比
         */
        private double calRatioByItems(List<Item> items, Date startDate, Date endDate, boolean invert) {
            // 符合时间
            BigDecimal calTime = BigDecimal.ZERO;
            boolean calFlag = false;

            BigDecimal startDateTime = BigDecimal.valueOf(startDate.getTime());
            BigDecimal endDateTime = BigDecimal.valueOf(endDate.getTime());
            // 总时间
            BigDecimal allTime = endDateTime.subtract(startDateTime);

            // 上一次符合的时间
            BigDecimal preTime = BigDecimal.ZERO;

            for (Item item : items) {
                if (item.getValue() instanceof Boolean && !invert == (Boolean) item.getValue()) {

                    if (calFlag) {
                        continue;
                    }

                    preTime = BigDecimal.valueOf(item.getHappenedDate().getTime());
                    calFlag = true;
                } else {
                    if (!calFlag) {
                        continue;
                    }

                    calTime = calTime.add(BigDecimal.valueOf(item.getHappenedDate().getTime()).subtract(preTime));
                    calFlag = false;
                }
            }

            // 处理结束的时间，当最后时间的calFlag还是true时处理
            if (calFlag) {
                calTime = calTime.add(endDateTime.subtract(preTime));
            }

            return calTime.divide(allTime, 4, RoundingMode.HALF_UP).doubleValue();
        }

        @Override
        public String toString() {
            return "EnableRatioMapper{}";
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 6070172089803753764L;

        @JSONField(name = "invert", ordinal = 1)
        private boolean invert;

        @JSONField(name = "#invert", ordinal = 2, deserialize = false)
        private String invertRem = "true：计算的是假值的比例;false：计算的是真值的比例";

        public Config() {
        }

        public Config(boolean invert) {
            this.invert = invert;
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
                    "invert=" + invert +
                    ", invertRem='" + invertRem + '\'' +
                    '}';
        }
    }
}
