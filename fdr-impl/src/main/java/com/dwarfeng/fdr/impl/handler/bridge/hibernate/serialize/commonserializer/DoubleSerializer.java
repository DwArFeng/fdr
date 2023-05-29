package com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.commonserializer;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.Serializer;

/**
 * Double 序列化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class DoubleSerializer implements Serializer {

    @Override
    public String serialize(Object target) {
        return Double.toString((Double) target);
    }
}
