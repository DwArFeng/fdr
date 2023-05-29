package com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.commonserializer;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.Serializer;

/**
 * Short 序列化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class ShortSerializer implements Serializer {

    @Override
    public String serialize(Object target) {
        return Short.toString((Short) target);
    }
}
