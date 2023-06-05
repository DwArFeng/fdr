package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.LookupSupport;
import com.dwarfeng.fdr.stack.bean.key.LookupSupportKey;
import com.dwarfeng.fdr.stack.service.LookupSupportMaintainService;
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
public class LookupSupportMaintainServiceImplTest {

    @Autowired
    private LookupSupportMaintainService service;

    private final List<LookupSupport> lookupSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            LookupSupport lookupSupport = new LookupSupport(
                    new LookupSupportKey("category-" + i, "preset-" + i),
                    new String[]{"param-1", "param-2", "param-3"},
                    "description"
            );
            lookupSupports.add(lookupSupport);
        }
    }

    @After
    public void tearDown() {
        lookupSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (LookupSupport lookupSupport : lookupSupports) {
                lookupSupport.setKey(service.insert(lookupSupport));
                service.update(lookupSupport);
                LookupSupport testLookupSupport = service.get(lookupSupport.getKey());
                assertEquals(BeanUtils.describe(lookupSupport), BeanUtils.describe(testLookupSupport));
            }
        } finally {
            for (LookupSupport lookupSupport : lookupSupports) {
                service.deleteIfExists(lookupSupport.getKey());
            }
        }
    }
}
