package com.dwarfeng.fdr.api.maintain.dubbo.api.impl;

import com.dwarfeng.dutil.basic.str.UUIDUtil;
import com.dwarfeng.fdr.api.maintain.dubbo.api.FilteredValueMaintainApi;
import com.dwarfeng.fdr.api.maintain.dubbo.api.PointMaintainApi;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.key.UuidKey;
import com.dwarfeng.fdr.stack.exception.ServiceException;
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
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class FilteredValueMaintainApiImplTest {

    @Autowired
    private PointMaintainApi pointMaintainApi;
    @Autowired
    private FilteredValueMaintainApi filteredValueMaintainApi;

    private Point parentPoint;
    private List<FilteredValue> filteredValues;

    @Before
    public void setUp() throws Exception {
        parentPoint = new Point(
                new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                null,
                "parent-point",
                "test-point",
                true,
                true
        );
        filteredValues = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FilteredValue filteredValue = new FilteredValue(
                    new UuidKey(UUIDUtil.toDenseString(UUID.randomUUID())),
                    parentPoint.getKey(),
                    new Date(),
                    "filtered-value-" + i,
                    "this is a test"
            );
            filteredValues.add(filteredValue);
        }
    }

    @After
    public void tearDown() throws Exception {
        parentPoint = null;
        filteredValues.clear();
    }

    @Test
    public void test() throws ServiceException {
        try {
            pointMaintainApi.insert(parentPoint);
            for (FilteredValue filteredValue : filteredValues) {
                filteredValueMaintainApi.insert(filteredValue);
            }
        } finally {
            pointMaintainApi.delete(parentPoint.getKey());
            for (FilteredValue filteredValue : filteredValues) {
                filteredValueMaintainApi.delete(filteredValue.getKey());
            }
        }
    }

}