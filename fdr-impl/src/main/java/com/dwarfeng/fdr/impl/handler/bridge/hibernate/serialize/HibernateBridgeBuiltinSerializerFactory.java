package com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize.commonserializer.*;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 内置的序列化器工厂。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class HibernateBridgeBuiltinSerializerFactory extends AbstractSerializerFactory {

    @Override
    protected Map<Class<?>, Serializer> provideSerializerMap() {
        LinkedHashMap<Class<?>, Serializer> result = new LinkedHashMap<>();
        result.put(Character.class, new CharSerializer());
        result.put(String.class, new IdentitySerializer());
        result.put(Boolean.class, new BooleanSerializer());
        result.put(Byte.class, new ByteSerializer());
        result.put(Short.class, new ShortSerializer());
        result.put(Integer.class, new IntegerSerializer());
        result.put(Long.class, new LongSerializer());
        result.put(Float.class, new FloatSerializer());
        result.put(Double.class, new DoubleSerializer());
        result.put(Object.class, new ToStringSerializer());
        return result;
    }
}
