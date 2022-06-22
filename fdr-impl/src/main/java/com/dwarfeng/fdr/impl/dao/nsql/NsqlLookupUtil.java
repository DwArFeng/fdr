package com.dwarfeng.fdr.impl.dao.nsql;

import java.util.Arrays;
import java.util.Objects;

/**
 * 本地SQL查询工具类。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public final class NsqlLookupUtil {

    /**
     * 对象验证。
     *
     * @param objs      待验证的对象组成的数组。
     * @param classes   对象的类。
     * @param nullables 是否允许对象为空。
     * @throws IllegalArgumentException 验证不通过时抛出的异常。
     */
    public static void objsValidation(Object[] objs, Class<?>[] classes, boolean[] nullables) throws IllegalArgumentException {
        if (objs.length != classes.length || objs.length != nullables.length) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
        for (int i = 0; i < objs.length; i++) {
            Object object = objs[i];
            Class<?> clazz = classes[i];
            boolean nullable = nullables[i];
            if (Objects.isNull(object) && !nullable) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }
            if (!clazz.isInstance(object)) {
                throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
            }
        }
    }

    private NsqlLookupUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
