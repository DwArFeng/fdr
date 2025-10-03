package com.dwarfeng.fdr.impl.handler.source.mock.historical;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.fdr.sdk.handler.source.AbstractSource;
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
 * 历史模拟数据源。
 *
 * <p>
 * 历史数据源可以根据配置文件中的配置，生成一段时间内的模拟数据。<br>
 * 在配置中指定历史数据的起止时间，两个数据之间的发生时间的增量，以及数据点配置。<br>
 * 数据源会每秒对每个数据点生成一条数据，生成的数据量可以通过配置文件中的配置进行配置，
 * 使用较小的数据量可以降低数据处理与记录的压力，但是会增加数据生成完成的时间。<br>
 * 全部数据生成完成后，数据源将会停止生成数据。
 *
 * <p>
 * 对数据源进行下线操作后，数据源会暂停生成数据。数据源再次上线后，会从上次生成的数据点开始继续生成数据，而不是重新开始。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class HistoricalMockSource extends AbstractSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricalMockSource.class);

    private final HistoricalMockSourceRandomGenerator randomGenerator;
    private final ThreadPoolTaskScheduler scheduler;

    @Value("${source.mock.historical.data_size_per_point_per_sec}")
    private int dataSizePerPointPerSec;
    @Value("${source.mock.historical.start_date}")
    private String startDate;
    @Value("${source.mock.historical.end_date}")
    private String endDate;
    @Value("${source.mock.historical.happened_date_increment}")
    private long happenedDateIncrement;
    @Value("${source.mock.historical.data_config}")
    private String dataConfig;

    private long anchorTimestamp = 0;
    private long endTimestamp = 0;
    private List<HistoricalMockSourceDataConfigItem> dataConfigItems = null;
    private Map<String, Method> methodMap = null;
    private Future<?> taskFuture = null;

    public HistoricalMockSource(
            HistoricalMockSourceRandomGenerator randomGenerator,
            ThreadPoolTaskScheduler scheduler
    ) {
        this.randomGenerator = randomGenerator;
        this.scheduler = scheduler;
    }

    @PostConstruct
    public void init() {
        // 解析锚点时间戳、结束时间戳。
        anchorTimestamp = TimeUtil.dateString2Timestamp(startDate);
        endTimestamp = TimeUtil.dateString2Timestamp(endDate);

        // 将 dataConfig 转换为 HistoricalMockSourceDataConfigItem 的列表。
        dataConfigItems = JSON.parseArray(dataConfig, HistoricalMockSourceDataConfigItem.class);
        // 构造 methodMap。
        methodMap = new HashMap<>();
        // 扫描 MockBridgeRandomGenerator 的所有带有 RequiredPointType 注解的方法，将其放入 methodMap。
        for (Method method : HistoricalMockSourceRandomGenerator.class.getMethods()) {
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

        @Override
        public void run() {
            lock.lock();
            try {
                runTask();
            } catch (Exception e) {
                LOGGER.warn("生成数据点时发生异常", e);
            } finally {
                lock.unlock();
            }
        }

        private void runTask() throws Exception {
            // 如果锚点时间戳大于等于结束时间戳，则停止生成数据。
            if (anchorTimestamp >= endTimestamp) {
                LOGGER.debug("锚点时间戳大于等于结束时间戳，停止生成数据。");
                return;
            }

            // 最多生成 dataSizePerPointPerSec 个数据点，每循环一次，锚点时间戳增加 happenedDateIncrement。
            // 如果锚点时间戳增加后大于等于结束时间戳，则退出循环。
            for (int i = 0; i < dataSizePerPointPerSec; i++) {
                // 对每个数据点生成数据，数据的发生时间戳是锚点时间戳。
                for (HistoricalMockSourceDataConfigItem dataConfigItem : dataConfigItems) {
                    String pointType = dataConfigItem.getPointType();
                    if (!methodMap.containsKey(pointType)) {
                        LOGGER.warn("未知的数据点类型: {}", pointType);
                        continue;
                    }

                    LongIdKey pointKey = new LongIdKey(dataConfigItem.getPointId());
                    Method method = methodMap.get(pointType);

                    context.record(new RecordInfo(
                            pointKey,
                            method.invoke(randomGenerator),
                            new Date(anchorTimestamp)
                    ));
                }

                // 锚点时间戳增加 happenedDateIncrement。
                anchorTimestamp += happenedDateIncrement;

                // 如果锚点时间戳大于等于结束时间戳，则退出循环。
                if (anchorTimestamp >= endTimestamp) {
                    LOGGER.info("所有数据点已经生成完成，停止生成数据。");
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "HistoricalMockSource{" +
                "randomGenerator=" + randomGenerator +
                ", scheduler=" + scheduler +
                ", dataSizePerPointPerSec=" + dataSizePerPointPerSec +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", happenedDateIncrement=" + happenedDateIncrement +
                ", dataConfig='" + dataConfig + '\'' +
                ", anchorTimestamp=" + anchorTimestamp +
                ", endTimestamp=" + endTimestamp +
                ", dataConfigItems=" + dataConfigItems +
                ", methodMap=" + methodMap +
                ", taskFuture=" + taskFuture +
                ", context=" + context +
                ", onlineFlag=" + onlineFlag +
                '}';
    }
}
