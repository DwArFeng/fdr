package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupSessionInfo;
import com.dwarfeng.fdr.stack.exception.MappingLookupSessionCanceledException;
import com.dwarfeng.fdr.stack.exception.MappingLookupTimeoutException;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 映射查询
 *
 * @author DwArFeng
 * @since 1.10.0
 */
class MappingLookupSession {

    public static MappingLookupSession of(LongIdKey sessionKey, MappingLookupInfo info) {
        return new MappingLookupSession(
                sessionKey, info.getMapperType(), info.getPointKey(), info.getStartDate(), info.getEndDate(),
                info.getMapperArgs(), new Date(), false, null, false, null, 0, info.getStartDate(), null, null
        );
    }

    private final LongIdKey key;
    private final String mapperType;
    private final LongIdKey pointKey;
    private final Date startDate;
    private final Date endDate;
    private final Object[] mapperArgs;
    private final Date createdDate;

    private boolean canceledFlag;
    private Date canceledDate;
    private boolean finishedFlag;
    private Date finishedDate;
    private int fetchedSize;
    private Date currentPeriodStartDate;
    private List<TimedValue> result;
    private HandlerException exception;

    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();

    private MappingLookupSession(
            LongIdKey key, String mapperType, LongIdKey pointKey, Date startDate, Date endDate,
            Object[] mapperArgs, Date createdDate, boolean canceledFlag, Date canceledDate, boolean finishedFlag,
            Date finishedDate, int fetchedSize, Date currentPeriodStartDate, List<TimedValue> result,
            HandlerException exception
    ) {
        this.key = key;
        this.mapperType = mapperType;
        this.pointKey = pointKey;
        this.startDate = startDate;
        this.endDate = endDate;
        this.mapperArgs = mapperArgs;
        this.createdDate = createdDate;
        this.canceledFlag = canceledFlag;
        this.canceledDate = canceledDate;
        this.finishedFlag = finishedFlag;
        this.finishedDate = finishedDate;
        this.fetchedSize = fetchedSize;
        this.currentPeriodStartDate = currentPeriodStartDate;
        this.result = result;
        this.exception = exception;
    }

    public LongIdKey getKey() {
        return key;
    }

    public String getMapperType() {
        return mapperType;
    }

    public LongIdKey getPointKey() {
        return pointKey;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Object[] getMapperArgs() {
        return mapperArgs;
    }

    public boolean isCanceledFlag() {
        lock.lock();
        try {
            return canceledFlag;
        } finally {
            lock.unlock();
        }
    }

    public MappingLookupSessionInfo getInfo() {
        lock.lock();
        try {
            return new MappingLookupSessionInfo(
                    key, mapperType, pointKey, startDate, endDate, mapperArgs, createdDate, canceledFlag, canceledDate,
                    finishedFlag, finishedDate,
                    fetchedSize, currentPeriodStartDate);
        } finally {
            lock.unlock();
        }
    }

    public List<TimedValue> getResult() throws HandlerException {
        lock.lock();
        try {
            while (!finishedFlag) {
                try {
                    condition.await();
                } catch (InterruptedException ignored) {
                    // 抛异常也要按照基本法。
                }
            }

            if (canceledFlag) {
                throw new MappingLookupSessionCanceledException();
            }

            if (Objects.nonNull(exception)) {
                throw exception;
            }

            return result;
        } finally {
            lock.unlock();
        }
    }

    public List<TimedValue> getResult(long timeout) throws HandlerException {
        Date deadLine = new Date(System.currentTimeMillis() + timeout);
        boolean stillWaiting = true;

        lock.lock();
        try {
            while (!finishedFlag && stillWaiting) {
                try {
                    stillWaiting = condition.awaitUntil(deadLine);
                } catch (InterruptedException ignored) {
                    // 抛异常也要按照基本法。
                }
            }

            if (!stillWaiting) {
                throw new MappingLookupTimeoutException(timeout);
            }

            if (canceledFlag) {
                throw new MappingLookupSessionCanceledException();
            }

            if (Objects.nonNull(exception)) {
                throw exception;
            }

            return result;
        } finally {
            lock.unlock();
        }
    }

    public void cancel() {
        lock.lock();
        try {
            canceledFlag = true;
            canceledDate = new Date();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void setResult(List<TimedValue> result) {
        lock.lock();
        try {
            this.result = result;
        } finally {
            lock.unlock();
        }
    }

    public void setException(HandlerException exception) {
        lock.lock();
        try {
            this.exception = exception;
        } finally {
            lock.unlock();
        }
    }

    public void finish() {
        lock.lock();
        try {
            finishedFlag = true;
            finishedDate = new Date();
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void setFetchedSize(int fetchedSize) {
        lock.lock();
        try {
            this.fetchedSize = fetchedSize;
        } finally {
            lock.unlock();
        }
    }

    public void setCurrentPeriodStartDate(Date currentPeriodStartDate) {
        lock.lock();
        try {
            this.currentPeriodStartDate = currentPeriodStartDate;
        } finally {
            lock.unlock();
        }
    }
}
