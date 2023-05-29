package com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.exception.TypeNotSupportException;

import java.util.Collection;

/**
 * 序列化器工厂。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface SerializerFactory {

    /**
     * 获取该序列化器工厂支持的类型。
     *
     * @return 该序列化器工厂支持的类型。
     */
    Collection<Class<?>> getSupportedTypes();

    /**
     * 获取指定类型的序列化器。
     *
     * @param type 指定的类型。
     * @return 指定类型的序列化器。
     * @throws TypeNotSupportException 指定的类型不被支持。
     */
    <T> Serializer getSerializer(Class<T> type) throws TypeNotSupportException;
}
