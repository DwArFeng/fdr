package com.dwarfeng.fdr.node.configuration;

import com.alibaba.fastjson.parser.ParserConfig;
import com.dwarfeng.fdr.sdk.bean.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FastJsonConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(FastJsonConfiguration.class);

    public FastJsonConfiguration() {
        LOGGER.info("正在配置 FastJson autotype 白名单");
        //实体对象。
        ParserConfig.getGlobalInstance().addAccept(FastJsonPoint.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonFilterInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonTriggerInfo.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonFilterSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonTriggerSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonMapperSupport.class.getCanonicalName());
        ParserConfig.getGlobalInstance().addAccept(FastJsonLookupSupport.class.getCanonicalName());
        LOGGER.debug("FastJson autotype 白名单配置完毕");
    }
}
