package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.exception.UnsupportedTriggerTypeException;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.fdr.stack.handler.TriggerHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class TriggerHandlerImpl implements TriggerHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggerHandlerImpl.class);

    private final List<TriggerMaker> triggerMakers;

    public TriggerHandlerImpl(List<TriggerMaker> triggerMakers) {
        this.triggerMakers = Optional.ofNullable(triggerMakers).orElse(Collections.emptyList());
    }

    @Override
    public Trigger make(String type, String param) throws HandlerException {
        try {
            // 生成触发器。
            LOGGER.debug("通过触发器信息构建新的的触发器...");
            TriggerMaker triggerMaker = triggerMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedTriggerTypeException(type));
            Trigger trigger = triggerMaker.makeTrigger(type, param);
            LOGGER.debug("触发器构建成功!");
            LOGGER.debug("触发器: " + trigger);
            return trigger;
        } catch (TriggerException e) {
            throw e;
        } catch (Exception e) {
            throw new TriggerException(e);
        }
    }
}
