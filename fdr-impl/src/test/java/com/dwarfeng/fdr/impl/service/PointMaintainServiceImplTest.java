package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
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
public class PointMaintainServiceImplTest {

    @Autowired
    private PointMaintainService pointMaintainService;

    private List<Point> points;

    @Before
    public void setUp() {
        points = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Point point = new Point(null, "name", "remark", true, true, true, true, true, true);
            points.add(point);
        }
    }

    @After
    public void tearDown() {
        points.clear();
    }

    @Test
    public void test() throws Exception {
        try {
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                pointMaintainService.update(point);
                Point testPoint = pointMaintainService.get(point.getKey());
                assertEquals(BeanUtils.describe(point), BeanUtils.describe(testPoint));
            }
        } finally {
            for (Point point : points) {
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }
}
