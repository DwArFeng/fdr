package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.fdr.stack.service.TriggerSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
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
public class TriggerSupportMaintainServiceImplTest {

    @Autowired
    private TriggerSupportMaintainService service;

    private final List<TriggerSupport> triggerSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            TriggerSupport triggerSupport = new TriggerSupport(
                    new StringIdKey("trigger-support-" + (i + 1)),
                    "label-" + (i + 1),
                    "这是测试用的TriggerSupport",
                    "1233211234567"
            );
            triggerSupports.add(triggerSupport);
        }
    }

    @After
    public void tearDown() {
        triggerSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (TriggerSupport triggerSupport : triggerSupports) {
                triggerSupport.setKey(service.insert(triggerSupport));
                service.update(triggerSupport);
                TriggerSupport testTriggerSupport = service.get(triggerSupport.getKey());
                assertEquals(BeanUtils.describe(triggerSupport), BeanUtils.describe(testTriggerSupport));
            }
        } finally {
            for (TriggerSupport triggerSupport : triggerSupports) {
                service.deleteIfExists(triggerSupport.getKey());
            }
        }
    }
}
