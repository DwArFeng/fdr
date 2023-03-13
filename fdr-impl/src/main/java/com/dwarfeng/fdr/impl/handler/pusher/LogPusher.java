package com.dwarfeng.fdr.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonFilteredValue;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonPersistenceValue;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonRealtimeValue;
import com.dwarfeng.fdr.sdk.bean.entity.FastJsonTriggeredValue;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 将信息输出至日志的推送器。
 *
 * @author DwArFeng
 * @since 1.7.2
 */
@Component
public class LogPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "log";

    private static final Logger LOGGER = LoggerFactory.getLogger(LogPusher.class);

    private static final String LEVEL_TRACE = "TRACE";
    private static final String LEVEL_DEBUG = "DEBUG";
    private static final String LEVEL_INFO = "INFO";
    private static final String LEVEL_WARN = "WARN";
    private static final String LEVEL_ERROR = "ERROR";

    @Value("${pusher.log.log_level}")
    private String logLevel;

    public LogPusher() {
        super(PUSHER_TYPE);
    }

    @Override
    public void dataFiltered(FilteredValue filteredValue) throws HandlerException {
        String title = "推送被过滤值:";
        String message = JSON.toJSONString(FastJsonFilteredValue.of(filteredValue), true);
        logData(title, message);
    }

    @Override
    public void dataFiltered(List<FilteredValue> filteredValues) throws HandlerException {
        for (FilteredValue filteredValue : filteredValues) {
            dataFiltered(filteredValue);
        }
    }

    @Override
    public void dataTriggered(TriggeredValue triggeredValue) throws HandlerException {
        String title = "推送被触发值:";
        String message = JSON.toJSONString(FastJsonTriggeredValue.of(triggeredValue), true);
        logData(title, message);
    }

    @Override
    public void dataTriggered(List<TriggeredValue> triggeredValues) throws HandlerException {
        for (TriggeredValue triggeredValue : triggeredValues) {
            dataTriggered(triggeredValue);
        }
    }

    @Override
    public void realtimeUpdated(RealtimeValue realtimeValue) throws HandlerException {
        String title = "推送更新实时值:";
        String message = JSON.toJSONString(FastJsonRealtimeValue.of(realtimeValue), true);
        logData(title, message);
    }

    @Override
    public void realtimeUpdated(List<RealtimeValue> realtimeValues) throws HandlerException {
        for (RealtimeValue realtimeValue : realtimeValues) {
            realtimeUpdated(realtimeValue);
        }
    }

    @Override
    public void persistenceRecorded(PersistenceValue persistenceValue) throws HandlerException {
        String title = "推送记录持久化值:";
        String message = JSON.toJSONString(FastJsonPersistenceValue.of(persistenceValue), true);
        logData(title, message);
    }

    @Override
    public void persistenceRecorded(List<PersistenceValue> persistenceValues) throws HandlerException {
        for (PersistenceValue persistenceValue : persistenceValues) {
            persistenceRecorded(persistenceValue);
        }
    }

    @Override
    public void recordReset() throws HandlerException {
        String title = "记录功能重置:";
        String message = StringUtils.EMPTY;
        logData(title, message);
    }

    @Override
    public void mapReset() throws HandlerException {
        String title = "映射功能重置:";
        String message = StringUtils.EMPTY;
        logData(title, message);
    }

    private void logData(String title, String message) throws HandlerException {
        String logLevel = this.logLevel.toUpperCase();
        switch (logLevel) {
            case LEVEL_TRACE:
                LOGGER.trace(title);
                LOGGER.trace(message);
                return;
            case LEVEL_DEBUG:
                LOGGER.debug(title);
                LOGGER.debug(message);
                return;
            case LEVEL_INFO:
                LOGGER.info(title);
                LOGGER.info(message);
                return;
            case LEVEL_WARN:
                LOGGER.warn(title);
                LOGGER.warn(message);
                return;
            case LEVEL_ERROR:
                LOGGER.error(title);
                LOGGER.error(message);
                return;
            default:
                throw new HandlerException("未知的日志等级: " + logLevel);
        }
    }

    @Override
    public String toString() {
        return "LogPusher{" +
                "logLevel='" + logLevel + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }
}
