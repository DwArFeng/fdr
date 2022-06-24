package com.dwarfeng.fdr.impl.service.operation;

import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.dao.RealtimeValueDao;
import com.dwarfeng.subgrade.sdk.exception.ServiceExceptionCodes;
import com.dwarfeng.subgrade.sdk.service.custom.operation.BatchCrudOperation;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RealtimeValueCrudOperation implements BatchCrudOperation<LongIdKey, RealtimeValue> {

    @Autowired
    private RealtimeValueDao realtimeValueDao;

    @Override
    public boolean exists(LongIdKey key) throws Exception {
        return realtimeValueDao.exists(key);
    }

    @Override
    public RealtimeValue get(LongIdKey key) throws Exception {
        if (!realtimeValueDao.exists(key)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return realtimeValueDao.get(key);
    }

    @Override
    public LongIdKey insert(RealtimeValue realtimeValue) throws Exception {
        return realtimeValueDao.insert(realtimeValue);
    }

    @Override
    public void update(RealtimeValue realtimeValue) throws Exception {
        realtimeValueDao.update(realtimeValue);
    }

    @Override
    public void delete(LongIdKey key) throws Exception {
        realtimeValueDao.delete(key);
    }

    @Override
    public boolean allExists(List<LongIdKey> keys) throws Exception {
        return realtimeValueDao.allExists(keys);
    }

    @Override
    public boolean nonExists(List<LongIdKey> keys) throws Exception {
        return realtimeValueDao.nonExists(keys);
    }

    @Override
    public List<RealtimeValue> batchGet(List<LongIdKey> keys) throws Exception {
        if (!realtimeValueDao.allExists(keys)) {
            throw new ServiceException(ServiceExceptionCodes.ENTITY_NOT_EXIST);
        }
        return realtimeValueDao.batchGet(keys);
    }

    @Override
    public List<LongIdKey> batchInsert(List<RealtimeValue> realtimeValueDetailCategories) throws Exception {
        return realtimeValueDao.batchInsert(realtimeValueDetailCategories);
    }

    @Override
    public void batchUpdate(List<RealtimeValue> realtimeValueDetailCategories) throws Exception {
        realtimeValueDao.batchUpdate(realtimeValueDetailCategories);
    }

    @Override
    public void batchDelete(List<LongIdKey> keys) throws Exception {
        for (LongIdKey key : keys) {
            delete(key);
        }
    }
}
