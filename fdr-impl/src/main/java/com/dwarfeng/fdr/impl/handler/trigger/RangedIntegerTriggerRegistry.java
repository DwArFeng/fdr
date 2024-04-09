package com.dwarfeng.fdr.impl.handler.trigger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.dcti.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.exception.TriggerMakeException;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 具有范围的 Integer过滤器注册。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
@Component
public class RangedIntegerTriggerRegistry extends AbstractTriggerRegistry {

    public static final String TRIGGER_TYPE = "ranged_integer_trigger";

    @Autowired
    private ApplicationContext ctx;

    public RangedIntegerTriggerRegistry() {
        super(TRIGGER_TYPE);
    }

    @Override
    public String provideLabel() {
        return "具有范围的整型触发器";
    }

    @Override
    public String provideDescription() {
        return "如果数据值是整型数且数值在配置的范围之内，则进行触发。";
    }

    @Override
    public String provideExampleContent() {
        return JSON.toJSONString(new Config(
                1,
                true,
                2,
                false
        ), true);
    }

    @Override
    public Trigger makeTrigger(TriggerInfo triggerInfo) throws TriggerException {
        try {
            RangedIntegerTrigger trigger = ctx.getBean(RangedIntegerTrigger.class);
            trigger.setPointKey(triggerInfo.getPointKey());
            trigger.setTriggerInfoKey(triggerInfo.getKey());
            trigger.setConfig(JSON.parseObject(triggerInfo.getContent(), Config.class));
            return trigger;
        } catch (Exception e) {
            throw new TriggerMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "RangedIntegerTriggerRegistry{" +
                "ctx=" + ctx +
                ", triggerType='" + triggerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class RangedIntegerTrigger implements Trigger, Bean {

        private static final long serialVersionUID = 1618651025967120267L;
        private static final Logger LOGGER = LoggerFactory.getLogger(RangedIntegerTrigger.class);

        private LongIdKey pointKey;
        private LongIdKey triggerInfoKey;
        private Config config;

        public RangedIntegerTrigger() {
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        public TriggeredValue test(DataInfo dataInfo) throws TriggerException {
            try {
                LOGGER.debug("测试数据点 " + dataInfo.toString() + "...");
                int integerValue = Integer.parseInt(dataInfo.getValue());

                // 不触发判据。
                if ((config.getCanEqualsMin() && integerValue < config.getMin()) ||
                        (!config.getCanEqualsMin() && integerValue <= config.getMin())) {
                    LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 小于(或小于等于等于)最小值, 不进行触发...");
                    return null;
                }
                if ((config.getCanEqualsMax() && integerValue > config.getMax()) ||
                        (!config.getCanEqualsMax() && integerValue >= config.getMax())) {
                    LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 大于(或大于等于等于)最大值, 不进行触发...");
                    return null;
                }

                // 触发判据。
                LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 在最大值与最小值之间, 进行触发...");
                return new TriggeredValue(
                        null,
                        pointKey,
                        triggerInfoKey,
                        dataInfo.getHappenedDate(),
                        dataInfo.getValue(),
                        String.format("数据值大于(或等于)%d且小于(或等于)%d", config.getMin(), config.getMax())
                );
            } catch (Exception e) {
                throw new TriggerException(e);
            }
        }

        public LongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(LongIdKey pointKey) {
            this.pointKey = pointKey;
        }

        public LongIdKey getTriggerInfoKey() {
            return triggerInfoKey;
        }

        public void setTriggerInfoKey(LongIdKey triggerInfoKey) {
            this.triggerInfoKey = triggerInfoKey;
        }

        public Config getConfig() {
            return config;
        }

        public void setConfig(Config config) {
            this.config = config;
        }

        @Override
        public String toString() {
            return "RangedIntegerTrigger{" +
                    "pointKey=" + pointKey +
                    ", triggerInfoKey=" + triggerInfoKey +
                    ", config=" + config +
                    '}';
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 2167683469180120702L;

        @JSONField(name = "min")
        private Integer min;

        @JSONField(name = "can_equals_min")
        private Boolean canEqualsMin;

        @JSONField(name = "max")
        private Integer max;

        @JSONField(name = "can_equals_max")
        private Boolean canEqualsMax;

        public Config() {
        }

        public Config(Integer min, Boolean canEqualsMin, Integer max, Boolean canEqualsMax) {
            this.min = min;
            this.canEqualsMin = canEqualsMin;
            this.max = max;
            this.canEqualsMax = canEqualsMax;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

        public Boolean getCanEqualsMin() {
            return canEqualsMin;
        }

        public void setCanEqualsMin(Boolean canEqualsMin) {
            this.canEqualsMin = canEqualsMin;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Boolean getCanEqualsMax() {
            return canEqualsMax;
        }

        public void setCanEqualsMax(Boolean canEqualsMax) {
            this.canEqualsMax = canEqualsMax;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "min=" + min +
                    ", canEqualsMin=" + canEqualsMin +
                    ", max=" + max +
                    ", canEqualsMax=" + canEqualsMax +
                    '}';
        }
    }
}
