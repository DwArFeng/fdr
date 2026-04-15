package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.fdr.stack.bean.dto.PointCompositeLookupInfo;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.stack.bean.dto.PagedData;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Point 组合查询测试。
 *
 * @author WFM
 * @since 2.3.2
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
public class PointCompositeLookupTest {

    @Autowired
    private PointMaintainService pointMaintainService;

    private List<Point> points;

    @Before
    public void setUp() {
        points = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.JANUARY, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 创建 40 个测试 Point 对象，覆盖所有字段组合。
        for (int i = 0; i < 40; i++) {
            calendar.set(2024, Calendar.JANUARY, 1 + i, 0, 0, 0);
            Date date1 = calendar.getTime();
            calendar.set(2024, Calendar.JANUARY, 1 + i + 10, 0, 0, 0);
            Date date2 = calendar.getTime();

            // 字符串字段：使用有规律的命名，便于模糊匹配测试。
            String name = String.format("point_name_%03d", i);
            String remark = String.format("remark_%03d", i);
            String reservedStringAlpha = String.format("alpha_%03d", i);
            String reservedStringBravo = String.format("bravo_%03d", i);
            String reservedStringCharlie = String.format("charlie_%03d", i);
            String reservedStringDelta = String.format("delta_%03d", i);

            // 布尔字段：覆盖不同的组合。
            boolean normalKeepEnabled = (i % 2 == 0);
            boolean normalPersistEnabled = (i % 3 == 0);
            boolean filteredKeepEnabled = (i % 4 == 0);
            boolean filteredPersistEnabled = (i % 5 == 0);
            boolean triggeredKeepEnabled = (i % 6 == 0);
            boolean triggeredPersistEnabled = (i % 7 == 0);
            Boolean reservedBooleanAlpha = (i % 2 == 0) ? Boolean.TRUE : Boolean.FALSE;
            Boolean reservedBooleanBravo = (i % 3 == 0) ? Boolean.TRUE : Boolean.FALSE;

            // 数值字段：使用分段值，便于范围查询测试。
            Long reservedLongAlpha = (long) (i * 10);
            Long reservedLongBravo = (long) (i * 15);
            Integer reservedIntegerAlpha = i * 5;
            Integer reservedIntegerBravo = i * 8;

            Point point = new Point(
                    null, name, remark,
                    normalKeepEnabled, normalPersistEnabled,
                    filteredKeepEnabled, filteredPersistEnabled,
                    triggeredKeepEnabled, triggeredPersistEnabled,
                    12450,
                    reservedStringAlpha, reservedStringBravo,
                    reservedStringCharlie, reservedStringDelta,
                    reservedLongAlpha, reservedLongBravo,
                    reservedIntegerAlpha, reservedIntegerBravo,
                    reservedBooleanAlpha, reservedBooleanBravo,
                    date1, date2
            );
            points.add(point);
        }
    }

    @After
    public void tearDown() {
        points.clear();
    }

    @Test
    public void testEmptyLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行空查询（所有字段为 null）。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果：空查询应返回所有 points 的数据。
            assertNotNull(result);
            assertEquals(40, filteredResults.size());
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testNamePatternLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行名称模糊匹配查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setNamePattern("point_name_001");
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果。
            assertNotNull(result);
            assertEquals(1, filteredResults.size());
            // 验证返回的结果包含匹配的记录。
            assertTrue(filteredResults.get(0).getName().contains("point_name_001"));
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testRemarkPatternLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行备注模糊匹配查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setRemarkPattern("remark_002");
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果。
            assertNotNull(result);
            assertEquals(1, filteredResults.size());
            assertTrue(filteredResults.get(0).getRemark().contains("remark_002"));
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testNormalKeepEnabledLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 normalKeepEnabled 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setNormalKeepEnabled(true);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果：应该返回所有 normalKeepEnabled 为 true 的记录（i % 2 == 0，共 20 个）。
            assertNotNull(result);
            assertEquals(20, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.isNormalKeepEnabled());
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testNormalPersistEnabledLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 normalPersistEnabled 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setNormalPersistEnabled(true);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i % 3 == 0，共 14 个：0,3,6,9,12,15,18,21,24,27,30,33,36,39）。
            assertNotNull(result);
            assertEquals(14, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.isNormalPersistEnabled());
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testFilteredKeepEnabledLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 filteredKeepEnabled 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setFilteredKeepEnabled(true);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i % 4 == 0，共 10 个：0,4,8,12,16,20,24,28,32,36）。
            assertNotNull(result);
            assertEquals(10, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.isFilteredKeepEnabled());
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testFilteredPersistEnabledLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 filteredPersistEnabled 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setFilteredPersistEnabled(true);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i % 5 == 0，共 8 个：0,5,10,15,20,25,30,35）。
            assertNotNull(result);
            assertEquals(8, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.isFilteredPersistEnabled());
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testTriggeredKeepEnabledLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 triggeredKeepEnabled 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setTriggeredKeepEnabled(true);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i % 6 == 0，共 7 个：0,6,12,18,24,30,36）。
            assertNotNull(result);
            assertEquals(7, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.isTriggeredKeepEnabled());
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testTriggeredPersistEnabledLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 triggeredPersistEnabled 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setTriggeredPersistEnabled(true);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i % 7 == 0，共 6 个：0,7,14,21,28,35）。
            assertNotNull(result);
            assertEquals(6, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.isTriggeredPersistEnabled());
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedStringAlphaPatternLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedStringAlphaPattern 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedStringAlphaPattern("alpha_003");
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果。
            assertNotNull(result);
            assertEquals(1, filteredResults.size());
            assertTrue(filteredResults.get(0).getReservedStringAlpha().contains("alpha_003"));
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedStringBravoPatternLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedStringBravoPattern 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedStringBravoPattern("bravo_004");
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果。
            assertNotNull(result);
            assertEquals(1, filteredResults.size());
            assertTrue(filteredResults.get(0).getReservedStringBravo().contains("bravo_004"));
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedStringCharliePatternLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedStringCharliePattern 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedStringCharliePattern("charlie_005");
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果。
            assertNotNull(result);
            assertEquals(1, filteredResults.size());
            assertTrue(filteredResults.get(0).getReservedStringCharlie().contains("charlie_005"));
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedStringDeltaPatternLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedStringDeltaPattern 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedStringDeltaPattern("delta_006");
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果。
            assertNotNull(result);
            assertEquals(1, filteredResults.size());
            assertTrue(filteredResults.get(0).getReservedStringDelta().contains("delta_006"));
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedLongAlphaRangeLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedLongAlpha 范围查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedLongAlphaMin(50L);
            lookupInfo.setReservedLongAlphaMax(150L);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果：应该返回 reservedLongAlpha 在 50-150 范围内的记录（i=5 到 i=15，共 11 个）。
            assertNotNull(result);
            assertEquals(11, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.getReservedLongAlpha() >= 50L);
                assertTrue(point.getReservedLongAlpha() <= 150L);
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedLongBravoRangeLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedLongBravo 范围查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedLongBravoMin(75L);
            lookupInfo.setReservedLongBravoMax(225L);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i=5 到 i=15，共 11 个）。
            assertNotNull(result);
            assertEquals(11, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.getReservedLongBravo() >= 75L);
                assertTrue(point.getReservedLongBravo() <= 225L);
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedIntegerAlphaRangeLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedIntegerAlpha 范围查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedIntegerAlphaMin(25);
            lookupInfo.setReservedIntegerAlphaMax(75);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i=5 到 i=15，共 11 个）。
            assertNotNull(result);
            assertEquals(11, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.getReservedIntegerAlpha() >= 25);
                assertTrue(point.getReservedIntegerAlpha() <= 75);
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedIntegerBravoRangeLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedIntegerBravo 范围查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedIntegerBravoMin(40);
            lookupInfo.setReservedIntegerBravoMax(120);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i=5 到 i=15，共 11 个）。
            assertNotNull(result);
            assertEquals(11, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.getReservedIntegerBravo() >= 40);
                assertTrue(point.getReservedIntegerBravo() <= 120);
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedBooleanAlphaLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedBooleanAlpha 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedBooleanAlpha(true);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i % 2 == 0，共 20 个）。
            assertNotNull(result);
            assertEquals(20, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.getReservedBooleanAlpha());
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedBooleanBravoLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedBooleanBravo 查询。
            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedBooleanBravo(true);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i % 3 == 0，共 14 个：0,3,6,9,12,15,18,21,24,27,30,33,36,39）。
            assertNotNull(result);
            assertEquals(14, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.getReservedBooleanBravo());
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedDateAlphaRangeLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedDateAlpha 范围查询。
            Calendar calendar = Calendar.getInstance();
            calendar.set(2024, Calendar.JANUARY, 5, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date minDate = calendar.getTime();
            calendar.set(2024, Calendar.JANUARY, 15, 0, 0, 0);
            Date maxDate = calendar.getTime();

            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedDateAlphaMin(minDate);
            lookupInfo.setReservedDateAlphaMax(maxDate);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i=4 到 i=14，共 11 个）。
            assertNotNull(result);
            assertEquals(11, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.getReservedDateAlpha().compareTo(minDate) >= 0);
                assertTrue(point.getReservedDateAlpha().compareTo(maxDate) <= 0);
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @Test
    public void testReservedDateBravoRangeLookup() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行 reservedDateBravo 范围查询。
            Calendar calendar = Calendar.getInstance();
            calendar.set(2024, Calendar.JANUARY, 10, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date minDate = calendar.getTime();
            calendar.set(2024, Calendar.JANUARY, 20, 0, 0, 0);
            Date maxDate = calendar.getTime();

            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            lookupInfo.setReservedDateBravoMin(minDate);
            lookupInfo.setReservedDateBravoMax(maxDate);
            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果（i=0 到 i=9，共 10 个）。
            assertNotNull(result);
            assertEquals(10, filteredResults.size());
            for (Point point : filteredResults) {
                assertTrue(point.getReservedDateBravo().compareTo(minDate) >= 0);
                assertTrue(point.getReservedDateBravo().compareTo(maxDate) <= 0);
            }
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }

    @SuppressWarnings("ExtractMethodRecommender")
    @Test
    public void testCompositeLookupAllFields() throws Exception {
        try {
            // 插入测试数据并创建主键 Set。
            Set<LongIdKey> testPointKeys = new HashSet<>();
            for (Point point : points) {
                point.setKey(pointMaintainService.insert(point));
                testPointKeys.add(point.getKey());
            }

            // 执行所有字段复合查询。
            Calendar calendar = Calendar.getInstance();
            calendar.set(2024, Calendar.JANUARY, 5, 0, 0, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date dateMin = calendar.getTime();
            calendar.set(2024, Calendar.JANUARY, 25, 0, 0, 0);
            Date dateMax = calendar.getTime();

            PointCompositeLookupInfo lookupInfo = new PointCompositeLookupInfo();
            // 字符串字段。
            lookupInfo.setNamePattern("point_name_010");
            lookupInfo.setRemarkPattern("remark_010");
            lookupInfo.setReservedStringAlphaPattern("alpha_010");
            lookupInfo.setReservedStringBravoPattern("bravo_010");
            lookupInfo.setReservedStringCharliePattern("charlie_010");
            lookupInfo.setReservedStringDeltaPattern("delta_010");
            // 布尔字段。
            lookupInfo.setNormalKeepEnabled(true);
            lookupInfo.setNormalPersistEnabled(false);
            lookupInfo.setFilteredKeepEnabled(false);
            lookupInfo.setFilteredPersistEnabled(true);
            lookupInfo.setTriggeredKeepEnabled(false);
            lookupInfo.setTriggeredPersistEnabled(false);
            lookupInfo.setReservedBooleanAlpha(true);
            lookupInfo.setReservedBooleanBravo(false);
            // 范围查询字段。
            lookupInfo.setReservedLongAlphaMin(90L);
            lookupInfo.setReservedLongAlphaMax(110L);
            lookupInfo.setReservedLongBravoMin(135L);
            lookupInfo.setReservedLongBravoMax(165L);
            lookupInfo.setReservedIntegerAlphaMin(45);
            lookupInfo.setReservedIntegerAlphaMax(55);
            lookupInfo.setReservedIntegerBravoMin(72);
            lookupInfo.setReservedIntegerBravoMax(88);
            lookupInfo.setReservedDateAlphaMin(dateMin);
            lookupInfo.setReservedDateAlphaMax(dateMax);
            lookupInfo.setReservedDateBravoMin(dateMin);
            lookupInfo.setReservedDateBravoMax(dateMax);

            PagedData<Point> result = pointMaintainService.lookup(
                    PointMaintainService.COMPOSITE_LOOKUP,
                    new Object[]{lookupInfo}
            );

            // 从查询结果中筛选出 key 在 testPointKeys 中的记录。
            List<Point> filteredResults = new ArrayList<>();
            for (Point resultPoint : result.getData()) {
                if (resultPoint.getKey() != null && testPointKeys.contains(resultPoint.getKey())) {
                    filteredResults.add(resultPoint);
                }
            }

            // 验证结果：应该返回满足所有条件的记录（i=10 的记录应该匹配）。
            assertNotNull(result);
            assertEquals(1, filteredResults.size());
            // 验证返回的记录满足所有条件。
            Point matchedPoint = filteredResults.get(0);
            assertTrue(matchedPoint.getName().contains("point_name_010"));
            assertTrue(matchedPoint.getRemark().contains("remark_010"));
            assertTrue(matchedPoint.isNormalKeepEnabled());
            assertFalse(matchedPoint.isNormalPersistEnabled());
            assertFalse(matchedPoint.isFilteredKeepEnabled());
            assertTrue(matchedPoint.isFilteredPersistEnabled());
            assertFalse(matchedPoint.isTriggeredKeepEnabled());
            assertFalse(matchedPoint.isTriggeredPersistEnabled());
        } finally {
            // 清理数据。
            for (Point point : points) {
                if (Objects.isNull(point.getKey())) {
                    continue;
                }
                pointMaintainService.deleteIfExists(point.getKey());
            }
        }
    }
}
