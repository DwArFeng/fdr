package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.service.FetcherInfoMaintainService;
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
public class FetcherInfoMaintainServiceImplTest {

    @Autowired
    private FetcherInfoMaintainService fetcherInfoMaintainService;

    private List<FetcherInfo> fetcherInfos;

    @Before
    public void setUp() {
        fetcherInfos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FetcherInfo fetcherInfo = new FetcherInfo(null, true, "type", "param", "remark");
            fetcherInfos.add(fetcherInfo);
        }
    }

    @After
    public void tearDown() {
        fetcherInfos.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (FetcherInfo fetcherInfo : fetcherInfos) {
                fetcherInfo.setKey(fetcherInfoMaintainService.insert(fetcherInfo));
                fetcherInfoMaintainService.update(fetcherInfo);
                FetcherInfo testFetcherInfo = fetcherInfoMaintainService.get(fetcherInfo.getKey());
                assertEquals(BeanUtils.describe(fetcherInfo), BeanUtils.describe(testFetcherInfo));
            }
        } finally {
            for (FetcherInfo fetcherInfo : fetcherInfos) {
                if (Objects.isNull(fetcherInfo.getKey())) {
                    continue;
                }
                fetcherInfoMaintainService.deleteIfExists(fetcherInfo.getKey());
            }
        }
    }
}
