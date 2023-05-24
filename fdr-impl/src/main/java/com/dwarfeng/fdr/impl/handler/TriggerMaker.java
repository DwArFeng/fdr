package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.handler.Trigger;

/**
 * 触发器制造器。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public interface TriggerMaker {

    /**
     * 返回制造器是否支持指定的类型。
     *
     * @param type 指定的类型。
     * @return 制造器是否支持指定的类型。
     */
    boolean supportType(String type);

    /**
     * 根据指定的触发器信息生成一个触发器。
     *
     * <p>
     * 可以保证传入的触发器信息中的类型是支持的。
     *
     * @param type  触发器类型。
     * @param param 触发器参数。
     * @return 生成的触发器。
     * @throws TriggerException 触发器异常。
     */
    Trigger makeTrigger(String type, String param) throws TriggerException;
}
