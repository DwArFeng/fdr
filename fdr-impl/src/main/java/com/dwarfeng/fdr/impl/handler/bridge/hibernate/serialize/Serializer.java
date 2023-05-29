package com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize;

/**
 * 序列化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface Serializer {

    String serialize(Object target);
}
