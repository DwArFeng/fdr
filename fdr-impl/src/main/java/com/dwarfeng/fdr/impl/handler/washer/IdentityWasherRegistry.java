package com.dwarfeng.fdr.impl.handler.washer;

import com.dwarfeng.fdr.stack.exception.WasherException;
import com.dwarfeng.fdr.stack.exception.WasherMakeException;
import com.dwarfeng.fdr.stack.handler.Washer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 本征清洗器注册。
 *
 * @author DwArFeng
 * @since 2.2.2
 */
@Component
public class IdentityWasherRegistry extends AbstractWasherRegistry {

    public static final String WASHER_TYPE = "identity_washer";

    private final ApplicationContext ctx;

    public IdentityWasherRegistry(ApplicationContext ctx) {
        super(WASHER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public String provideLabel() {
        return "本征清洗器";
    }

    @Override
    public String provideDescription() {
        return "不对数据进行任何处理，直接返回原始数据。";
    }

    @Override
    public String provideExampleParam() {
        return "";
    }

    @Override
    public Washer makeWasher(String type, String param) throws WasherException {
        try {
            return ctx.getBean(IdentityWasher.class);
        } catch (Exception e) {
            throw new WasherMakeException(e);
        }
    }

    @Override
    public String toString() {
        return "IdentityWasherRegistry{" +
                "washerType='" + washerType + '\'' +
                '}';
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class IdentityWasher extends AbstractWasher {

        @Override
        protected Object doWash(Object rawValue) {
            return rawValue;
        }

        @Override
        public String toString() {
            return "IdentityWasher{}";
        }
    }
}
