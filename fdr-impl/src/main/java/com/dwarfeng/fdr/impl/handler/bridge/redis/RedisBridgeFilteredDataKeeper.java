package com.dwarfeng.fdr.impl.handler.bridge.redis;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.redis.bean.RedisBridgeFilteredData;
import com.dwarfeng.fdr.impl.handler.bridge.redis.service.RedisBridgeFilteredDataMaintainService;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
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
 * Redis 桥接被过滤数据保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RedisBridgeFilteredDataKeeper extends RedisBridgeKeeper<FilteredData, RedisBridgeFilteredData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisBridgeFilteredDataKeeper.class);

    public RedisBridgeFilteredDataKeeper(
            RedisBridgeFilteredDataMaintainService service,
            @Qualifier("redisBridge.valueCodingHandler") ValueCodingHandler valueCodingHandler,
            @Value("${bridge.redis.earlier_override.filtered_data}") boolean allowEarlierDataOverride
    ) {
        super(service, valueCodingHandler, allowEarlierDataOverride);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    @Override
    protected Date getHappenedDate(@Nonnull RedisBridgeFilteredData entity) {
        return entity.getHappenedDate();
    }

    @Override
    protected RedisBridgeFilteredData transformData(@Nullable FilteredData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        String flatValue = valueCodingHandler.encode(data.getValue());
        return new RedisBridgeFilteredData(
                data.getPointKey(),
                data.getFilterKey(),
                flatValue,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @Override
    protected FilteredData reverseTransform(@Nullable RedisBridgeFilteredData data) throws Exception {
        if (Objects.isNull(data)) {
            return null;
        }
        Object value = valueCodingHandler.decode(data.getValue());
        return new FilteredData(
                data.getKey(),
                data.getFilterKey(),
                value,
                data.getMessage(),
                data.getHappenedDate()
        );
    }

    @Override
    public String toString() {
        return "RedisBridgeFilteredDataKeeper{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                ", allowEarlierDataOverride=" + allowEarlierDataOverride +
                '}';
    }
}
