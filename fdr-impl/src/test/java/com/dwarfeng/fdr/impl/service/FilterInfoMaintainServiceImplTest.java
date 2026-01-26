package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import org.apache.commons.beanutils.BeanUtils;
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
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class FilterInfoMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private FilterInfoMaintainService filterInfoMaintainService;

    private Point parentPoint;
    private List<FilterInfo> filterInfos;

    @Before
    public void setUp() {
        parentPoint = new Point(
                null, "name", "remark", true, true, true, true, true, true,
                "reservedStringAlpha", "reservedStringBravo", "reservedStringCharlie", "reservedStringDelta",
                12450L, 12450L, 12450, 12450, true, true, new Date(), new Date()
        );
        filterInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FilterInfo filterInfo = new FilterInfo(null, null, i, true, "type", "param", "remark");
            filterInfos.add(filterInfo);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        filterInfos.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (FilterInfo filterInfo : filterInfos) {
                filterInfo.setKey(filterInfoMaintainService.insert(filterInfo));
                filterInfo.setPointKey(parentPoint.getKey());
                filterInfoMaintainService.update(filterInfo);
                FilterInfo testFilterInfo = filterInfoMaintainService.get(filterInfo.getKey());
                assertEquals(BeanUtils.describe(filterInfo), BeanUtils.describe(testFilterInfo));
            }
        } finally {
            for (FilterInfo filterInfo : filterInfos) {
                if (Objects.isNull(filterInfo.getKey())) {
                    continue;
                }
                filterInfoMaintainService.deleteIfExists(filterInfo.getKey());
            }
            if (Objects.nonNull(parentPoint.getKey())) {
                pointMaintainService.deleteIfExists(parentPoint.getKey());
            }
        }
    }
}
