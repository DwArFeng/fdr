package com.dwarfeng.fdr.impl.handler.bridge.hibernate.serialize;

import java.util.Collection;

/**
 * 序列化工具类。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class SerializeUtil {

    /**
     * 在指定的类空间中查找指定的类。
     *
     * @param classSpace  指定的类空间。
     * @param targetClass 指定的类。
     * @return 指定的类在指定的类空间中的实例，如果没有找到，则返回 null。
     */
    public static Class<?> findClass(Collection<Class<?>> classSpace, Class<?> targetClass) {
        for (Class<?> aClass : classSpace) {
            if (aClass.equals(targetClass)) {
                return aClass;
            }
        }
        return null;
    }

    private SerializeUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
