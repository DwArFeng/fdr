package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
import com.dwarfeng.fdr.stack.service.TriggeredValueMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class TriggeredValueMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private TriggerInfoMaintainService triggerInfoMaintainService;
    @Autowired
    private TriggeredValueMaintainService triggeredValueMaintainService;

    private Point parentPoint;
    private TriggerInfo parentTriggerInfo;
    private List<TriggeredValue> triggeredValues;

    @Before
    public void setUp() {
        parentPoint = new Point(
                null, "parent-point", "test-point", true, true
        );
        parentTriggerInfo = new TriggerInfo(
                null, parentPoint.getKey(), true, "parent-trigger-info", "this is a test", "test"
        );
        triggeredValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TriggeredValue triggeredValue = new TriggeredValue(
                    null,
                    parentPoint.getKey(),
                    parentTriggerInfo.getKey(),
                    i == 0 ? new Date(10000) : new Date(20000),
                    "triggered-value-" + i,
                    "this is a test"
            );
            triggeredValues.add(triggeredValue);
        }
        triggeredValues.add(new TriggeredValue(
                null,
                parentPoint.getKey(),
                parentTriggerInfo.getKey(),
                new Date(30000),
                "triggered-value-" + triggeredValues.size(),
                "this is a test"
        ));
    }

    @After
    public void tearDown() {
        parentPoint = null;
        parentTriggerInfo = null;
        triggeredValues.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            parentTriggerInfo.setKey(triggerInfoMaintainService.insert(parentTriggerInfo));
            parentTriggerInfo.setPointKey(parentPoint.getKey());
            triggerInfoMaintainService.update(parentTriggerInfo);
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValue.setKey(triggeredValueMaintainService.insert(triggeredValue));
                triggeredValue.setPointKey(parentPoint.getKey());
                triggeredValue.setTriggerKey(parentTriggerInfo.getKey());
                triggeredValueMaintainService.update(triggeredValue);
            }
        } finally {
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValueMaintainService.deleteIfExists(triggeredValue.getKey());
            }
            triggerInfoMaintainService.deleteIfExists(parentTriggerInfo.getKey());
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }

    @Test
    public void testPresetLookup() throws ServiceException {
        try {
            parentTriggerInfo.setKey(triggerInfoMaintainService.insert(parentTriggerInfo));
            parentTriggerInfo.setPointKey(parentPoint.getKey());
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValue.setPointKey(parentPoint.getKey());
                triggeredValue.setTriggerKey(parentTriggerInfo.getKey());
                triggeredValue.setKey(triggeredValueMaintainService.insert(triggeredValue));
            }

            PagedData<TriggeredValue> lookup = triggeredValueMaintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_POINT, new Object[]{parentPoint.getKey()}
            );
            assertEquals(triggeredValues.size(), lookup.getCount());
            for (TriggeredValue triggeredValue : lookup.getData()) {
                assertEquals(parentPoint.getKey(), triggeredValue.getPointKey());
            }

            lookup = triggeredValueMaintainService.lookup(
                    TriggeredValueMaintainService.CHILD_FOR_TRIGGER, new Object[]{parentTriggerInfo.getKey()}
            );
            assertEquals(triggeredValues.size(), lookup.getCount());
            for (TriggeredValue triggeredValue : lookup.getData()) {
                assertEquals(parentTriggerInfo.getKey(), triggeredValue.getTriggerKey());
            }
        } finally {
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValueMaintainService.deleteIfExists(triggeredValue.getKey());
            }
            triggerInfoMaintainService.deleteIfExists(parentTriggerInfo.getKey());
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }

    @Test
    @Deprecated
    public void testPrevious() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValue.setPointKey(parentPoint.getKey());
                triggeredValue.setKey(triggeredValueMaintainService.insert(triggeredValue));
            }
            TriggeredValue previous = triggeredValueMaintainService.previous(parentPoint.getKey(), new Date(12450));
            assertNotNull(previous);
            assertEquals(triggeredValues.get(0).getKey(), previous.getKey());
            previous = triggeredValueMaintainService.previous(parentPoint.getKey(), new Date(10000));
            assertNull(previous);
            previous = triggeredValueMaintainService.previous(parentPoint.getKey(), new Date(9999));
            assertNull(previous);
        } finally {
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValueMaintainService.deleteIfExists(triggeredValue.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }

    @Test
    @Deprecated
    public void testRear() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValue.setPointKey(parentPoint.getKey());
                triggeredValue.setKey(triggeredValueMaintainService.insert(triggeredValue));
            }
            TriggeredValue rear = triggeredValueMaintainService.rear(parentPoint.getKey(), new Date(22450));
            assertNotNull(rear);
            assertEquals(triggeredValues.get(triggeredValues.size() - 1).getKey(), rear.getKey());
            rear = triggeredValueMaintainService.rear(parentPoint.getKey(), new Date(30000));
            assertNull(rear);
            rear = triggeredValueMaintainService.rear(parentPoint.getKey(), new Date(30001));
            assertNull(rear);
        } finally {
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValueMaintainService.deleteIfExists(triggeredValue.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }
}
