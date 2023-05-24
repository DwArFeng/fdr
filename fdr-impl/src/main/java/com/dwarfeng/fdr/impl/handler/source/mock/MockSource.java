package com.dwarfeng.fdr.impl.handler.source.mock;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fdr.impl.handler.source.AbstractSource;
import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Future;

@Component
public class MockSource extends AbstractSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockSource.class);

    private final MockSourceRandomGenerator randomGenerator;
    private final ThreadPoolTaskScheduler scheduler;

    @Value("${source.mock.data_size_per_point_per_sec}")
    private int dataSizePerPointPerSec;
    @Value("${source.mock.data_config}")
    private String dataConfig;

    private List<MockSourceDataConfigItem> dataConfigItems = null;
    private Map<String, Method> methodMap = null;
    private Future<?> taskFuture = null;

    public MockSource(
            MockSourceRandomGenerator randomGenerator,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.randomGenerator = randomGenerator;
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() {
        // 将 dataConfig 转换为 MockSourceDataConfigItem 的列表。
        dataConfigItems = JSON.parseArray(dataConfig, MockSourceDataConfigItem.class);
        // 构造 methodMap。
        methodMap = new HashMap<>();
        // 扫描 MockBridgeRandomGenerator 的所有带有 RequiredPointType 注解的方法，将其放入 methodMap。
        for (Method method : MockSourceRandomGenerator.class.getMethods()) {
            if (!method.isAnnotationPresent(RequiredPointType.class)) {
                continue;
            }
            RequiredPointType requiredPointType = method.getAnnotation(RequiredPointType.class);
            methodMap.put(requiredPointType.value(), method);
        }
    }

    @Override
    protected void doOnline() {
        if (Objects.nonNull(taskFuture)) {
            return;
        }
        taskFuture = scheduler.scheduleAtFixedRate(new RecordInfoGenerateTask(), 1000);
    }

    @Override
    protected void doOffline() {
        if (Objects.isNull(taskFuture)) {
            return;
        }
        taskFuture.cancel(true);
        taskFuture = null;
    }

    private final class RecordInfoGenerateTask implements Runnable {

        private Long lastTimestamp = null;

        @Override
        public void run() {
            try {
                runTask();
            } catch (Exception e) {
                LOGGER.warn("生成数据点时发生异常", e);
            }
        }

        private void runTask() throws Exception {
            if (Objects.isNull(lastTimestamp)) {
                lastTimestamp = System.currentTimeMillis();
                return;
            }

            long currentTimestamp = System.currentTimeMillis();

            List<Date> happendDateList = new ArrayList<>(dataSizePerPointPerSec);
            for (int i = 0; i < dataSizePerPointPerSec; i++) {
                Date happenedDate = new Date(
                        lastTimestamp + (currentTimestamp - lastTimestamp) * i / dataSizePerPointPerSec
                );
                happendDateList.add(happenedDate);
            }
            for (MockSourceDataConfigItem dataConfigItem : dataConfigItems) {
                String pointType = dataConfigItem.getPointType();
                if (!methodMap.containsKey(pointType)) {
                    LOGGER.warn("未知的数据点类型: " + pointType);
                    continue;
                }

                LongIdKey pointKey = new LongIdKey(dataConfigItem.getPointId());
                Method method = methodMap.get(pointType);

                for (Date happenedDate : happendDateList) {
                    context.record(new RecordInfo(
                            pointKey,
                            method.invoke(randomGenerator),
                            happenedDate
                    ));
                }
            }

            lastTimestamp = currentTimestamp;
        }
    }
}
