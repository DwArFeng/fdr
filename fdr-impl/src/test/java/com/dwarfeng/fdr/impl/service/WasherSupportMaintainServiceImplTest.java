package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.WasherSupport;
import com.dwarfeng.fdr.stack.service.WasherSupportMaintainService;
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
public class WasherSupportMaintainServiceImplTest {

    @Autowired
    private WasherSupportMaintainService service;

    private final List<WasherSupport> washerSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            WasherSupport washerSupport = new WasherSupport(
                    new StringIdKey("washer-support-" + (i + 1)),
                    "label-" + (i + 1),
                    "这是测试用的WasherSupport",
                    "1233211234567"
            );
            washerSupports.add(washerSupport);
        }
    }

    @After
    public void tearDown() {
        washerSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (WasherSupport washerSupport : washerSupports) {
                washerSupport.setKey(service.insert(washerSupport));
                service.update(washerSupport);
                WasherSupport testWasherSupport = service.get(washerSupport.getKey());
                assertEquals(BeanUtils.describe(washerSupport), BeanUtils.describe(testWasherSupport));
            }
        } finally {
            for (WasherSupport washerSupport : washerSupports) {
                service.deleteIfExists(washerSupport.getKey());
            }
        }
    }
}
