package com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.commonserializer;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.Serializer;

/**
 * 格式化序列化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FormatSerializer implements Serializer {

    private final String format;

    public FormatSerializer(String format) {
        this.format = format;
    }

    @Override
    public String serialize(Object target) {
        return String.format(format, target);
    }
}
