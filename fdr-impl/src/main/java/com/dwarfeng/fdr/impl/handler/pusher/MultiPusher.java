package com.dwarfeng.fdr.impl.handler.pusher;

import com.dwarfeng.fdr.impl.handler.Pusher;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.bean.entity.PersistenceValue;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.bean.entity.TriggeredValue;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 同时将消息推送给所有代理的多重推送器。
 *
 * @author DwArFeng
 * @since 1.4.0
 */
@Component
public class MultiPusher extends AbstractPusher {

    public static final String PUSHER_TYPE = "multi";
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiPusher.class);

    @Autowired
    private List<Pusher> pushers;

    @Value("${pusher.multi.delegate_types}")
    private String delegateTypes;

    private final List<Pusher> delegates = new ArrayList<>();

    public MultiPusher() {
        super(PUSHER_TYPE);
    }

    @PostConstruct
    public void init() throws HandlerException {
        StringTokenizer st = new StringTokenizer(delegateTypes, ",");
        while (st.hasMoreTokens()) {
            String delegateType = st.nextToken();
            delegates.add(pushers.stream().filter(p -> p.supportType(delegateType)).findAny()
                    .orElseThrow(() -> new HandlerException("未知的 pusher 类型: " + delegateType)));
        }
    }

    @Override
    public void dataFiltered(FilteredValue filteredValue) {
        for (Pusher delegate : delegates) {
            try {
                delegate.dataFiltered(filteredValue);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void dataFiltered(List<FilteredValue> filteredValues) {
        for (Pusher delegate : delegates) {
            try {
                delegate.dataFiltered(filteredValues);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void dataTriggered(TriggeredValue triggeredValue) {
        for (Pusher delegate : delegates) {
            try {
                delegate.dataTriggered(triggeredValue);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void dataTriggered(List<TriggeredValue> triggeredValues) {
        for (Pusher delegate : delegates) {
            try {
                delegate.dataTriggered(triggeredValues);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void realtimeUpdated(RealtimeValue realtimeValue) {
        for (Pusher delegate : delegates) {
            try {
                delegate.realtimeUpdated(realtimeValue);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void realtimeUpdated(List<RealtimeValue> realtimeValues) {
        for (Pusher delegate : delegates) {
            try {
                delegate.realtimeUpdated(realtimeValues);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void persistenceRecorded(PersistenceValue persistenceValue) {
        for (Pusher delegate : delegates) {
            try {
                delegate.persistenceRecorded(persistenceValue);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void persistenceRecorded(List<PersistenceValue> persistenceValues) {
        for (Pusher delegate : delegates) {
            try {
                delegate.persistenceRecorded(persistenceValues);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void recordReset() {
        for (Pusher delegate : delegates) {
            try {
                delegate.recordReset();
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void mapReset() {
        for (Pusher delegate : delegates) {
            try {
                delegate.mapReset();
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public String toString() {
        return "MultiPusher{" +
                "delegateTypes='" + delegateTypes + '\'' +
                ", pusherType='" + pusherType + '\'' +
                '}';
    }
}
