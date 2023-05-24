package com.dwarfeng.fdr.impl.handler;

/**
 * 持久数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PersistenceKeeper {

    /**
     * 判断指定的类型是否支持。
     *
     * @param type 指定的类型。
     * @return 指定的类型是否支持。
     */
    boolean supportType(String type);
}
