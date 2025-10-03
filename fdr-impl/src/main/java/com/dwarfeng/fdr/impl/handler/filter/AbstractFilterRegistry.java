package com.dwarfeng.fdr.impl.handler.filter;

/**
 * 抽象过滤器注册。
 *
 * @author DwArFeng
 * @see com.dwarfeng.fdr.sdk.handler.filter.AbstractFilterRegistry
 * @since 1.7.2
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AbstractFilterRegistry extends com.dwarfeng.fdr.sdk.handler.filter.AbstractFilterRegistry {

    public AbstractFilterRegistry() {
    }

    public AbstractFilterRegistry(String filterType) {
        super(filterType);
    }
}
