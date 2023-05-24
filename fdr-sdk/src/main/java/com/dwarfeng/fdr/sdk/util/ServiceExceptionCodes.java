package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.subgrade.stack.exception.ServiceException;

/**
 * 服务异常代码。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public final class ServiceExceptionCodes {

    private static int EXCEPTION_CODE_OFFSET = 5000;

    public static final ServiceException.Code FILTER_FAILED =
            new ServiceException.Code(offset(0), "filter failed");
    public static final ServiceException.Code FILTER_MAKE_FAILED =
            new ServiceException.Code(offset(1), "filter make failed");
    public static final ServiceException.Code FILTER_TYPE_UNSUPPORTED =
            new ServiceException.Code(offset(2), "filter type unsupported");
    public static final ServiceException.Code FILTER_EXECUTION_FAILED =
            new ServiceException.Code(offset(3), "filter execution failed");
    public static final ServiceException.Code TRIGGER_FAILED =
            new ServiceException.Code(offset(10), "trigger failed");
    public static final ServiceException.Code TRIGGER_MAKE_FAILED =
            new ServiceException.Code(offset(11), "trigger make failed");
    public static final ServiceException.Code TRIGGER_TYPE_UNSUPPORTED =
            new ServiceException.Code(offset(12), "trigger type unsupported");
    public static final ServiceException.Code TRIGGER_EXECUTION_FAILED =
            new ServiceException.Code(offset(13), "trigger execution failed");
    public static final ServiceException.Code POINT_NOT_EXISTS =
            new ServiceException.Code(offset(20), "point not exists");
    public static final ServiceException.Code RECORD_HANDLER_STOPPED =
            new ServiceException.Code(offset(30), "record handler stopped");
    public static final ServiceException.Code CONSUME_HANDLER_STOPPED =
            new ServiceException.Code(offset(31), "consume handler stopped");
    public static final ServiceException.Code MAPPER_FAILED =
            new ServiceException.Code(offset(40), "mapper failed");
    public static final ServiceException.Code MAPPER_MAKE_FAILED =
            new ServiceException.Code(offset(41), "mapper make failed");
    public static final ServiceException.Code MAPPER_TYPE_UNSUPPORTED =
            new ServiceException.Code(offset(42), "mapper type unsupported");
    public static final ServiceException.Code MAPPER_EXECUTION_FAILED =
            new ServiceException.Code(offset(43), "mapper execution failed");
    public static final ServiceException.Code METHOD_NOT_SUPPORTED =
            new ServiceException.Code(offset(50), "method not supported");

    private static int offset(int i) {
        return EXCEPTION_CODE_OFFSET + i;
    }

    /**
     * 获取异常代号的偏移量。
     *
     * @return 异常代号的偏移量。
     */
    public static int getExceptionCodeOffset() {
        return EXCEPTION_CODE_OFFSET;
    }

    /**
     * 设置异常代号的偏移量。
     *
     * @param exceptionCodeOffset 指定的异常代号的偏移量。
     */
    public static void setExceptionCodeOffset(int exceptionCodeOffset) {
        // 设置 EXCEPTION_CODE_OFFSET 的值。
        EXCEPTION_CODE_OFFSET = exceptionCodeOffset;

        // 以新的 EXCEPTION_CODE_OFFSET 为基准，更新异常代码的值。
        FILTER_FAILED.setCode(offset(0));
        FILTER_MAKE_FAILED.setCode(offset(1));
        FILTER_TYPE_UNSUPPORTED.setCode(offset(2));
        FILTER_EXECUTION_FAILED.setCode(offset(3));
        TRIGGER_FAILED.setCode(offset(10));
        TRIGGER_MAKE_FAILED.setCode(offset(11));
        TRIGGER_TYPE_UNSUPPORTED.setCode(offset(12));
        TRIGGER_EXECUTION_FAILED.setCode(offset(13));
        POINT_NOT_EXISTS.setCode(offset(20));
        RECORD_HANDLER_STOPPED.setCode(offset(30));
        CONSUME_HANDLER_STOPPED.setCode(offset(31));
        MAPPER_FAILED.setCode(offset(40));
        MAPPER_MAKE_FAILED.setCode(offset(41));
        MAPPER_TYPE_UNSUPPORTED.setCode(offset(42));
        MAPPER_EXECUTION_FAILED.setCode(offset(43));
        METHOD_NOT_SUPPORTED.setCode(offset(50));
    }

    private ServiceExceptionCodes() {
        throw new IllegalStateException("禁止实例化");
    }
}
