package com.dwarfeng.fdr.impl.handler.fnt.handler;

import com.dwarfeng.fdr.stack.exception.TriggerException;
import com.dwarfeng.fdr.stack.handler.Trigger;
import com.dwarfeng.fdr.stack.handler.TriggerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TriggerHandlerImpl implements TriggerHandler {

    @Autowired
    private TriggerHandlerDelegate delegate;

    @Override
    public Trigger make(String pointUuid, String triggerUuid, String content) throws TriggerException {
        return delegate.make(pointUuid, triggerUuid, content);
    }
}