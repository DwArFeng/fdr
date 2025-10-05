package com.dwarfeng.fdr.impl.handler.mapper;

/**
 * 一对一映射器。
 *
 * <p>
 * 一对一映射器是一个抽象的映射器，它执行映射操作时，会遍历数据表中的所有序列，并将一个序列映射为另一个序列。
 *
 * @author DwArFeng
 * @see com.dwarfeng.fdr.sdk.handler.mapper.OneToOneMapper
 * @since 2.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class OneToOneMapper extends com.dwarfeng.fdr.sdk.handler.mapper.OneToOneMapper {

    public OneToOneMapper() {
    }
}
