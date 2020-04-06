package com.dwarfeng.fdr.impl.handler;

/**
 * 映射器支持器。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface MapperSupporter {

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
     * 提供参数说明。
     *
     * @return 参数说明。
     */
    String provideArgsIllustrate();
}
