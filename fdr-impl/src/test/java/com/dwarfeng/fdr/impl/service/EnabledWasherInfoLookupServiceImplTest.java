package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.fdr.stack.cache.EnabledWasherInfoCache;
import com.dwarfeng.fdr.stack.service.EnabledWasherInfoLookupService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.fdr.stack.service.WasherInfoMaintainService;
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
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class EnabledWasherInfoLookupServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private WasherInfoMaintainService washerInfoMaintainService;
    @Autowired
    private EnabledWasherInfoLookupService enabledWasherInfoLookupService;
    @Autowired
    private EnabledWasherInfoCache enabledWasherInfoCache;

    private Point parentPoint;
    private List<WasherInfo> washerInfos;

    @Before
    public void setUp() {
        parentPoint = new Point(
                null, "name", "remark", true, true, true, true, true, true,
                "reservedStringAlpha", "reservedStringBravo", "reservedStringCharlie", "reservedStringDelta",
                12450L, 12450L, 12450, 12450, true, true, new Date(), new Date()
        );
        washerInfos = new ArrayList<>();
        int i = 0;
        for (; i < 5; i++) {
            WasherInfo washerInfo = new WasherInfo(null, null, i, true, true, "type", "param", "washer_info.enabled");
            washerInfos.add(washerInfo);
        }
        for (; i < 10; i++) {
            WasherInfo washerInfo = new WasherInfo(null, null, i, false, true, "type", "param", "washer_info.disabled");
            washerInfos.add(washerInfo);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        washerInfos.clear();
    }

    @Test
    public void test() throws ServiceException, CacheException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (WasherInfo washerInfo : washerInfos) {
                washerInfo.setKey(washerInfoMaintainService.insert(washerInfo));
                washerInfo.setPointKey(parentPoint.getKey());
                washerInfoMaintainService.update(washerInfo);
            }
            assertEquals(5, washerInfoMaintainService.lookup(
                    WasherInfoMaintainService.ENABLED_CHILD_FOR_POINT_INDEX_ASC, new Object[]{parentPoint.getKey()}
            ).getCount());
            assertEquals(5, enabledWasherInfoLookupService.getEnabledWasherInfos(parentPoint.getKey()).size());
            assertEquals(5, enabledWasherInfoCache.get(parentPoint.getKey()).size());
            WasherInfo washerInfo = washerInfos.get(0);
            washerInfoMaintainService.deleteIfExists(washerInfo.getKey());
            assertEquals(0, enabledWasherInfoCache.get(parentPoint.getKey()).size());
            washerInfoMaintainService.insert(washerInfo);
            assertEquals(0, enabledWasherInfoCache.get(parentPoint.getKey()).size());
            assertEquals(5, washerInfoMaintainService.lookup(
                    WasherInfoMaintainService.ENABLED_CHILD_FOR_POINT_INDEX_ASC, new Object[]{parentPoint.getKey()}
            ).getCount());
            assertEquals(5, enabledWasherInfoLookupService.getEnabledWasherInfos(parentPoint.getKey()).size());
            assertEquals(5, enabledWasherInfoCache.get(parentPoint.getKey()).size());
        } finally {
            for (WasherInfo washerInfo : washerInfos) {
                washerInfoMaintainService.deleteIfExists(washerInfo.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }
}
