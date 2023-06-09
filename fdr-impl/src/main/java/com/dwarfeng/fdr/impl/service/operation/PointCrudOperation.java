package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.fdr.stack.cache.*;
import com.dwarfeng.fdr.stack.dao.FilterInfoDao;
import com.dwarfeng.fdr.stack.dao.PointDao;
import com.dwarfeng.fdr.stack.dao.TriggerInfoDao;
import com.dwarfeng.fdr.stack.dao.WasherInfoDao;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import com.dwarfeng.fdr.stack.service.WasherInfoMaintainService;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PointCrudOperation implements BatchCrudOperation<LongIdKey, Point> {

    private final PointDao pointDao;
    private final PointCache pointCache;

    private final FilterInfoDao filterInfoDao;
    private final FilterInfoCache filterInfoCache;

    private final TriggerInfoDao triggerInfoDao;
    private final TriggerInfoCache triggerInfoCache;

    private final WasherInfoDao washerInfoDao;
    private final WasherInfoCache washerInfoCache;

    private final EnabledFilterInfoCache enabledFilterInfoCache;

    private final EnabledTriggerInfoCache enabledTriggerInfoCache;

    private final EnabledWasherInfoCache enabledWasherInfoCache;

    @Value("${cache.timeout.entity.point}")
    private long pointTimeout;

    public PointCrudOperation(
            PointDao pointDao, PointCache pointCache,
            FilterInfoDao filterInfoDao, FilterInfoCache filterInfoCache,
            TriggerInfoDao triggerInfoDao, TriggerInfoCache triggerInfoCache,
            WasherInfoDao washerInfoDao, WasherInfoCache washerInfoCache,
            EnabledFilterInfoCache enabledFilterInfoCache,
            EnabledTriggerInfoCache enabledTriggerInfoCache,
            EnabledWasherInfoCache enabledWasherInfoCache
    ) {
        this.pointDao = pointDao;
        this.pointCache = pointCache;
        this.filterInfoDao = filterInfoDao;
        this.filterInfoCache = filterInfoCache;
        this.triggerInfoDao = triggerInfoDao;
        this.triggerInfoCache = triggerInfoCache;
        this.washerInfoDao = washerInfoDao;
        this.washerInfoCache = washerInfoCache;
        this.enabledFilterInfoCache = enabledFilterInfoCache;
        this.enabledTriggerInfoCache = enabledTriggerInfoCache;
        this.enabledWasherInfoCache = enabledWasherInfoCache;
    }

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return pointCache.exists(key) || pointDao.exists(key);
    }

    @Override
    public Point get(LongIdKey key) throws Exception {
        if (pointCache.exists(key)) {
            return pointCache.get(key);
        } else {
            if (!pointDao.exists(key)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            Point point = pointDao.get(key);
            pointCache.push(point, pointTimeout);
            return point;
        }
    }

    @Override
    public LongIdKey insert(Point point) throws Exception {
        pointCache.push(point, pointTimeout);
        return pointDao.insert(point);
    }

    @Override
    public void update(Point point) throws Exception {
        pointCache.push(point, pointTimeout);
        pointDao.update(point);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        //查找点位拥有的过滤器与触发器。
        List<LongIdKey> filterInfoKeys = filterInfoDao.lookup(
                FilterInfoMaintainService.CHILD_FOR_POINT, new Object[]{key}
        ).stream().map(FilterInfo::getKey).collect(Collectors.toList());
        List<LongIdKey> triggerInfoKeys = triggerInfoDao.lookup(
                TriggerInfoMaintainService.CHILD_FOR_POINT, new Object[]{key}
        ).stream().map(TriggerInfo::getKey).collect(Collectors.toList());
        List<LongIdKey> washerInfoKeys = washerInfoDao.lookup(
                WasherInfoMaintainService.CHILD_FOR_POINT, new Object[]{key}
        ).stream().map(WasherInfo::getKey).collect(Collectors.toList());

        //删除点位拥有的过滤器与触发器。
        filterInfoDao.batchDelete(filterInfoKeys);
        filterInfoCache.batchDelete(filterInfoKeys);
        triggerInfoDao.batchDelete(triggerInfoKeys);
        triggerInfoCache.batchDelete(triggerInfoKeys);
        washerInfoDao.batchDelete(washerInfoKeys);
        washerInfoCache.batchDelete(washerInfoKeys);

        //使能过滤器信息和使能触发器缓存信息删除。
        enabledFilterInfoCache.delete(key);
        enabledTriggerInfoCache.delete(key);
        enabledWasherInfoCache.delete(key);

        // 删除数据点自身。
        pointDao.delete(key);
        pointCache.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return pointCache.allExists(keys) || pointDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return pointCache.nonExists(keys) && pointDao.nonExists(keys);
    }

    @Override
    public List<Point> batchGet(List<LongIdKey> keys) throws Exception {
        if (pointCache.allExists(keys)) {
            return pointCache.batchGet(keys);
        } else {
            if (!pointDao.allExists(keys)) {
                throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
            }
            List<Point> points = pointDao.batchGet(keys);
            pointCache.batchPush(points, pointTimeout);
            return points;
        }
    }

    @Override
    public List<LongIdKey> batchInsert(List<Point> points) throws Exception {
        pointCache.batchPush(points, pointTimeout);
        return pointDao.batchInsert(points);
    }

    @Override
    public void batchUpdate(List<Point> points) throws Exception {
        pointCache.batchPush(points, pointTimeout);
        pointDao.batchUpdate(points);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
