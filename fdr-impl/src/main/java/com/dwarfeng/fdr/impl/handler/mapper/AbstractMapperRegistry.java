package com.dwarfeng.fdr.impl.handler.mapper;

/**
 * 抽象映射器注册。
 *
 * @author DwArFeng
 * @see com.dwarfeng.fdr.sdk.handler.mapper.AbstractMapperRegistry
 * @since 1.7.2
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AbstractMapperRegistry extends com.dwarfeng.fdr.sdk.handler.mapper.AbstractMapperRegistry {

    public AbstractMapperRegistry() {
    }

    public AbstractMapperRegistry(String mapperType) {
        super(mapperType);
    }
}
