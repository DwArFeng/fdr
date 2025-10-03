package com.dwarfeng.fdr.impl.handler.washer;

/**
 * 抽象清洗器注册。
 *
 * @author DwArFeng
 * @see com.dwarfeng.fdr.sdk.handler.washer.AbstractWasherRegistry
 * @since 2.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
public abstract class AbstractWasherRegistry extends com.dwarfeng.fdr.sdk.handler.washer.AbstractWasherRegistry {

    public AbstractWasherRegistry() {
    }

    public AbstractWasherRegistry(String washerType) {
        super(washerType);
    }
}
