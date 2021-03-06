package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
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
public class PersistenceValueMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private PersistenceValueMaintainService persistenceValueMaintainService;

    private Point parentPoint;
    private List<PersistenceValue> persistenceValues;

    @Before
    public void setUp() {
        parentPoint = new Point(
                null,
                "parent-point",
                "test-point",
                true,
                true
        );
        persistenceValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PersistenceValue persistenceValue = new PersistenceValue(
                    null,
                    parentPoint.getKey(),
                    i == 0 ? new Date(10000) : new Date(),
                    "persistence-value-" + i
            );
            persistenceValues.add(persistenceValue);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        persistenceValues.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValue.setKey(persistenceValueMaintainService.insert(persistenceValue));
                persistenceValue.setPointKey(parentPoint.getKey());
                persistenceValueMaintainService.update(persistenceValue);
            }
        } finally {
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValueMaintainService.delete(persistenceValue.getKey());
            }
            pointMaintainService.delete(parentPoint.getKey());
        }
    }

    @Test
    public void testPrevious() throws ServiceException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValue.setPointKey(parentPoint.getKey());
                persistenceValue.setKey(persistenceValueMaintainService.insert(persistenceValue));
            }
            PersistenceValue previous = persistenceValueMaintainService.previous(parentPoint.getKey(), new Date(12450));
            assertNotNull(previous);
            assertEquals(persistenceValues.get(0).getKey(), previous.getKey());
            previous = persistenceValueMaintainService.previous(parentPoint.getKey(), new Date(10000));
            assertNull(previous);
            previous = persistenceValueMaintainService.previous(parentPoint.getKey(), new Date(9999));
            assertNull(previous);
        } finally {
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValueMaintainService.delete(persistenceValue.getKey());
            }
            pointMaintainService.delete(parentPoint.getKey());
        }
    }
}
