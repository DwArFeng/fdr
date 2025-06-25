package com.dwarfeng.fdr.sdk.bean;

import com.dwarfeng.fdr.sdk.bean.dto.*;
import com.dwarfeng.fdr.sdk.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.dto.*;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.JSFixedFastJsonLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * Bean 映射器。
 *
 * <p>
 * 该映射器中包含了 <code>sdk</code> 模块中所有实体与 <code>stack</code> 模块中对应实体的映射方法。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@Mapper
public interface BeanMapper {

    // -----------------------------------------------------------Subgrade Key-----------------------------------------------------------
    FastJsonLongIdKey longIdKeyToFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromFastJson(FastJsonLongIdKey fastJsonLongIdKey);

    FastJsonStringIdKey stringIdKeyToFastJson(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromFastJson(FastJsonStringIdKey fastJsonStringIdKey);

    JSFixedFastJsonLongIdKey longIdKeyToJSFixedFastJson(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromJSFixedFastJson(JSFixedFastJsonLongIdKey jSFixedFastJsonLongIdKey);

    WebInputLongIdKey longIdKeyToWebInput(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromWebInput(WebInputLongIdKey webInputLongIdKey);

    // -----------------------------------------------------------Fdr Entity-----------------------------------------------------------
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

    FastJsonWasherInfo washerInfoToFastJson(WasherInfo washerInfo);

    @InheritInverseConfiguration
    WasherInfo washerInfoFromFastJson(FastJsonWasherInfo fastJsonWasherInfo);

    FastJsonWasherSupport washerSupportToFastJson(WasherSupport washerSupport);

    @InheritInverseConfiguration
    WasherSupport washerSupportFromFastJson(FastJsonWasherSupport fastJsonWasherSupport);

    JSFixedFastJsonFilterInfo filterInfoToJSFixedFastJson(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromJSFixedFastJson(JSFixedFastJsonFilterInfo jSFixedFastJsonFilterInfo);

    JSFixedFastJsonPoint pointToJSFixedFastJson(Point point);

    @InheritInverseConfiguration
    Point pointFromJSFixedFastJson(JSFixedFastJsonPoint jSFixedFastJsonPoint);

    JSFixedFastJsonTriggerInfo triggerInfoToJSFixedFastJson(TriggerInfo triggerInfo);

    @InheritInverseConfiguration
    TriggerInfo triggerInfoFromJSFixedFastJson(JSFixedFastJsonTriggerInfo jSFixedFastJsonTriggerInfo);

    JSFixedFastJsonWasherInfo washerInfoToJSFixedFastJson(WasherInfo washerInfo);

    @InheritInverseConfiguration
    WasherInfo washerInfoFromJSFixedFastJson(JSFixedFastJsonWasherInfo jSFixedFastJsonWasherInfo);

    WebInputFilterInfo filterInfoToWebInput(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromWebInput(WebInputFilterInfo webInputFilterInfo);

    WebInputPoint pointToWebInput(Point point);

    @InheritInverseConfiguration
    Point pointFromWebInput(WebInputPoint webInputPoint);

    WebInputTriggerInfo triggerInfoToWebInput(TriggerInfo triggerInfo);

    @InheritInverseConfiguration
    TriggerInfo triggerInfoFromWebInput(WebInputTriggerInfo webInputTriggerInfo);

    WebInputWasherInfo washerInfoToWebInput(WasherInfo washerInfo);

    @InheritInverseConfiguration
    WasherInfo washerInfoFromWebInput(WebInputWasherInfo webInputWasherInfo);

    // -----------------------------------------------------------Fdr DTO-----------------------------------------------------------
    FastJsonFilteredData filteredDataToFastJson(FilteredData filteredData);

    @InheritInverseConfiguration
    FilteredData filteredDataFromFastJson(FastJsonFilteredData fastJsonFilteredData);

    FastJsonFilteredLookupResult filteredLookupResultToFastJson(LookupResult<FilteredData> filteredLookupResult);

    @InheritInverseConfiguration
    LookupResult<FilteredData> filteredLookupResultFromFastJson(
            FastJsonFilteredLookupResult fastJsonFilteredLookupResult
    );

    FastJsonLookupInfo lookupInfoToFastJson(LookupInfo lookupInfo);

    @InheritInverseConfiguration
    LookupInfo lookupInfoFromFastJson(FastJsonLookupInfo fastJsonLookupInfo);

    FastJsonNativeQueryInfo nativeQueryInfoToFastJson(NativeQueryInfo nativeQueryInfo);

    @InheritInverseConfiguration
    NativeQueryInfo nativeQueryInfoFromFastJson(FastJsonNativeQueryInfo fastJsonNativeQueryInfo);

    FastJsonNormalData normalDataToFastJson(NormalData normalData);

    @InheritInverseConfiguration
    NormalData normalDataFromFastJson(FastJsonNormalData fastJsonNormalData);

    FastJsonNormalLookupResult normalLookupResultToFastJson(LookupResult<NormalData> normalLookupResult);

    @InheritInverseConfiguration
    LookupResult<NormalData> normalLookupResultFromFastJson(FastJsonNormalLookupResult fastJsonNormalLookupResult);

    FastJsonQueryInfo queryInfoToFastJson(QueryInfo queryInfo);

    @InheritInverseConfiguration
    QueryInfo queryInfoFromFastJson(FastJsonQueryInfo fastJsonQueryInfo);

    FastJsonQueryResult queryResultToFastJson(QueryResult queryResult);

    @InheritInverseConfiguration
    QueryResult queryResultFromFastJson(FastJsonQueryResult fastJsonQueryResult);

    FastJsonRecordInfo recordInfoToFastJson(RecordInfo recordInfo);

    @InheritInverseConfiguration
    RecordInfo recordInfoFromFastJson(FastJsonRecordInfo fastJsonRecordInfo);

    FastJsonTriggeredData triggeredDataToFastJson(TriggeredData triggeredData);

    @InheritInverseConfiguration
    TriggeredData triggeredDataFromFastJson(FastJsonTriggeredData fastJsonTriggeredData);

    FastJsonTriggeredLookupResult triggeredLookupResultToFastJson(LookupResult<TriggeredData> triggeredLookupResult);

    @InheritInverseConfiguration
    LookupResult<TriggeredData> triggeredLookupResultFromFastJson(
            FastJsonTriggeredLookupResult fastJsonTriggeredLookupResult
    );

    JSFixedFastJsonFilteredData filteredDataToJSFixedFastJson(FilteredData filteredData);

    @InheritInverseConfiguration
    FilteredData filteredDataFromJSFixedFastJson(JSFixedFastJsonFilteredData jSFixedFastJsonFilteredData);

    JSFixedFastJsonFilteredLookupResult filteredLookupResultToJSFixedFastJson(
            LookupResult<FilteredData> filteredLookupResult
    );

    @InheritInverseConfiguration
    LookupResult<FilteredData> filteredLookupResultFromJSFixedFastJson(
            JSFixedFastJsonFilteredLookupResult jSFixedFastJsonFilteredLookupResult
    );

    JSFixedFastJsonNormalData normalDataToJSFixedFastJson(NormalData normalData);

    @InheritInverseConfiguration
    NormalData normalDataFromJSFixedFastJson(JSFixedFastJsonNormalData jSFixedFastJsonNormalData);

    JSFixedFastJsonNormalLookupResult normalLookupResultToJSFixedFastJson(LookupResult<NormalData> normalLookupResult);

    @InheritInverseConfiguration
    LookupResult<NormalData> normalLookupResultFromJSFixedFastJson(
            JSFixedFastJsonNormalLookupResult jSFixedFastJsonNormalLookupResult
    );

    JSFixedFastJsonQueryResult queryResultToJSFixedFastJson(QueryResult queryResult);

    @InheritInverseConfiguration
    QueryResult queryResultFromJSFixedFastJson(JSFixedFastJsonQueryResult jSFixedFastJsonQueryResult);

    JSFixedFastJsonRecordInfo recordInfoToJSFixedFastJson(RecordInfo recordInfo);

    @InheritInverseConfiguration
    RecordInfo recordInfoFromJSFixedFastJson(JSFixedFastJsonRecordInfo jSFixedFastJsonRecordInfo);

    JSFixedFastJsonTriggeredData triggeredDataToJSFixedFastJson(TriggeredData triggeredData);

    @InheritInverseConfiguration
    TriggeredData triggeredDataFromJSFixedFastJson(JSFixedFastJsonTriggeredData jSFixedFastJsonTriggeredData);

    JSFixedFastJsonTriggeredLookupResult triggeredLookupResultToJSFixedFastJson(
            LookupResult<TriggeredData> triggeredLookupResult
    );

    @InheritInverseConfiguration
    LookupResult<TriggeredData> triggeredLookupResultFromJSFixedFastJson(
            JSFixedFastJsonTriggeredLookupResult jSFixedFastJsonTriggeredLookupResult
    );

    WebInputLookupInfo lookupInfoToWebInput(LookupInfo lookupInfo);

    @InheritInverseConfiguration
    LookupInfo lookupInfoFromWebInput(WebInputLookupInfo webInputLookupInfo);

    WebInputNativeQueryInfo nativeQueryInfoToWebInput(NativeQueryInfo nativeQueryInfo);

    @InheritInverseConfiguration
    NativeQueryInfo nativeQueryInfoFromWebInput(WebInputNativeQueryInfo webInputNativeQueryInfo);

    WebInputQueryInfo queryInfoToWebInput(QueryInfo queryInfo);

    @InheritInverseConfiguration
    QueryInfo queryInfoFromWebInput(WebInputQueryInfo webInputQueryInfo);
}
