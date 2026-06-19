package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.cache.EnabledFetcherInfoCache;
import com.dwarfeng.fdr.stack.service.EnabledFetcherInfoLookupService;
import com.dwarfeng.fdr.stack.service.FetcherInfoMaintainService;
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
public class EnabledFetcherInfoLookupServiceImplTest {

    @Autowired
    private FetcherInfoMaintainService fetcherInfoMaintainService;
    @Autowired
    private EnabledFetcherInfoLookupService enabledFetcherInfoLookupService;
    @Autowired
    private EnabledFetcherInfoCache enabledFetcherInfoCache;

    private List<FetcherInfo> fetcherInfos;

    @Before
    public void setUp() {
        fetcherInfos = new ArrayList<>();
        int i = 0;
        for (; i < 5; i++) {
            FetcherInfo fetcherInfo = new FetcherInfo(null, true, "type", "param", "fetcher_info.enabled");
            fetcherInfos.add(fetcherInfo);
        }
        for (; i < 10; i++) {
            FetcherInfo fetcherInfo = new FetcherInfo(null, false, "type", "param", "fetcher_info.disabled");
            fetcherInfos.add(fetcherInfo);
        }
    }

    @After
    public void tearDown() {
        for (FetcherInfo fetcherInfo : fetcherInfos) {
            try {
                fetcherInfoMaintainService.deleteIfExists(fetcherInfo.getKey());
            } catch (ServiceException e) {
                // Ignore exception during cleanup.
            }
        }
        fetcherInfos.clear();
    }

    @Test
    public void test() throws ServiceException, CacheException {
        try {
            // 记录数据库中已有启用 FetcherInfo 的数量，排除干扰。
            int existedEnabledFetcherInfoCount = fetcherInfoMaintainService.lookupAsList(
                    FetcherInfoMaintainService.ENABLED, new Object[0]
            ).size();

            for (FetcherInfo fetcherInfo : fetcherInfos) {
                fetcherInfo.setKey(fetcherInfoMaintainService.insert(fetcherInfo));
            }

            // 验证 lookup 结果。
            assertEquals(
                    existedEnabledFetcherInfoCount + 5,
                    fetcherInfoMaintainService.lookupAsList(
                            FetcherInfoMaintainService.ENABLED, new Object[0]
                    ).size()
            );
            assertEquals(
                    existedEnabledFetcherInfoCount + 5,
                    enabledFetcherInfoLookupService.getEnabledFetcherInfos().size()
            );
            assertEquals(
                    existedEnabledFetcherInfoCount + 5,
                    enabledFetcherInfoCache.get().size()
            );

            // 删除一个启用 FetcherInfo，验证缓存被清空。
            FetcherInfo fetcherInfo = fetcherInfos.get(0);
            fetcherInfoMaintainService.deleteIfExists(fetcherInfo.getKey());
            assertEquals(0, enabledFetcherInfoCache.size());

            // 重新插入，验证缓存依然为空（未预加载）。
            fetcherInfo.setKey(null);
            fetcherInfo.setKey(fetcherInfoMaintainService.insert(fetcherInfo));
            assertEquals(0, enabledFetcherInfoCache.size());

            // 再次查询触发缓存回填，验证结果正确。
            assertEquals(
                    existedEnabledFetcherInfoCount + 5,
                    fetcherInfoMaintainService.lookupAsList(
                            FetcherInfoMaintainService.ENABLED, new Object[0]
                    ).size()
            );
            assertEquals(
                    existedEnabledFetcherInfoCount + 5,
                    enabledFetcherInfoLookupService.getEnabledFetcherInfos().size()
            );
            assertEquals(
                    existedEnabledFetcherInfoCount + 5,
                    enabledFetcherInfoCache.get().size()
            );
        } finally {
            for (FetcherInfo fetcherInfo : fetcherInfos) {
                try {
                    fetcherInfoMaintainService.deleteIfExists(fetcherInfo.getKey());
                } catch (ServiceException e) {
                    // Ignore exception during cleanup.
                }
            }
        }
    }
}
