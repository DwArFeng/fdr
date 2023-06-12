package com.dwarfeng.fdr.impl.handler.source.mock.realtime;

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

/**
 * 实时模拟数据源。
 *
 * <p>
 * 实时数据源可以根据配置文件中的配置，生成基于当前时间的模拟数据。<br>
 * 该数据源对每个配置的数据点每秒生成一次数据，每次的生成数量以及类型由配置文件中的配置决定，
 * 生成的数据点的发生时间平均分布在当前时间与上一次生成时间之间。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class RealtimeMockSource extends AbstractSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeMockSource.class);

    private final RealtimeMockSourceRandomGenerator randomGenerator;
    private final ThreadPoolTaskScheduler scheduler;

    @Value("${source.mock.realtime.data_size_per_point_per_sec}")
    private int dataSizePerPointPerSec;
    @Value("${source.mock.realtime.data_config}")
    private String dataConfig;

    private List<RealtimeMockSourceDataConfigItem> dataConfigItems = null;
    private Map<String, Method> methodMap = null;
    private Future<?> taskFuture = null;

    public RealtimeMockSource(
            RealtimeMockSourceRandomGenerator randomGenerator,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.randomGenerator = randomGenerator;
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() {
        // 将 dataConfig 转换为 RealtimeMockSourceDataConfigItem 的列表。
        dataConfigItems = JSON.parseArray(dataConfig, RealtimeMockSourceDataConfigItem.class);
        // 构造 methodMap。
        methodMap = new HashMap<>();
        // 扫描 MockBridgeRandomGenerator 的所有带有 RequiredPointType 注解的方法，将其放入 methodMap。
        for (Method method : RealtimeMockSourceRandomGenerator.class.getMethods()) {
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
            for (RealtimeMockSourceDataConfigItem dataConfigItem : dataConfigItems) {
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

    @Override
    public String toString() {
        return "RealtimeMockSource{" +
                "randomGenerator=" + randomGenerator +
                ", scheduler=" + scheduler +
                ", dataSizePerPointPerSec=" + dataSizePerPointPerSec +
                ", dataConfig='" + dataConfig + '\'' +
                ", dataConfigItems=" + dataConfigItems +
                ", methodMap=" + methodMap +
                ", taskFuture=" + taskFuture +
                ", context=" + context +
                ", onlineFlag=" + onlineFlag +
                '}';
    }
}
