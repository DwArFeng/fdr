package com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.commonserializer;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.Serializer;

/**
 * Boolean 序列化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class BooleanSerializer implements Serializer {

    @Override
    public String serialize(Object target) {
        return Boolean.toString((Boolean) target);
    }
}
