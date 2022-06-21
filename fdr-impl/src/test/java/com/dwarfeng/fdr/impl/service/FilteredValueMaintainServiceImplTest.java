package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
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
public class FilteredValueMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private FilterInfoMaintainService filterInfoMaintainService;
    @Autowired
    private FilteredValueMaintainService filteredValueMaintainService;

    private Point parentPoint;
    private FilterInfo parentFilterInfo;
    private List<FilteredValue> filteredValues;

    @Before
    public void setUp() {
        parentPoint = new Point(
                null,
                "parent-point",
                "test-point",
                true,
                true
        );
        parentFilterInfo = new FilterInfo(
                null,
                parentPoint.getKey(),
                true,
                "parent-filter-info",
                "this is a test",
                "test"
        );
        filteredValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FilteredValue filteredValue = new FilteredValue(
                    null,
                    parentPoint.getKey(),
                    parentFilterInfo.getKey(),
                    i == 0 ? new Date(10000) : new Date(20000),
                    "filtered-value-" + i,
                    "this is a test"
            );
            filteredValues.add(filteredValue);
        }
        filteredValues.add(new FilteredValue(
                null,
                parentPoint.getKey(),
                parentFilterInfo.getKey(),
                new Date(30000),
                "filtered-value-" + filteredValues.size(),
                "this is a test"
        ));
    }

    @After
    public void tearDown() {
        parentPoint = null;
        parentFilterInfo = null;
        filteredValues.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            parentFilterInfo.setKey(filterInfoMaintainService.insert(parentFilterInfo));
            parentFilterInfo.setPointKey(parentPoint.getKey());
            filterInfoMaintainService.update(parentFilterInfo);
            for (FilteredValue filteredValue : filteredValues) {
                filteredValue.setKey(filteredValueMaintainService.insert(filteredValue));
                filteredValue.setPointKey(parentPoint.getKey());
                filteredValue.setFilterKey(parentFilterInfo.getKey());
                filteredValueMaintainService.update(filteredValue);
            }
        } finally {
            for (FilteredValue filteredValue : filteredValues) {
                filteredValueMaintainService.deleteIfExists(filteredValue.getKey());
            }
            filterInfoMaintainService.deleteIfExists(parentFilterInfo.getKey());
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }

    @Test
    public void testPrevious() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (FilteredValue filteredValue : filteredValues) {
                filteredValue.setPointKey(parentPoint.getKey());
                filteredValue.setKey(filteredValueMaintainService.insert(filteredValue));
            }
            FilteredValue previous = filteredValueMaintainService.previous(parentPoint.getKey(), new Date(12450));
            assertNotNull(previous);
            assertEquals(filteredValues.get(0).getKey(), previous.getKey());
            previous = filteredValueMaintainService.previous(parentPoint.getKey(), new Date(10000));
            assertNull(previous);
            previous = filteredValueMaintainService.previous(parentPoint.getKey(), new Date(9999));
            assertNull(previous);
        } finally {
            for (FilteredValue filteredValue : filteredValues) {
                filteredValueMaintainService.deleteIfExists(filteredValue.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }

    @Test
    public void testRear() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (FilteredValue filteredValue : filteredValues) {
                filteredValue.setPointKey(parentPoint.getKey());
                filteredValue.setKey(filteredValueMaintainService.insert(filteredValue));
            }
            FilteredValue rear = filteredValueMaintainService.rear(parentPoint.getKey(), new Date(22450));
            assertNotNull(rear);
            assertEquals(filteredValues.get(filteredValues.size() - 1).getKey(), rear.getKey());
            rear = filteredValueMaintainService.rear(parentPoint.getKey(), new Date(30000));
            assertNull(rear);
            rear = filteredValueMaintainService.rear(parentPoint.getKey(), new Date(30001));
            assertNull(rear);
        } finally {
            for (FilteredValue filteredValue : filteredValues) {
                filteredValueMaintainService.deleteIfExists(filteredValue.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }
}
