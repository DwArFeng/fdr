package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupSessionInfo;
import com.dwarfeng.fdr.stack.exception.MappingLookupSessionCanceledException;
import com.dwarfeng.fdr.stack.exception.MappingLookupSessionNotExistsException;
import com.dwarfeng.fdr.stack.exception.MappingLookupTimeoutException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.List;

/**
 * 持久值映射查询处理器。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public interface PersistenceValueMappingLookupHandler extends Handler {

    /**
     * 映射查询。
     *
     * @param mappingLookupInfo 映射查询信息。
     * @return 映射查询的结果。
     * @throws HandlerException                      处理器异常。
     * @throws MappingLookupSessionCanceledException 会话取消。
     */
    List<TimedValue> mappingLookup(MappingLookupInfo mappingLookupInfo) throws HandlerException;

    /**
     * 异步式执行映射查询。
     *
     * @param mappingLookupInfo 映射查询信息。
     * @return 映射查询的会话主键。
     * @throws HandlerException 处理器异常。
     */
    LongIdKey mappingLookupAsync(MappingLookupInfo mappingLookupInfo) throws HandlerException;

    /**
     * 取消异步式会话。
     *
     * @param sessionKey 映射查询的会话主键。
     * @throws HandlerException                       处理器异常。
     * @throws MappingLookupSessionNotExistsException 会话主键不存在。
     */
    void cancel(LongIdKey sessionKey) throws HandlerException;

    /**
     * 异步式获取查询结果。
     *
     * <p>
     * 在映射查询结束前，该方法会一直处于阻塞状态。
     *
     * @param sessionKey 映射查询的会话主键。
     * @return 映射查询的结果。
     * @throws HandlerException                       处理器异常。
     * @throws MappingLookupSessionNotExistsException 会话主键不存在。
     * @throws MappingLookupSessionCanceledException  会话取消。
     */
    List<TimedValue> getResult(LongIdKey sessionKey) throws HandlerException;

    /**
     * 异步式获取查询结果。
     *
     * <p>
     * 在映射查询结束前，该方法会一直处于阻塞状态。
     * <p>
     * 当阻塞时间超过了设定的超时时间后，方法直接抛出 {@link MappingLookupTimeoutException}。
     *
     * @param sessionKey 映射查询的会话主键。
     * @param timeout    超时时间。
     * @return 映射查询的结果。
     * @throws HandlerException                       处理器异常。
     * @throws MappingLookupSessionNotExistsException 会话主键不存在。
     * @throws MappingLookupTimeoutException          会话超时。
     * @throws MappingLookupSessionCanceledException  会话取消。
     */
    List<TimedValue> getResult(LongIdKey sessionKey, long timeout) throws HandlerException;

    /**
     * 获取指定的主键对应的会话信息。
     *
     * @param sessionKey 映射查询的会话主键。
     * @return 指定的主键对应的会话信息。
     * @throws HandlerException                       处理器异常。
     * @throws MappingLookupSessionNotExistsException 会话主键不存在。
     */
    MappingLookupSessionInfo getSessionInfo(LongIdKey sessionKey) throws HandlerException;

    /**
     * 获取处理器维护的所有会话信息。
     *
     * @return 处理器维护的所有会话信息。
     * @throws HandlerException 处理器异常。
     */
    List<MappingLookupSessionInfo> getSessionInfos() throws HandlerException;

    /**
     * 清理所有已经结束并超过最大保持时长的会话。
     *
     * @throws HandlerException 处理器异常。
     */
    void cleanup() throws HandlerException;
}
