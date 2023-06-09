package com.dwarfeng.fdr.impl.handler.washer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.util.Constants;
import com.dwarfeng.fdr.stack.exception.WasherException;
import com.dwarfeng.fdr.stack.exception.WasherMakeException;
import com.dwarfeng.fdr.stack.handler.Washer;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 将原始对象转换为布尔值的清洗器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ToBooleanWasherRegistry extends AbstractWasherRegistry {

    public static final String WASHER_TYPE = "to_boolean_washer";

    private final ApplicationContext ctx;

    public ToBooleanWasherRegistry(ApplicationContext ctx) {
        super(WASHER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "将原始对象转换为布尔值的清洗器";
    }

    @Override
    public String provideDescription() {
        return "将原始对象转换为布尔值的清洗器。\n" +
                "如果原始对象的类型是 java.lang.Boolean 的子类，则直接返回原始对象的 booleanValue() 方法的返回值；\n" +
                "如果原始对象的类型是 java.lang.String，则尝试按照规则将其转换为布尔值，如果转换失败，" +
                "则将其转换为 Constants.DATA_VALUE_ILLEGAL；\n" +
                "如果原始对象的类型是 java.lang.Number 的子类，则尝试按照规则将其转换为布尔值，如果转换失败，" +
                "则将其转换为 Constants.DATA_VALUE_ILLEGAL；\n" +
                "其余情况均将其转换为 Constants.DATA_VALUE_ILLEGAL。";
    }

    @Override
    public String provideExampleParam() {
        Config config = new Config();
        return JSON.toJSONString(config, true);
    }

    @Override
    public Washer makeWasher(String type, String param) throws WasherException {
        try {
            Config config = JSON.parseObject(param, Config.class);
            return ctx.getBean(ToBooleanWasher.class, config);
        } catch (Exception e) {
            throw new WasherMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "ToBooleanWasherRegistry{" +
                "washerType='" + washerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ToBooleanWasher extends AbstractWasher {

        private final Config config;

        public ToBooleanWasher(Config config) {
            this.config = config;
        }

        @Override
        protected Object doWash(Object rawValue) {
            if (rawValue instanceof Boolean) {
                return rawValue;
            } else if (rawValue instanceof String) {
                return parseString((String) rawValue);
            } else if (rawValue instanceof Number) {
                return parseNumber((Number) rawValue);
            } else {
                return false;
            }
        }

        private Object parseString(String rawValue) {
            // 展开参数。
            String stringTrueValue = config.getStringTrueValue();
            String stringFalseValue = config.getStringFalseValue();
            boolean stringIgnoreCase = config.isStringIgnoreCase();
            boolean strict = config.isStrict();

            // 如果 stringIgnoreCase 为 true，则将 rawValue 以及 stringTrueValue 和 stringFalseValue 都转换为小写。
            if (stringIgnoreCase) {
                rawValue = rawValue.toLowerCase();
                stringTrueValue = stringTrueValue.toLowerCase();
                stringFalseValue = stringFalseValue.toLowerCase();
            }

            // 如果 rawValue 与 stringTrueValue 相等，则返回 true。
            if (Objects.equals(rawValue, stringTrueValue)) {
                return true;
            }
            // 如果 rawValue 与 stringFalseValue 相等，则返回 false。
            if (Objects.equals(rawValue, stringFalseValue)) {
                return false;
            }
            // 如果 strict 为 true，则返回 Constants.DATA_VALUE_ILLEGAL。
            if (strict) {
                return Constants.DATA_VALUE_ILLEGAL;
            }
            // 如果 rawValue 与 stringTrueValue 不相等，则返回 false。
            return false;
        }

        private Object parseNumber(Number rawValue) {
            // 展开参数。
            double numberTrueValue = config.getNumberTrueValue();
            double numberFalseValue = config.getNumberFalseValue();
            boolean strict = config.isStrict();

            // 如果 rawValue.doubleValue() 与 numberTrueValue 相等，则返回 true。
            if (rawValue.doubleValue() == numberTrueValue) {
                return true;
            }
            // 如果 rawValue.doubleValue() 与 numberFalseValue 相等，则返回 false。
            if (rawValue.doubleValue() == numberFalseValue) {
                return false;
            }
            // 如果 strict 为 true，则返回 Constants.DATA_VALUE_ILLEGAL。
            if (strict) {
                return Constants.DATA_VALUE_ILLEGAL;
            }
            // 如果 rawValue.doubleValue() 与 numberTrueValue 不相等，则返回 false。
            return false;
        }
    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 1597901317398449609L;

        @JSONField(name = "string_true_value", defaultValue = "true")
        private String stringTrueValue = "true";

        @JSONField(name = "string_false_value", defaultValue = "false")
        private String stringFalseValue = "false";

        @JSONField(name = "string_ignore_case", defaultValue = "true")
        private boolean stringIgnoreCase = true;

        @JSONField(name = "number_true_value", defaultValue = "1")
        private double numberTrueValue = 1;

        @JSONField(name = "number_false_value", defaultValue = "0")
        private double numberFalseValue = 0;

        @JSONField(name = "strict", defaultValue = "true")
        private boolean strict = true;

        public Config() {
        }

        public Config(
                String stringTrueValue, String stringFalseValue, boolean stringIgnoreCase, double numberTrueValue,
                double numberFalseValue, boolean strict
        ) {
            this.stringTrueValue = stringTrueValue;
            this.stringFalseValue = stringFalseValue;
            this.stringIgnoreCase = stringIgnoreCase;
            this.numberTrueValue = numberTrueValue;
            this.numberFalseValue = numberFalseValue;
            this.strict = strict;
        }

        public String getStringTrueValue() {
            return stringTrueValue;
        }

        public void setStringTrueValue(String stringTrueValue) {
            this.stringTrueValue = stringTrueValue;
        }

        public String getStringFalseValue() {
            return stringFalseValue;
        }

        public void setStringFalseValue(String stringFalseValue) {
            this.stringFalseValue = stringFalseValue;
        }

        public boolean isStringIgnoreCase() {
            return stringIgnoreCase;
        }

        public void setStringIgnoreCase(boolean stringIgnoreCase) {
            this.stringIgnoreCase = stringIgnoreCase;
        }

        public double getNumberTrueValue() {
            return numberTrueValue;
        }

        public void setNumberTrueValue(double numberTrueValue) {
            this.numberTrueValue = numberTrueValue;
        }

        public double getNumberFalseValue() {
            return numberFalseValue;
        }

        public void setNumberFalseValue(double numberFalseValue) {
            this.numberFalseValue = numberFalseValue;
        }

        public boolean isStrict() {
            return strict;
        }

        public void setStrict(boolean strict) {
            this.strict = strict;
        }

        @Override
        public String toString() {
            return "Config{" +
                    "stringTrueValue='" + stringTrueValue + '\'' +
                    ", stringFalseValue='" + stringFalseValue + '\'' +
                    ", stringIgnoreCase=" + stringIgnoreCase +
                    ", numberTrueValue=" + numberTrueValue +
                    ", numberFalseValue=" + numberFalseValue +
                    ", strict=" + strict +
                    '}';
        }
    }
}
