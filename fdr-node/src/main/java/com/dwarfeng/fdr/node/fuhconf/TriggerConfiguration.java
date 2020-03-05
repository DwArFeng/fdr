package com.dwarfeng.fdr.node.fuhconf;

import com.dwarfeng.fdr.impl.handler.TriggerHandlerImpl;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TriggerConfiguration {

    @Bean
    public Map<LongIdKey, TriggerHandlerImpl.TriggerMetaData> longIdKeyTriggerMetaDataMap() {
        return new HashMap<>();
    }
}
