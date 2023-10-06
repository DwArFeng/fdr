package com.dwarfeng.fdr.impl.handler.bridge.redis;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeTriggeredData;
import com.dwarfeng.fdr.impl.handler.bridge.redis.service.RedisBridgeTriggeredDataMaintainService;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Date;
import java.util.Objects;

/**
 * Redis 桥接被触发数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RedisBridgeTriggeredDataKeeper extends RedisBridgeKeeper<TriggeredData, RedisBridgeTriggeredData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisBridgeTriggeredDataKeeper.class);

    public RedisBridgeTriggeredDataKeeper(
            RedisBridgeTriggeredDataMaintainService service,
            @Qualifier("redisBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler,
            @Value("${bridge.redis.earlier_override.triggered_data}") boolean allowEarlierDataOverride
    ) {
        super(service, valueCodingHandler, allowEarlierDataOverride);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected Date getHappenedDate(@Nonnull RedisBridgeTriggeredData entity) {
        return entity.getHappenedDate();
    }

    @Override
    protected RedisBridgeTriggeredData transformData(@Nullable TriggeredData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new RedisBridgeTriggeredData(
                data.getPointKey(),
                data.getTriggerKey(),
                flatValue,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @Override
    protected TriggeredData reverseTransform(@Nullable RedisBridgeTriggeredData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        Object value = valueCodingHandler.decode(data.getValue());
        return new TriggeredData(
                data.getKey(),
                data.getTriggerKey(),
                value,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @Override
    public String toString() {
        return "RedisBridgeTriggeredDataKeeper{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                ", allowEarlierDataOverride=" + allowEarlierDataOverride +
                '}';
    }
}
