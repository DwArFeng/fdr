package com.dwarfeng.fdr.impl.handler.bridge.redis;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeNormalData;
import com.dwarfeng.fdr.impl.handler.bridge.redis.service.RedisBridgeNormalDataMaintainService;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
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
 * Redis 桥接一般数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RedisBridgeNormalDataKeeper extends RedisBridgeKeeper<NormalData, RedisBridgeNormalData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisBridgeNormalDataKeeper.class);

    public RedisBridgeNormalDataKeeper(
            RedisBridgeNormalDataMaintainService service,
            @Qualifier("redisBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler,
            @Value("${bridge.redis.earlier_override.normal_data}") boolean allowEarlierDataOverride
    ) {
        super(service, valueCodingHandler, allowEarlierDataOverride);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected Date getHappenedDate(@Nonnull RedisBridgeNormalData entity) {
        return entity.getHappenedDate();
    }

    @Override
    protected RedisBridgeNormalData transformData(@Nullable NormalData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new RedisBridgeNormalData(
                data.getPointKey(),
                flatValue,
                data.getHappenedDate()
        );
    }

    @Override
    protected NormalData reverseTransform(@Nullable RedisBridgeNormalData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        Object value = valueCodingHandler.decode(data.getValue());
        return new NormalData(
                data.getKey(),
                value,
                data.getHappenedDate()
        );
    }

    @Override
    public String toString() {
        return "RedisBridgeNormalDataKeeper{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                ", allowEarlierDataOverride=" + allowEarlierDataOverride +
                '}';
    }
}
