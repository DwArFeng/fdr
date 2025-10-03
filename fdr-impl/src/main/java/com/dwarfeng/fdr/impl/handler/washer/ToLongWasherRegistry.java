package com.dwarfeng.fdr.impl.handler.washer;

import com.dwarfeng.fdr.sdk.handler.washer.AbstractWasher;
import com.dwarfeng.fdr.sdk.handler.washer.AbstractWasherRegistry;
import com.dwarfeng.fdr.sdk.util.Constants;
import com.dwarfeng.fdr.stack.exception.WasherException;
import com.dwarfeng.fdr.stack.exception.WasherMakeException;
import com.dwarfeng.fdr.stack.handler.Washer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 将原始对象转换为长整型数的清洗器注册。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ToLongWasherRegistry extends AbstractWasherRegistry {

    public static final String WASHER_TYPE = "to_long_washer";

    private final ApplicationContext ctx;

    public ToLongWasherRegistry(ApplicationContext ctx) {
        super(WASHER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "将原始对象转换为长整型数的清洗器";
    }

    @Override
    public String provideDescription() {
        return "将原始对象转换为长整型数的清洗器。\n" +
                "如果原始对象的类型是 java.lang.Number 的子类，则直接返回原始对象的 longValue() 方法的返回值；\n" +
                "如果原始对象的类型是 java.lang.String，则尝试将其转换为长整型数，如果转换失败，" +
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
            return ctx.getBean(ToLongWasher.class);
        } catch (Exception e) {
            throw new WasherMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "ToLongWasherRegistry{" +
                "washerType='" + washerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class ToLongWasher extends AbstractWasher {

        @Override
        protected Object doWash(Object rawValue) {
            if (rawValue instanceof Number) {
                return ((Number) rawValue).longValue();
            } else if (rawValue instanceof String) {
                try {
                    return Long.parseLong((String) rawValue);
                } catch (NumberFormatException e) {
                    return Constants.DATA_VALUE_ILLEGAL;
                }
            } else {
                return Constants.DATA_VALUE_ILLEGAL;
            }
        }

        @Override
        public String toString() {
            return "ToLongWasher{}";
        }
    }
}
