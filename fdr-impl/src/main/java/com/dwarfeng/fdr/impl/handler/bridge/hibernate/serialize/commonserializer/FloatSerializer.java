package com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.commonserializer;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.Serializer;

/**
 * Float 序列化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FloatSerializer implements Serializer {

    @Override
    public String serialize(Object target) {
        return Float.toString((Float) target);
    }
}
