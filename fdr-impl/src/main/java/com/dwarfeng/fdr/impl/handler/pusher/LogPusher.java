package com.dwarfeng.fdr.impl.handler.pusher;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fdr.sdk.bean.dto.FastJsonFilteredData;
import com.dwarfeng.fdr.sdk.bean.dto.FastJsonNormalData;
import com.dwarfeng.fdr.sdk.bean.dto.FastJsonTriggeredData;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
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
    public void normalUpdated(NormalData normalData) throws HandlerException {
        String title = "推送更新一般数据:";
        String message = JSON.toJSONString(FastJsonNormalData.of(normalData), true);
        logData(title, message);
    }

    @Override
    public void normalUpdated(List<NormalData> normalDatas) throws HandlerException {
        for (NormalData normalRecord : normalDatas) {
            normalUpdated(normalRecord);
        }
    }

    @Override
    public void normalRecorded(NormalData normalData) throws HandlerException {
        String title = "推送记录一般数据:";
        String message = JSON.toJSONString(FastJsonNormalData.of(normalData), true);
        logData(title, message);
    }

    @Override
    public void normalRecorded(List<NormalData> normalDatas) throws HandlerException {
        for (NormalData normalRecord : normalDatas) {
            normalRecorded(normalRecord);
        }
    }

    @Override
    public void filteredUpdated(FilteredData filteredData) throws HandlerException {
        String title = "推送更新被过滤数据:";
        String message = JSON.toJSONString(FastJsonFilteredData.of(filteredData), true);
        logData(title, message);
    }

    @Override
    public void filteredUpdated(List<FilteredData> filteredDatas) throws HandlerException {
        for (FilteredData filteredRecord : filteredDatas) {
            filteredUpdated(filteredRecord);
        }
    }

    @Override
    public void filteredRecorded(FilteredData filteredData) throws HandlerException {
        String title = "推送记录被过滤数据:";
        String message = JSON.toJSONString(FastJsonFilteredData.of(filteredData), true);
        logData(title, message);
    }

    @Override
    public void filteredRecorded(List<FilteredData> filteredDatas) throws HandlerException {
        for (FilteredData filteredRecord : filteredDatas) {
            filteredRecorded(filteredRecord);
        }
    }

    @Override
    public void triggeredUpdated(TriggeredData triggeredData) throws HandlerException {
        String title = "推送更新被触发数据:";
        String message = JSON.toJSONString(FastJsonTriggeredData.of(triggeredData), true);
        logData(title, message);
    }

    @Override
    public void triggeredUpdated(List<TriggeredData> triggeredDatas) throws HandlerException {
        for (TriggeredData triggeredRecord : triggeredDatas) {
            triggeredUpdated(triggeredRecord);
        }
    }

    @Override
    public void triggeredRecorded(TriggeredData triggeredData) throws HandlerException {
        String title = "推送记录被触发数据:";
        String message = JSON.toJSONString(FastJsonTriggeredData.of(triggeredData), true);
        logData(title, message);
    }

    @Override
    public void triggeredRecorded(List<TriggeredData> triggeredDatas) throws HandlerException {
        for (TriggeredData triggeredRecord : triggeredDatas) {
            triggeredRecorded(triggeredRecord);
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
