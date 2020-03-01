package com.dwarfeng.fdr.sdk.util;

/**
 * 约束类。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class Constraints {
    /**
     * 名称的长度约束。
     */
    public static final int LENGTH_NAME = 50;
    /**
     * 备注的长度约束。
     */
    public static final int LENGTH_REMARK = 100;
    /**
     * 值的长度约束。
     */
    public static final int LENGTH_VALUE = 40;
    /**
     * 消息的长度约束。
     */
    public static final int LENGTH_MESSAGE = 100;

    private Constraints() {
        throw new IllegalStateException("禁止实例化");
    }
}