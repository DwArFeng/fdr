package com.dwarfeng.fdr.impl.handler.washer;

import com.dwarfeng.fdr.sdk.util.Constants;
import com.dwarfeng.fdr.stack.exception.WasherException;
import com.dwarfeng.fdr.stack.exception.WasherMakeException;
import com.dwarfeng.fdr.stack.handler.Washer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 将原始对象转换为双精度浮点数的清洗器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ToDoubleWasherRegistry extends AbstractWasherRegistry {

    public static final String WASHER_TYPE = "to_double_washer";

    private final ApplicationContext ctx;

    public ToDoubleWasherRegistry(ApplicationContext ctx) {
        super(WASHER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "将原始对象转换为双精度浮点数的清洗器";
    }

    @Override
    public String provideDescription() {
        return "将原始对象转换为双精度浮点数的清洗器。\n" +
                "如果原始对象的类型是 java.lang.Number 的子类，则直接返回原始对象的 doubleValue() 方法的返回值；\n" +
                "如果原始对象的类型是 java.lang.String，则尝试将其转换为双精度浮点数，如果转换失败，" +
                "则将其转换为 Constants.DATA_VALUE_ILLEGAL；\n" +
                "其余情况均将其转换为 Constants.DATA_VALUE_ILLEGAL。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Washer makeWasher(String type, String param) throws WasherException {
        try {
            return ctx.getBean(ToDoubleWasher.class);
        } catch (Exception e) {
            throw new WasherMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "ToDoubleWasherRegistry{" +
                "washerType='" + washerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ToDoubleWasher extends AbstractWasher {

        @Override
        protected Object doWash(Object rawValue) {
            if (rawValue instanceof Number) {
                return ((Number) rawValue).doubleValue();
            } else if (rawValue instanceof String) {
                try {
                    return Double.parseDouble((String) rawValue);
                } catch (NumberFormatException e) {
                    return Constants.DATA_VALUE_ILLEGAL;
                }
            } else {
                return Constants.DATA_VALUE_ILLEGAL;
            }
        }

        @Override
        public String toString() {
            return "ToDoubleWasher{}";
        }
    }
}
