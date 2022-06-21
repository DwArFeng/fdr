package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.cache.EnabledFilterInfoCache;
import com.dwarfeng.fdr.stack.service.EnabledFilterInfoLookupService;
import com.dwarfeng.fdr.stack.service.FilterInfoMaintainService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.stack.exception.CacheException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class EnabledFilterInfoLookupServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private FilterInfoMaintainService filterInfoMaintainService;
    @Autowired
    private EnabledFilterInfoLookupService enabledFilterInfoLookupService;
    @Autowired
    private EnabledFilterInfoCache enabledFilterInfoCache;

    private Point parentPoint;
    private List<FilterInfo> filterInfos;

    @Before
    public void setUp() {
        parentPoint = new Point(
                null,
                "parent-point",
                "test-point",
                true,
                true
        );
        filterInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FilterInfo filterInfo = new FilterInfo(
                    null,
                    parentPoint.getKey(),
                    true,
                    "filter-info-enabled-" + i,
                    "this is a test",
                    "test"
            );
            filterInfos.add(filterInfo);
        }
        for (int i = 0; i < 5; i++) {
            FilterInfo filterInfo = new FilterInfo(
                    null,
                    parentPoint.getKey(),
                    false,
                    "filter-info-disabled-" + i,
                    "this is a test",
                    "test"
            );
            filterInfos.add(filterInfo);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        filterInfos.clear();
    }

    @Test
    public void test() throws ServiceException, CacheException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (FilterInfo filterInfo : filterInfos) {
                filterInfo.setKey(filterInfoMaintainService.insert(filterInfo));
                filterInfo.setPointKey(parentPoint.getKey());
                filterInfoMaintainService.update(filterInfo);
            }
            assertEquals(5, filterInfoMaintainService.lookup(FilterInfoMaintainService.ENABLED_CHILD_FOR_POINT, new Object[]{parentPoint.getKey()}).getCount());
            assertEquals(5, enabledFilterInfoLookupService.getEnabledFilterInfos(parentPoint.getKey()).size());
            assertEquals(5, enabledFilterInfoCache.get(parentPoint.getKey()).size());
            FilterInfo filterInfo = filterInfos.get(0);
            filterInfoMaintainService.deleteIfExists(filterInfo.getKey());
            assertEquals(0, enabledFilterInfoCache.get(parentPoint.getKey()).size());
            filterInfoMaintainService.insert(filterInfo);
            assertEquals(0, enabledFilterInfoCache.get(parentPoint.getKey()).size());
            assertEquals(5, filterInfoMaintainService.lookup(FilterInfoMaintainService.ENABLED_CHILD_FOR_POINT, new Object[]{parentPoint.getKey()}).getCount());
            assertEquals(5, enabledFilterInfoLookupService.getEnabledFilterInfos(parentPoint.getKey()).size());
            assertEquals(5, enabledFilterInfoCache.get(parentPoint.getKey()).size());
        } finally {
            for (FilterInfo filterInfo : filterInfos) {
                filterInfoMaintainService.deleteIfExists(filterInfo.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }
}
