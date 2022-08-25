package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.fdr.impl.handler.mapper.GroovyMapperRegistry;
import com.dwarfeng.fdr.impl.handler.mapper.MinMapperRegistry;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.fdr.stack.service.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-context*.xml")
@Deprecated
public class MappingLookupServiceImplTest {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private MappingLookupService mappingLookupService;
    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private PersistenceValueMaintainService persistenceValueMaintainService;
    @Autowired
    private FilteredValueMaintainService filteredValueMaintainService;
    @Autowired
    private TriggeredValueMaintainService triggeredValueMaintainService;

    private Point parentPoint;
    private List<PersistenceValue> persistenceValues;
    private List<FilteredValue> filteredValues;
    private List<TriggeredValue> triggeredValues;

    @Before
    public void setUp() {
        parentPoint = new Point(null, "parent-point", "test-point", true, true);
        persistenceValues = new ArrayList<>();
        filteredValues = new ArrayList<>();
        triggeredValues = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PersistenceValue persistenceValue = new PersistenceValue(
                    null, null, new Date(i * 1000), Integer.toString(i)
            );
            persistenceValues.add(persistenceValue);
            FilteredValue filteredValue = new FilteredValue(
                    null, null, null, new Date(i * 1000), Integer.toString(i), "你猜"
            );
            filteredValues.add(filteredValue);
            TriggeredValue triggeredValue = new TriggeredValue(
                    null, null, null, new Date(i * 1000), Integer.toString(i), "你猜"
            );
            triggeredValues.add(triggeredValue);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        persistenceValues = null;
        filteredValues = null;
        triggeredValues = null;
    }

    @Test
    public void testPersistenceValue() throws Exception {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValue.setPointKey(parentPoint.getKey());
                persistenceValue.setKey(persistenceValueMaintainService.insert(persistenceValue));
            }

            Resource resource = ctx.getResource("classpath:groovy/ExampleMapperProcessor.groovy");
            String groovyCode;
            try (InputStream in = resource.getInputStream();
                 StringOutputStream out = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(in, out, 4096);
                out.flush();
                groovyCode = out.toString();
            }

            // Groovy映射器测试。
            List<TimedValue> timedValues = mappingLookupService.mappingPersistenceValue(
                    GroovyMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(1000), new Date(1000 * 50),
                    new Object[]{groovyCode, 10}
            );
            assertEquals(10, timedValues.size());
            for (int i = 0; i < timedValues.size(); i++) {
                assertEquals(persistenceValues.get(i + 1).getValue(), timedValues.get(i).getValue());
            }

            // 最小值映射器测试。
            timedValues = mappingLookupService.mappingPersistenceValue(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2000), new Date(2500),
                    new Object[]{false}
            );
            assertEquals(1, timedValues.size());
            assertEquals(persistenceValues.get(2).getValue(), timedValues.get(0).getValue());
            timedValues = mappingLookupService.mappingPersistenceValue(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2250), new Date(2750),
                    new Object[]{false}
            );
            assertEquals(0, timedValues.size());
            timedValues = mappingLookupService.mappingPersistenceValue(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2250), new Date(2750),
                    new Object[]{true}
            );
            assertEquals(1, timedValues.size());
            assertEquals(persistenceValues.get(2).getValue(), timedValues.get(0).getValue());
        } finally {
            for (PersistenceValue persistenceValue : persistenceValues) {
                persistenceValueMaintainService.deleteIfExists(persistenceValue.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }

    @Test
    public void testFilteredValue() throws Exception {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (FilteredValue filteredValue : filteredValues) {
                filteredValue.setPointKey(parentPoint.getKey());
                filteredValue.setKey(filteredValueMaintainService.insert(filteredValue));
            }

            Resource resource = ctx.getResource("classpath:groovy/ExampleMapperProcessor.groovy");
            String groovyCode;
            try (InputStream in = resource.getInputStream();
                 StringOutputStream out = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(in, out, 4096);
                out.flush();
                groovyCode = out.toString();
            }

            // Groovy映射器测试。
            List<TimedValue> timedValues = mappingLookupService.mappingFilteredValue(
                    GroovyMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(1000), new Date(1000 * 50),
                    new Object[]{groovyCode, 10}
            );
            assertEquals(10, timedValues.size());
            for (int i = 0; i < timedValues.size(); i++) {
                assertEquals(filteredValues.get(i + 1).getValue(), timedValues.get(i).getValue());
            }

            // 最小值映射器测试。
            timedValues = mappingLookupService.mappingFilteredValue(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2000), new Date(2500),
                    new Object[]{false}
            );
            assertEquals(1, timedValues.size());
            assertEquals(filteredValues.get(2).getValue(), timedValues.get(0).getValue());
            timedValues = mappingLookupService.mappingFilteredValue(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2250), new Date(2750),
                    new Object[]{false}
            );
            assertEquals(0, timedValues.size());
            timedValues = mappingLookupService.mappingFilteredValue(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2250), new Date(2750),
                    new Object[]{true}
            );
            assertEquals(1, timedValues.size());
            assertEquals(filteredValues.get(2).getValue(), timedValues.get(0).getValue());
        } finally {
            for (FilteredValue filteredValue : filteredValues) {
                filteredValueMaintainService.deleteIfExists(filteredValue.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }

    @Test
    public void testTriggeredValue() throws Exception {
        try {
            parentPoint.setKey(pointMaintainService.insert(parentPoint));
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValue.setPointKey(parentPoint.getKey());
                triggeredValue.setKey(triggeredValueMaintainService.insert(triggeredValue));
            }

            Resource resource = ctx.getResource("classpath:groovy/ExampleMapperProcessor.groovy");
            String groovyCode;
            try (InputStream in = resource.getInputStream();
                 StringOutputStream out = new StringOutputStream(StandardCharsets.UTF_8, true)) {
                IOUtil.trans(in, out, 4096);
                out.flush();
                groovyCode = out.toString();
            }

            // Groovy映射器测试。
            List<TimedValue> timedValues = mappingLookupService.mappingTriggeredValue(
                    GroovyMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(1000), new Date(1000 * 50),
                    new Object[]{groovyCode, 10}
            );
            assertEquals(10, timedValues.size());
            for (int i = 0; i < timedValues.size(); i++) {
                assertEquals(triggeredValues.get(i + 1).getValue(), timedValues.get(i).getValue());
            }

            // 最小值映射器测试。
            timedValues = mappingLookupService.mappingTriggeredValue(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2000), new Date(2500),
                    new Object[]{false}
            );
            assertEquals(1, timedValues.size());
            assertEquals(triggeredValues.get(2).getValue(), timedValues.get(0).getValue());
            timedValues = mappingLookupService.mappingTriggeredValue(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2250), new Date(2750),
                    new Object[]{false}
            );
            assertEquals(0, timedValues.size());
            timedValues = mappingLookupService.mappingTriggeredValue(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2250), new Date(2750),
                    new Object[]{true}
            );
            assertEquals(1, timedValues.size());
            assertEquals(triggeredValues.get(2).getValue(), timedValues.get(0).getValue());
        } finally {
            for (TriggeredValue triggeredValue : triggeredValues) {
                triggeredValueMaintainService.deleteIfExists(triggeredValue.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }
}
