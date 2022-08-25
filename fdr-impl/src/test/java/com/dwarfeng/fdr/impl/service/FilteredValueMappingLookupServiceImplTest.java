package com.dwarfeng.fdr.impl.service;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.fdr.impl.handler.mapper.GroovyMapperRegistry;
import com.dwarfeng.fdr.impl.handler.mapper.MinMapperRegistry;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import com.dwarfeng.fdr.stack.service.FilteredValueMappingLookupService;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
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
public class FilteredValueMappingLookupServiceImplTest {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private PointMaintainService pointMaintainService;
    @Autowired
    private FilteredValueMaintainService filteredValueMaintainService;
    @Autowired
    private FilteredValueMappingLookupService filteredValueMappingLookupService;

    private Point parentPoint;
    private List<FilteredValue> filteredValues;

    @Before
    public void setUp() {
        parentPoint = new Point(null, "parent-point", "test-point", true, true);
        filteredValues = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            FilteredValue filteredValue = new FilteredValue(
                    null, null, null, new Date(i * 1000), Integer.toString(i), "你猜"
            );
            filteredValues.add(filteredValue);
        }
    }

    @After
    public void tearDown() {
        parentPoint = null;
        filteredValues = null;
    }

    @Test
    public void testMappingLookup() throws Exception {
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
            List<TimedValue> timedValues = filteredValueMappingLookupService.mappingLookup(new MappingLookupInfo(
                    GroovyMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(1000), new Date(1000 * 50),
                    new Object[]{groovyCode, 10}
            ));
            assertEquals(10, timedValues.size());
            for (int i = 0; i < timedValues.size(); i++) {
                assertEquals(filteredValues.get(i + 1).getValue(), timedValues.get(i).getValue());
            }

            // 最小值映射器测试。
            timedValues = filteredValueMappingLookupService.mappingLookup(new MappingLookupInfo(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2000), new Date(2500),
                    new Object[]{false}
            ));
            assertEquals(1, timedValues.size());
            assertEquals(filteredValues.get(2).getValue(), timedValues.get(0).getValue());
            timedValues = filteredValueMappingLookupService.mappingLookup(new MappingLookupInfo(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2250), new Date(2750),
                    new Object[]{false}
            ));
            assertEquals(0, timedValues.size());
            timedValues = filteredValueMappingLookupService.mappingLookup(new MappingLookupInfo(
                    MinMapperRegistry.MAPPER_TYPE, parentPoint.getKey(), new Date(2250), new Date(2750),
                    new Object[]{true}
            ));
            assertEquals(1, timedValues.size());
            assertEquals(filteredValues.get(2).getValue(), timedValues.get(0).getValue());
        } finally {
            for (FilteredValue filteredValue : filteredValues) {
                filteredValueMaintainService.deleteIfExists(filteredValue.getKey());
            }
            pointMaintainService.deleteIfExists(parentPoint.getKey());
        }
    }
}
