package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.exception.WasherException;
import com.dwarfeng.fdr.stack.handler.Washer;

/**
 * 清洗器制造器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WasherMaker {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的清洗器信息生成一个清洗器。
     *
     * <p>
     * 可以保证传入的清洗器信息中的类型是支持的。
     *
     * @param type  清洗器类型。
     * @param param 清洗器参数。
     * @return 生成的清洗器。
     * @throws WasherException 清洗器异常。
     */
    Washer makeWasher(String type, String param) throws WasherException;
}
