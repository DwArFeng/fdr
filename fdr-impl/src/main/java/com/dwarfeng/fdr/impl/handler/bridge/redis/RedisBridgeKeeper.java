package com.dwarfeng.fdr.impl.handler.bridge.redis;

import com.dwarfeng.dct.handler.ValueCodingHandler;
import com.dwarfeng.fdr.impl.handler.bridge.FullKeeper;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.service.BatchCrudService;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Redis 桥接器保持器。
 *
 * @author DwArFeng
 * @since 2.1.0
 */
public abstract class RedisBridgeKeeper<D extends Data, E extends Entity<LongIdKey>> extends FullKeeper<D> {

    protected final BatchCrudService<LongIdKey, E> service;

    protected final ValueCodingHandler valueCodingHandler;

    protected final boolean allowEarlierDataOverride;

    public RedisBridgeKeeper(
            BatchCrudService<LongIdKey, E> service,
            ValueCodingHandler valueCodingHandler,
            boolean allowEarlierDataOverride
    ) {
        this.service = service;
        this.valueCodingHandler = valueCodingHandler;
        this.allowEarlierDataOverride = allowEarlierDataOverride;
    }

    @Override
    protected void doUpdate(D data) throws Exception {
        if (!allowEarlierDataOverride) {
            E oldEntity = service.getIfExists(data.getPointKey());
            if (Objects.nonNull(oldEntity) && getHappenedDate(oldEntity).after(data.getHappenedDate())) {
                logEarlierDataWillNotUpdate(data, oldEntity);
                return;
            }
        }
        E entity = transformData(data);
        service.insertOrUpdate(entity);
    }

    @Override
    protected void doUpdate(List<D> datas) throws Exception {
        List<LongIdKey> entityKeys = datas.stream().map(Data::getPointKey).collect(Collectors.toList());
        Map<LongIdKey, E> oldEntityMap = service.batchGetIfExists(entityKeys).stream()
                .collect(Collectors.toMap(Entity::getKey, Function.identity()));
        List<E> ds = new ArrayList<>();
        for (D data : datas) {
            if (!allowEarlierDataOverride) {
                E oldEntity = oldEntityMap.get(data.getPointKey());
                if (Objects.nonNull(oldEntity) && getHappenedDate(oldEntity).after(data.getHappenedDate())) {
                    logEarlierDataWillNotUpdate(data, oldEntity);
                    continue;
                }
            }
            E entity = transformData(data);
            ds.add(entity);
        }
        service.batchInsertOrUpdate(ds);
    }

    private void logEarlierDataWillNotUpdate(D data, E oldEntity) {
        Logger logger = getLogger();
        String message = "数据点 " + data.getPointKey() + " 的发生时间 " + data.getHappenedDate() +
                " 早于保持器中的数据的发生时间 " + getHappenedDate(oldEntity) + ", 不更新";
        logger.debug(message);
    }

    @Override
    protected D doLatest(LongIdKey pointKey) throws Exception {
        E entity = service.getIfExists(pointKey);
        return reverseTransform(entity);
    }

    @Override
    protected List<D> doLatest(List<LongIdKey> pointKeys) throws Exception {
        List<E> entities = service.batchGetIfExists(pointKeys);
        List<D> datas = new ArrayList<>(entities.size());
        for (E entity : entities) {
            datas.add(reverseTransform(entity));
        }
        return datas;
    }

    protected abstract Logger getLogger();

    protected abstract Date getHappenedDate(@Nonnull E entity);

    protected abstract E transformData(@Nullable D data) throws Exception;

    protected abstract D reverseTransform(@Nullable E entity) throws Exception;

    @Override
    public String toString() {
        return "RedisBridgeKeeper{" +
                "service=" + service +
                ", valueCodingHandler=" + valueCodingHandler +
                ", allowEarlierDataOverride=" + allowEarlierDataOverride +
                '}';
    }
}
