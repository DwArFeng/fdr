package com.dwarfeng.fdr.sdk.bean;

import com.dwarfeng.fdr.sdk.bean.entity.*;
import com.dwarfeng.fdr.sdk.bean.key.FastJsonQuerySupportKey;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.key.QuerySupportKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * FastJson Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.11.0
 */
@Mapper
public interface FastJsonMapper {

    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    FastJsonQuerySupportKey querySupportKeyToFastJson(QuerySupportKey querySupportKey);

    @InheritInverseConfiguration
    QuerySupportKey querySupportKeyFromFastJson(FastJsonQuerySupportKey fastJsonQuerySupportKey);

    FastJsonFilterInfo filterInfoToFastJson(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromFastJson(FastJsonFilterInfo fastJsonFilterInfo);

    FastJsonFilterSupport filterSupportToFastJson(FilterSupport filterSupport);

    @InheritInverseConfiguration
    FilterSupport filterSupportFromFastJson(FastJsonFilterSupport fastJsonFilterSupport);

    FastJsonMapperSupport mapperSupportToFastJson(MapperSupport mapperSupport);

    @InheritInverseConfiguration
    MapperSupport mapperSupportFromFastJson(FastJsonMapperSupport fastJsonMapperSupport);

    FastJsonPoint pointToFastJson(Point point);

    @InheritInverseConfiguration
    Point pointFromFastJson(FastJsonPoint fastJsonPoint);

    FastJsonTriggerInfo triggerInfoToFastJson(TriggerInfo triggerInfo);

    @InheritInverseConfiguration
    TriggerInfo triggerInfoFromFastJson(FastJsonTriggerInfo fastJsonTriggerInfo);

    FastJsonTriggerSupport triggerSupportToFastJson(TriggerSupport triggerSupport);

    @InheritInverseConfiguration
    TriggerSupport triggerSupportFromFastJson(FastJsonTriggerSupport fastJsonTriggerSupport);

    FastJsonQuerySupport querySupportToFastJson(QuerySupport querySupport);

    @InheritInverseConfiguration
    QuerySupport querySupportFromFastJson(FastJsonQuerySupport fastJsonQuerySupport);
}
