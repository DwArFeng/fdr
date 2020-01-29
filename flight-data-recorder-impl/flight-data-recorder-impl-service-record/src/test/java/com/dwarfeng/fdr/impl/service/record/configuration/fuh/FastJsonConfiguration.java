package com.dwarfeng.fdr.impl.service.record.configuration.fuh;

import com.alibaba.fastjson.parser.ParserConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.sdk.bean.entity.FastJsonCategory");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.sdk.bean.entity.FastJsonPoint");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.sdk.bean.entity.FastJsonFilterInfo");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.sdk.bean.entity.FastJsonTriggerInfo");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.sdk.bean.entity.FastJsonFilteredValue");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.sdk.bean.entity.FastJsonPersistenceValue");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.sdk.bean.entity.FastJsonRealtimeValue");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.sdk.bean.entity.FastJsonTriggeredValue");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.impl.handler.fnt.struct.StructuredFilterInfo");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.impl.handler.fnt.struct.StructuredTriggerInfo");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.impl.handler.fnt.preset.BlankConfig");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.impl.handler.fnt.preset.IntegerFilter.Config");
        ParserConfig.getGlobalInstance().addAccept("com.dwarfeng.fdr.impl.handler.fnt.preset.IntegerRangeTrigger.Config");
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}
