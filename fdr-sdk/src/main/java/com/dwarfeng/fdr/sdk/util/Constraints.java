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
     * 消息的长度约束。
     */
    public static final int LENGTH_MESSAGE = 100;
    /**
     * 过滤器、触发器类型的长度约束。
     */
    public static final int LENGTH_TYPE = 50;
    /**
     * 查看支持类型的长度约束。
     */
    public static final int LENGTH_CATEGORY = 10;
    /**
     * 查看支持预设的长度约束。
     */
    public static final int LENGTH_PRESET = 100;
    /**
     * 查看支持参数的长度约束。
     */
    public static final int LENGTH_PARAM = 250;

    private Constraints() {
        throw new IllegalStateException("禁止实例化");
    }
}
