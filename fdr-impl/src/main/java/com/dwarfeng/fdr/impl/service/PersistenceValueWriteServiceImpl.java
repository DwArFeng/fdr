package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.service.PersistenceValueWriteService;
import com.dwarfeng.subgrade.impl.service.DaoOnlyBatchWriteService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PersistenceValueWriteServiceImpl implements PersistenceValueWriteService {

    @Autowired
    private DaoOnlyBatchWriteService<LongIdKey, PersistenceValue> batchWriteService;

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void write(PersistenceValue element) throws ServiceException {
        batchWriteService.write(element);
    }

    @Override
    @BehaviorAnalyse
    @Transactional(transactionManager = "hibernateTransactionManager", rollbackFor = Exception.class)
    public void batchWrite(List<PersistenceValue> elements) throws ServiceException {
        batchWriteService.batchWrite(elements);
    }
}