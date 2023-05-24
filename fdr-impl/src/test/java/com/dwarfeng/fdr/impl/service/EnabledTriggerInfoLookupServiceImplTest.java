package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.fdr.stack.cache.EnabledTriggerInfoCache;
import com.dwarfeng.fdr.stack.service.EnabledTriggerInfoLookupService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.fdr.stack.service.TriggerInfoMaintainService;
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
public class EnabledTriggerInfoLookupServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private TriggerInfoMaintainService triggerInfoMaintainService;
    @Autowired
    private EnabledTriggerInfoLookupService enabledTriggerInfoLookupService;
    @Autowired
    private EnabledTriggerInfoCache enabledTriggerInfoCache;

    private Point parentPoint;
    private List<TriggerInfo> triggerInfos;

    @Before
    public void setUp() {
        parentPoint = new Point(null, "name", "remark", true, true, true, true, true, true);
        triggerInfos = new ArrayList<>();
        int i = 0;
        for (; i < 5; i++) {
            TriggerInfo triggerInfo = new TriggerInfo(null, null, i, true, "type", "param", "trigger_info.enabled");
            triggerInfos.add(triggerInfo);
        }
        for (; i < 10; i++) {
            TriggerInfo triggerInfo = new TriggerInfo(null, null, i, false, "type", "param", "trigger_info.disabled");
            triggerInfos.add(triggerInfo);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        triggerInfos.clear();
    }

    @Test
    public void test() throws ServiceException, CacheException {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (TriggerInfo triggerInfo : triggerInfos) {
                triggerInfo.setKey(triggerInfoMaintainService.insert(triggerInfo));
                triggerInfo.setPointKey(parentPoint.getKey());
                triggerInfoMaintainService.update(triggerInfo);
            }
            assertEquals(5, triggerInfoMaintainService.lookup(
                    TriggerInfoMaintainService.ENABLED_CHILD_FOR_POINT_INDEX_ASC, new Object[]{parentPoint.getKey()}
            ).getCount());
            assertEquals(5, enabledTriggerInfoLookupService.getEnabledTriggerInfos(parentPoint.getKey()).size());
            assertEquals(5, enabledTriggerInfoCache.get(parentPoint.getKey()).size());
            TriggerInfo triggerInfo = triggerInfos.get(0);
            triggerInfoMaintainService.deleteIfExists(triggerInfo.getKey());
            assertEquals(0, enabledTriggerInfoCache.get(parentPoint.getKey()).size());
            triggerInfoMaintainService.insert(triggerInfo);
            assertEquals(0, enabledTriggerInfoCache.get(parentPoint.getKey()).size());
            assertEquals(5, triggerInfoMaintainService.lookup(
                    TriggerInfoMaintainService.ENABLED_CHILD_FOR_POINT_INDEX_ASC, new Object[]{parentPoint.getKey()}
            ).getCount());
            assertEquals(5, enabledTriggerInfoLookupService.getEnabledTriggerInfos(parentPoint.getKey()).size());
            assertEquals(5, enabledTriggerInfoCache.get(parentPoint.getKey()).size());
        } finally {
            for (TriggerInfo triggerInfo : triggerInfos) {
                triggerInfoMaintainService.deleteIfExists(triggerInfo.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }
}
