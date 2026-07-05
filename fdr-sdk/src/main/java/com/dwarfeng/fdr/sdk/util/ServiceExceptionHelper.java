package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.fdr.stack.exception.*;
import com.dwarfeng.subgrade.stack.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 异常的帮助工具类。
 *
 * @author DwArFeng
 * @since 4.0.0
 */
public final class ServiceExceptionHelper {

    /**
     * 向指定的映射中添加 fdr 默认的目标映射。
     *
     * <p>
     * 该方法可以在配置类中快速的搭建目标映射。
     *
     * @param map 指定的映射，允许为 <code>null</code>。
     * @return 添加了默认目标的映射。
     */
    public static Map<Class<? extends Exception>, ServiceException.Code> putDefaultDestination(
            Map<Class<? extends Exception>, ServiceException.Code> map
    ) {
        if (Objects.isNull(map)) {
            map = new HashMap<>();
        }

        map.put(FilterException.class, ServiceExceptionCodes.FILTER_FAILED);
        map.put(FilterMakeException.class, ServiceExceptionCodes.FILTER_MAKE_FAILED);
        map.put(UnsupportedFilterTypeException.class, ServiceExceptionCodes.FILTER_TYPE_UNSUPPORTED);
        map.put(FilterExecutionException.class, ServiceExceptionCodes.FILTER_EXECUTION_FAILED);
        map.put(TriggerException.class, ServiceExceptionCodes.TRIGGER_FAILED);
        map.put(TriggerMakeException.class, ServiceExceptionCodes.TRIGGER_MAKE_FAILED);
        map.put(UnsupportedTriggerTypeException.class, ServiceExceptionCodes.TRIGGER_TYPE_UNSUPPORTED);
        map.put(TriggerExecutionException.class, ServiceExceptionCodes.TRIGGER_EXECUTION_FAILED);
        map.put(PointNotExistsException.class, ServiceExceptionCodes.POINT_NOT_EXISTS);
        map.put(RecordHandlerStoppedException.class, ServiceExceptionCodes.RECORD_HANDLER_STOPPED);
        map.put(ConsumeStoppedException.class, ServiceExceptionCodes.CONSUME_HANDLER_STOPPED);
        map.put(MapperException.class, ServiceExceptionCodes.MAPPER_FAILED);
        map.put(MapperMakeException.class, ServiceExceptionCodes.MAPPER_MAKE_FAILED);
        map.put(UnsupportedMapperTypeException.class, ServiceExceptionCodes.MAPPER_TYPE_UNSUPPORTED);
        map.put(MapperExecutionException.class, ServiceExceptionCodes.MAPPER_EXECUTION_FAILED);
        map.put(FunctionNotSupportedException.class, ServiceExceptionCodes.FUNCTION_NOT_SUPPORTED);
        map.put(LatestNotSupportedException.class, ServiceExceptionCodes.LATEST_NOT_SUPPORTED);
        map.put(LookupNotSupportedException.class, ServiceExceptionCodes.LOOKUP_NOT_SUPPORTED);
        map.put(QueryNotSupportedException.class, ServiceExceptionCodes.QUERY_NOT_SUPPORTED);
        map.put(NativeQueryNotSupportedException.class, ServiceExceptionCodes.NATIVE_QUERY_NOT_SUPPORTED);
        map.put(WasherException.class, ServiceExceptionCodes.WASHER_FAILED);
        map.put(WasherMakeException.class, ServiceExceptionCodes.WASHER_MAKE_FAILED);
        map.put(UnsupportedWasherTypeException.class, ServiceExceptionCodes.WASHER_TYPE_UNSUPPORTED);
        map.put(WasherExecutionException.class, ServiceExceptionCodes.WASHER_EXECUTION_FAILED);
        map.put(KeepException.class, ServiceExceptionCodes.KEEP_FAILED);
        map.put(UpdateException.class, ServiceExceptionCodes.UPDATE_FAILED);
        map.put(LatestException.class, ServiceExceptionCodes.LATEST_FAILED);
        map.put(PersistException.class, ServiceExceptionCodes.PERSIST_FAILED);
        map.put(RecordException.class, ServiceExceptionCodes.RECORD_FAILED);
        map.put(LookupException.class, ServiceExceptionCodes.LOOKUP_FAILED);
        map.put(NativeQueryException.class, ServiceExceptionCodes.NATIVE_QUERY_FAILED);
        map.put(QueryException.class, ServiceExceptionCodes.QUERY_FAILED);
        map.put(KeeperNotSupportedException.class, ServiceExceptionCodes.KEEPER_NOT_SUPPORTED);
        map.put(PersisterNotSupportedException.class, ServiceExceptionCodes.PERSISTER_NOT_SUPPORTED);
        map.put(FetcherNotExistsException.class, ServiceExceptionCodes.FETCHER_NOT_EXISTS);
        map.put(FetcherException.class, ServiceExceptionCodes.FETCHER_FAILED);
        map.put(FetcherExecutionException.class, ServiceExceptionCodes.FETCHER_EXECUTION_FAILED);
        map.put(FetcherMakeException.class, ServiceExceptionCodes.FETCHER_MAKE_FAILED);
        map.put(FetcherSessionException.class, ServiceExceptionCodes.FETCHER_SESSION_FAILED);
        map.put(UnsupportedFetcherTypeException.class, ServiceExceptionCodes.FETCHER_TYPE_UNSUPPORTED);
        return map;
    }

    private ServiceExceptionHelper() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
