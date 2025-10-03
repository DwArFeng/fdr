package com.dwarfeng.fdr.impl.handler.trigger;

/**
 * 抽象触发器注册。
 *
 * @author DwArFeng
 * @see com.dwarfeng.fdr.sdk.handler.trigger.AbstractTriggerRegistry
 * @since 1.7.2
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public abstract class AbstractTriggerRegistry extends com.dwarfeng.fdr.sdk.handler.trigger.AbstractTriggerRegistry {

    public AbstractTriggerRegistry() {
    }

    public AbstractTriggerRegistry(String triggerType) {
        super(triggerType);
    }
}
