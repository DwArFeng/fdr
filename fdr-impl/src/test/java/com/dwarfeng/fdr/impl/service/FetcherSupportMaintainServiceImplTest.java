package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherSupport;
import com.dwarfeng.fdr.stack.service.FetcherSupportMaintainService;
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
import java.util.Objects;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class FetcherSupportMaintainServiceImplTest {

    @Autowired
    private FetcherSupportMaintainService service;

    private final List<FetcherSupport> fetcherSupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            FetcherSupport fetcherSupport = new FetcherSupport(
                    new StringIdKey("fetcher-support-" + (i + 1)),
                    "label-" + (i + 1),
                    "这是测试用的 FetcherSupport",
                    "1233211234567"
            );
            fetcherSupports.add(fetcherSupport);
        }
    }

    @After
    public void tearDown() {
        fetcherSupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (FetcherSupport fetcherSupport : fetcherSupports) {
                fetcherSupport.setKey(service.insert(fetcherSupport));
                service.update(fetcherSupport);
                FetcherSupport testFetcherSupport = service.get(fetcherSupport.getKey());
                assertEquals(BeanUtils.describe(fetcherSupport), BeanUtils.describe(testFetcherSupport));
            }
        } finally {
            for (FetcherSupport fetcherSupport : fetcherSupports) {
                if (Objects.isNull(fetcherSupport.getKey())) {
                    continue;
                }
                service.deleteIfExists(fetcherSupport.getKey());
            }
        }
    }
}
