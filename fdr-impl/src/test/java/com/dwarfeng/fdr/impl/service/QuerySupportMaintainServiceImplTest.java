package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.QuerySupport;
import com.dwarfeng.fdr.stack.bean.key.QuerySupportKey;
import com.dwarfeng.fdr.stack.service.QuerySupportMaintainService;
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
public class QuerySupportMaintainServiceImplTest {

    @Autowired
    private QuerySupportMaintainService service;

    private final List<QuerySupport> querySupports = new ArrayList<>();

    @Before
    public void setUp() {
        for (int i = 0; i < 5; i++) {
            QuerySupport querySupport = new QuerySupport(
                    new QuerySupportKey("category-" + i, "preset-" + i),
                    new String[]{"param-1", "param-2", "param-3"},
                    "description"
            );
            querySupports.add(querySupport);
        }
    }

    @After
    public void tearDown() {
        querySupports.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (QuerySupport querySupport : querySupports) {
                querySupport.setKey(service.insert(querySupport));
                service.update(querySupport);
                QuerySupport testQuerySupport = service.get(querySupport.getKey());
                assertEquals(BeanUtils.describe(querySupport), BeanUtils.describe(testQuerySupport));
            }
        } finally {
            for (QuerySupport querySupport : querySupports) {
                service.deleteIfExists(querySupport.getKey());
            }
        }
    }
}
