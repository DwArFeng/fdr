package com.dwarfeng.fdr.sdk.handler;

/**
 * 清洗器支持器。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public interface WasherSupporter {

    /**
     * 提供类型。
     *
     * @return 类型。
     */
    String provideType();

    /**
     * 提供标签。
     *
     * @return 标签。
     */
    String provideLabel();

    /**
     * 提供描述。
     *
     * @return 描述。
     */
    String provideDescription();

    /**
     * 提供示例参数。
     *
     * @return 示例参数。
     */
    String provideExampleParam();
}
