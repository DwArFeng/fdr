package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupSessionInfo;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.exception.MappingLookupSessionNotExistsException;
import com.dwarfeng.fdr.stack.exception.UnsupportedMapperTypeException;
import com.dwarfeng.fdr.stack.handler.MapLocalCacheHandler;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.handler.PersistenceValueMappingLookupHandler;
import com.dwarfeng.fdr.stack.service.PersistenceValueMaintainService;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import com.dwarfeng.subgrade.sdk.interceptor.analyse.SkipRecord;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.KeyFetcher;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Component
public class PersistenceValueMappingLookupHandlerImpl implements PersistenceValueMappingLookupHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceValueMappingLookupHandlerImpl.class);

    private final ApplicationContext ctx;

    private final PersistenceValueMaintainService maintainService;

    private final MapLocalCacheHandler mapLocalCacheHandler;

    private final ThreadPoolTaskExecutor executor;
    private final ThreadPoolTaskScheduler scheduler;

    private final KeyFetcher<LongIdKey> keyFetcher;

    @Value("${mapping_lookup.persistence_value.cleanup_task_cron}")
    private String cleanupTaskCron;
    @Value("${mapping_lookup.persistence_value.finished_session_min_retain_time}")
    private long finishedSessionMinRetainTime;
    @Value("${mapping_lookup.persistence_value.max_lookup_page_size}")
    private int maxLookupPageSize;
    @Value("${mapping_lookup.persistence_value.max_lookup_period_span}")
    private long maxLookupPeriodSpan;

    private final Map<LongIdKey, MappingLookupSession> sessionMap = new HashMap<>();
    private final Lock lock = new ReentrantLock();

    private ScheduledFuture<?> cleanTaskScheduledFuture;

    public PersistenceValueMappingLookupHandlerImpl(
            ApplicationContext ctx, PersistenceValueMaintainService maintainService,
            MapLocalCacheHandler mapLocalCacheHandler, ThreadPoolTaskExecutor executor,
            ThreadPoolTaskScheduler scheduler, KeyFetcher<LongIdKey> keyFetcher
    ) {
        this.ctx = ctx;
        this.maintainService = maintainService;
        this.mapLocalCacheHandler = mapLocalCacheHandler;
        this.executor = executor;
        this.scheduler = scheduler;
        this.keyFetcher = keyFetcher;
    }

    public void init() throws Exception {
        CleanupTask cleanupTask = ctx.getBean(CleanupTask.class, this);
        cleanTaskScheduledFuture = scheduler.schedule(cleanupTask, new CronTrigger(cleanupTaskCron));
    }

    public void dispose() {
        cleanTaskScheduledFuture.cancel(true);
        cleanTaskScheduledFuture = null;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<TimedValue> mappingLookup(MappingLookupInfo mappingLookupInfo) throws HandlerException {
        try {
            MappingLookupSession session = MappingLookupSession.of(keyFetcher.fetchKey(), mappingLookupInfo);

            LookupTask lookupTask = ctx.getBean(LookupTask.class, this, session);
            executor.submit(lookupTask);

            lock.lock();
            try {
                sessionMap.put(session.getKey(), session);
            } finally {
                lock.unlock();
            }

            return session.getResult();
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    @BehaviorAnalyse
    public LongIdKey mappingLookupAsync(MappingLookupInfo mappingLookupInfo) throws HandlerException {
        try {
            MappingLookupSession session = MappingLookupSession.of(keyFetcher.fetchKey(), mappingLookupInfo);

            LookupTask lookupTask = ctx.getBean(LookupTask.class, this, session);
            executor.submit(lookupTask);

            lock.lock();
            try {
                sessionMap.put(session.getKey(), session);
            } finally {
                lock.unlock();
            }

            return session.getKey();
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    @BehaviorAnalyse
    public void cancel(LongIdKey sessionKey) throws HandlerException {
        try {
            lock.lock();
            try {
                makeSureSessionKeyExists(sessionKey);
                sessionMap.get(sessionKey).cancel();
            } finally {
                lock.unlock();
            }
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<TimedValue> getResult(LongIdKey sessionKey) throws HandlerException {
        try {
            makeSureSessionKeyExists(sessionKey);
            return sessionMap.get(sessionKey).getResult();
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<TimedValue> getResult(LongIdKey sessionKey, long timeout) throws HandlerException {
        try {
            makeSureSessionKeyExists(sessionKey);
            return sessionMap.get(sessionKey).getResult(timeout);
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public MappingLookupSessionInfo getSessionInfo(LongIdKey sessionKey) throws HandlerException {
        try {
            makeSureSessionKeyExists(sessionKey);
            return sessionMap.get(sessionKey).getInfo();
        } catch (HandlerException e) {
            throw e;
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    private void makeSureSessionKeyExists(LongIdKey key) throws MappingLookupSessionNotExistsException {
        if (!sessionMap.containsKey(key)) {
            throw new MappingLookupSessionNotExistsException(key);
        }
    }

    @Override
    @BehaviorAnalyse
    @SkipRecord
    public List<MappingLookupSessionInfo> getSessionInfos() throws HandlerException {
        try {
            return sessionMap.values().stream().map(MappingLookupSession::getInfo)
                    .sorted(MappingLookupSessionInfoComparator.INSTANCE).collect(Collectors.toList());
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @Override
    @BehaviorAnalyse
    public void cleanup() throws HandlerException {
        try {
            lock.lock();
            try {
                internalCleanup();
            } finally {
                lock.unlock();
            }
        } catch (Exception e) {
            throw new HandlerException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void internalCleanup() {
        lock.lock();
        try {
            long currentTimeMillis = System.currentTimeMillis();
            sessionMap.values().removeIf(session -> {
                MappingLookupSessionInfo info = session.getInfo();
                if (!info.isFinishedFlag()) {
                    return false;
                }
                return (currentTimeMillis - info.getFinishedDate().getTime()) >= finishedSessionMinRetainTime;
            });
        } finally {
            lock.unlock();
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    class LookupTask implements Runnable {

        private final MappingLookupSession session;

        // 该构造函数不是用于自动装配的。
        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
        public LookupTask(MappingLookupSession session) {
            this.session = session;
        }

        @SuppressWarnings("DuplicatedCode")
        @Override
        @Transactional(transactionManager = "hibernateTransactionManager", readOnly = true, rollbackFor = Exception.class)
        public void run() {
            String mapperType = session.getMapperType();
            LongIdKey pointKey = session.getPointKey();
            Date startDate = session.getStartDate();
            Date endDate = session.getEndDate();
            Object[] mapperArgs = session.getMapperArgs();

            try {
                List<TimedValue> timedValues = new ArrayList<>();
                List<PersistenceValue> singleLookupResult;
                int anchorPage = 0;
                Date anchorStartDate;
                Date anchorEndDate = startDate;
                boolean notLastPeriodFlag;
                do {
                    anchorStartDate = anchorEndDate;
                    long maxPeriodGripTime = startDate.getTime() + maxLookupPeriodSpan;
                    long endTime = endDate.getTime();
                    notLastPeriodFlag = maxPeriodGripTime < endTime;
                    anchorEndDate = notLastPeriodFlag ? new Date(maxPeriodGripTime) : endDate;
                    do {
                        // 判断用户是否取消了本次会话。
                        if (session.isCanceledFlag()) {
                            session.finish();
                            return;
                        }
                        if (notLastPeriodFlag) {
                            singleLookupResult = maintainService.lookupAsList(
                                    PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN_RB_OPEN,
                                    new Object[]{pointKey, anchorStartDate, anchorEndDate},
                                    new PagingInfo(anchorPage, maxLookupPageSize)
                            );
                        } else {
                            singleLookupResult = maintainService.lookupAsList(
                                    PersistenceValueMaintainService.CHILD_FOR_POINT_BETWEEN,
                                    new Object[]{pointKey, anchorStartDate, anchorEndDate},
                                    new PagingInfo(anchorPage, maxLookupPageSize)
                            );
                        }
                        timedValues.addAll(singleLookupResult.stream().map(
                                value -> new TimedValue(value.getValue(), value.getHappenedDate())
                        ).collect(Collectors.toList()));
                        anchorPage += 1;
                    } while (singleLookupResult.size() >= maxLookupPageSize);
                } while (notLastPeriodFlag);

                // 判断用户是否取消了本次会话。
                if (session.isCanceledFlag()) {
                    session.finish();
                    return;
                }
                PersistenceValue rawPrevious = maintainService.lookupFirst(
                        PersistenceValueMaintainService.CHILD_FOR_POINT_PREVIOUS, new Object[]{pointKey, startDate}
                );
                TimedValue previous = new TimedValue(rawPrevious.getValue(), rawPrevious.getHappenedDate());
                PersistenceValue rawRear = maintainService.lookupFirst(
                        PersistenceValueMaintainService.CHILD_FOR_POINT_REAR, new Object[]{pointKey, endDate}
                );
                TimedValue rear = new TimedValue(rawRear.getValue(), rawRear.getHappenedDate());

                // 判断用户是否取消了本次会话。
                if (session.isCanceledFlag()) {
                    session.finish();
                    return;
                }
                Mapper mapper = checkAndGetMapper(mapperType);
                List<TimedValue> result = mapper.map(
                        new Mapper.MapData(timedValues, previous, rear, startDate, endDate, mapperArgs)
                );

                // 设置结果。
                session.setResult(result);
            } catch (HandlerException e) {
                session.setException(e);
            } catch (Exception e) {
                session.setException(new HandlerException(e));
            } finally {
                session.finish();
            }
        }

        private Mapper checkAndGetMapper(String mapperType) throws HandlerException {
            Mapper mapper = mapLocalCacheHandler.getMapper(mapperType);
            if (Objects.isNull(mapper)) {
                throw new UnsupportedMapperTypeException(mapperType);
            }
            return mapper;
        }
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    class CleanupTask implements Runnable {

        @Override
        public void run() {
            LOGGER.debug("持久值映射查询处理器: 清理计划定期执行...");
            internalCleanup();
        }
    }
}
