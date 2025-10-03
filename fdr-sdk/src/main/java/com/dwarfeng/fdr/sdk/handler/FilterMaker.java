package com.dwarfeng.fdr.sdk.handler;

import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.handler.Filter;

/**
 * 过滤器制造器。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public interface FilterMaker {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的过滤器信息生成一个过滤器。
     *
     * <p>
     * 可以保证传入的过滤器信息中的类型是支持的。
     *
     * @param type  过滤器类型。
     * @param param 过滤器参数。
     * @return 生成的过滤器。
     * @throws FilterException 过滤器异常。
     */
    Filter makeFilter(String type, String param) throws FilterException;
}
