package com.dwarfeng.fdr.impl.handler;

/**
 * 触发器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface TriggerSupporter {

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
     * 提供示例内容。
     *
     * @return 示例内容。
     */
    String provideExampleContent();
}