package com.dwarfeng.fdr.impl.handler.fetcher.mock.lf;

import com.dwarfeng.fdr.sdk.util.RecordInfoUtil;
import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.fdr.stack.handler.FetcherSession;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * 低频模拟抓取器抓取任务。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MockLfFetcherFetchTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockLfFetcherFetchTask.class);

    private final FetcherSession.Context context;

    private final MockLfFetcherValueGenerator valueGenerator;

    private final LongIdKey pointKey;

    private final long fetchBeforeDelay;
    private final long fetchAfterDelay;

    public MockLfFetcherFetchTask(
            FetcherSession.Context context,
            MockLfFetcherValueGenerator valueGenerator,
            LongIdKey pointKey,
            long fetchBeforeDelay,
            long fetchAfterDelay
    ) {
        this.context = context;
        this.valueGenerator = valueGenerator;
        this.pointKey = pointKey;
        this.fetchBeforeDelay = fetchBeforeDelay;
        this.fetchAfterDelay = fetchAfterDelay;
    }

    @BehaviorAnalyse
    @Override
    public void run() {
        try {
            LOGGER.debug("抓取任务开始...");
            // 生成值。
            Object value = valueGenerator.generateValue();
            LOGGER.debug("抓取任务生成值: {}", value);
            // 构造模拟信息，并抓取。
            RecordInfo recordInfo = RecordInfoUtil.newInstance(pointKey, value, Instant.now());
            sleepIfNecessary(fetchBeforeDelay);
            context.record(recordInfo);
            sleepIfNecessary(fetchAfterDelay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warn("抓取任务执行时发生异常, 放弃数据抓取 1 次, 异常信息如下: ", e);
        } catch (Exception e) {
            LOGGER.warn("抓取任务执行时发生异常, 放弃数据抓取 1 次, 异常信息如下: ", e);
        }
    }

    /**
     * 如果延迟时长大于 0，则阻塞当前线程。
     *
     * @param delay 阻塞时长。
     * @throws InterruptedException 线程在阻塞期间被中断。
     */
    private void sleepIfNecessary(long delay) throws InterruptedException {
        if (delay <= 0) {
            return;
        }
        Thread.sleep(delay);
    }

    @Override
    public String toString() {
        return "MockLfFetcherFetchTask{" +
                "context=" + context +
                ", valueGenerator=" + valueGenerator +
                ", pointKey=" + pointKey +
                ", fetchBeforeDelay=" + fetchBeforeDelay +
                ", fetchAfterDelay=" + fetchAfterDelay +
                '}';
    }
}
