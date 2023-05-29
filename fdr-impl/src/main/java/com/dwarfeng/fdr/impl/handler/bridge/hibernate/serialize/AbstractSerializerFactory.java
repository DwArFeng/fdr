package com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.exception.TypeNotSupportException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 序列化器工厂的抽象实现。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AbstractSerializerFactory implements SerializerFactory {

    protected final Map<Class<?>, Serializer> serializerMap;

    protected final Map<Class<?>, Serializer> cache = new HashMap<>();

    public AbstractSerializerFactory() {
        serializerMap = provideSerializerMap();
    }

    @Override
    public Collection<Class<?>> getSupportedTypes() {
        return serializerMap.keySet();
    }

    @Override
    public <T> Serializer getSerializer(Class<T> type) throws TypeNotSupportException {
        if (cache.containsKey(type)) {
            return cache.get(type);
        } else {
            Class<?> aClass = SerializeUtil.findClass(serializerMap.keySet(), type);
            if (Objects.isNull(aClass)) {
                throw new TypeNotSupportException(type);
            }
            Serializer serializer = serializerMap.get(aClass);
            cache.put(type, serializer);
            return serializer;
        }
    }

    /**
     * 提供序列化器的映射。
     *
     * <p>
     * 该方法会在初始化时被调用一次，返回的映射将会被用于提供序列化器。
     *
     * <p>
     * 返回的映射需要保证每一个 key 对应的 value 的泛型都是 key 类型或 key 的超类类型。
     *
     * @return 序列化器的映射。
     */
    protected abstract Map<Class<?>, Serializer> provideSerializerMap();
}
