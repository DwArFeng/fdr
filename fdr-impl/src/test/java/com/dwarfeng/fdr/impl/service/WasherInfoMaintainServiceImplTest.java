package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.fdr.stack.service.WasherInfoMaintainService;
import org.apache.commons.beanutils.BeanUtils;
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
public class WasherInfoMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private WasherInfoMaintainService washerInfoMaintainService;

    private Point parentPoint;
    private List<WasherInfo> washerInfos;

    @Before
    public void setUp() {
        parentPoint = new Point(null, "name", "remark", true, true, true, true, true, true);
        washerInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            WasherInfo washerInfo = new WasherInfo(null, null, i, true, true, "type", "param", "remark");
            washerInfos.add(washerInfo);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        washerInfos.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (WasherInfo washerInfo : washerInfos) {
                washerInfo.setKey(washerInfoMaintainService.insert(washerInfo));
                washerInfo.setPointKey(parentPoint.getKey());
                washerInfoMaintainService.update(washerInfo);
                WasherInfo testWasherInfo = washerInfoMaintainService.get(washerInfo.getKey());
                assertEquals(BeanUtils.describe(washerInfo), BeanUtils.describe(testWasherInfo));
            }
        } finally {
            for (WasherInfo washerInfo : washerInfos) {
                washerInfoMaintainService.deleteIfExists(washerInfo.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }
}
