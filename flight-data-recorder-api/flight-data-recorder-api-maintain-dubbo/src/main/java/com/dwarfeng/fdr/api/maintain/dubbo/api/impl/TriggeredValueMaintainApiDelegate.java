package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.fdr.sdk.interceptor.TimeAnalyse;
import com.dwarfeng.fdr.sdk.util.ServiceExceptionCodes;
import com.dwarfeng.fdr.sdk.validation.bean.entity.ValidationTriggeredValue;
import com.dwarfeng.fdr.sdk.validation.bean.key.ValidationGuidKey;
import com.dwarfeng.fdr.sdk.validation.group.Insert;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.bean.key.GuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@SuppressWarnings("unused")
@Component
public class TriggeredValueMaintainApiDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(TriggeredValueMaintainApiDelegate.class);
    @Autowired
    public ValidateBean validateBean;
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    @Qualifier("triggeredValueMaintainService")
    private TriggeredValueMaintainService service;
    @Autowired
    private Mapper mapper;

    @TimeAnalyse
    public TriggeredValue get(GuidKey guidKey) throws ServiceException {
        ValidationGuidKey validationGuidKey = mapper.map(guidKey, ValidationGuidKey.class);
        try {
            validateBean.validateGet(validationGuidKey);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.get(guidKey);
    }

    @TimeAnalyse
    public GuidKey insert(TriggeredValue triggeredValue) throws ServiceException {
        ValidationTriggeredValue validationTriggeredValue = mapper.map(triggeredValue, ValidationTriggeredValue.class);
        try {
            validateBean.validateInsert(validationTriggeredValue);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.insert(triggeredValue);
    }

    @TimeAnalyse
    public GuidKey update(TriggeredValue triggeredValue) throws ServiceException {
        ValidationTriggeredValue validationTriggeredValue = mapper.map(triggeredValue, ValidationTriggeredValue.class);
        try {
            validateBean.validateUpdate(validationTriggeredValue);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        return service.update(triggeredValue);
    }

    @TimeAnalyse
    public void delete(GuidKey guidKey) throws ServiceException {
        ValidationGuidKey validationGuidKey = mapper.map(guidKey, ValidationGuidKey.class);
        try {
            validateBean.validateDelete(validationGuidKey);
        } catch (ConstraintViolationException e) {
            LOGGER.warn("参数非法，将抛出异常...", e);
            throw new ServiceException(ServiceExceptionCodes.PARAM_VALIDATION_FAILED);
        }
        service.delete(guidKey);
    }

    @Component
    @Validated
    public static class ValidateBean {

        @Validated({Default.class})
        public void validateGet(@Valid @NotNull ValidationGuidKey validationGuidKey) throws ConstraintViolationException {
        }

        @Validated({Insert.class})
        public void validateInsert(@Valid @NotNull ValidationTriggeredValue validationTriggeredValue) throws ConstraintViolationException {
        }

        @Validated({Default.class})
        public void validateUpdate(@Valid @NotNull ValidationTriggeredValue validationTriggeredValue) throws ConstraintViolationException {
        }

        @Validated({Default.class})
        public void validateDelete(@Valid @NotNull ValidationGuidKey validationGuidKey) throws ConstraintViolationException {
        }
    }
}
