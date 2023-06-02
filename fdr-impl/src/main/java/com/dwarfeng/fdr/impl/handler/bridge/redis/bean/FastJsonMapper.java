package com.dwarfeng.fdr.impl.handler.bridge.redis.bean;

import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Mapper
public interface FastJsonMapper {

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    RedisBridgeFastJsonNormalData redisBridgeNormalDataToFastJson(
            RedisBridgeNormalData redisBridgeNormalData
    );

    @InheritInverseConfiguration
    RedisBridgeNormalData redisBridgeNormalDataFromFastJson(
            RedisBridgeFastJsonNormalData redisBridgeFastJsonNormalData
    );

    RedisBridgeFastJsonFilteredData redisBridgeFilteredDataToFastJson(
            RedisBridgeFilteredData redisBridgeFilteredData
    );

    @InheritInverseConfiguration
    RedisBridgeFilteredData redisBridgeFilteredDataFromFastJson(
            RedisBridgeFastJsonFilteredData redisBridgeFastJsonFilteredData
    );

    RedisBridgeFastJsonTriggeredData redisBridgeTriggeredDataToFastJson(
            RedisBridgeTriggeredData redisBridgeTriggeredData
    );

    @InheritInverseConfiguration
    RedisBridgeTriggeredData redisBridgeTriggeredDataFromFastJson(
            RedisBridgeFastJsonTriggeredData redisBridgeFastJsonTriggeredData
    );
}
