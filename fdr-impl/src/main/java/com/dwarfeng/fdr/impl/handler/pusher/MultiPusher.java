package com.dwarfeng.fdr.impl.handler.pusher;

import com.dwarfeng.fdr.impl.handler.Pusher;
import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

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

    private final List<Pusher> pushers;

    @Value("${pusher.multi.delegate_types}")
    private String delegateTypes;

    private final List<Pusher> delegates = new ArrayList<>();

    public MultiPusher(List<Pusher> pushers) {
        super(PUSHER_TYPE);
        this.pushers = Optional.ofNullable(pushers).orElse(Collections.emptyList());
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
    public void normalUpdated(NormalData normalRecord) {
        for (Pusher delegate : delegates) {
            try {
                delegate.normalUpdated(normalRecord);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void normalUpdated(List<NormalData> normalRecords) {
        for (Pusher delegate : delegates) {
            try {
                delegate.normalUpdated(normalRecords);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void normalRecorded(NormalData normalRecord) {
        for (Pusher delegate : delegates) {
            try {
                delegate.normalRecorded(normalRecord);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void normalRecorded(List<NormalData> normalRecords) {
        for (Pusher delegate : delegates) {
            try {
                delegate.normalRecorded(normalRecords);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void filteredUpdated(FilteredData filteredRecord) {
        for (Pusher delegate : delegates) {
            try {
                delegate.filteredUpdated(filteredRecord);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void filteredUpdated(List<FilteredData> filteredRecords) {
        for (Pusher delegate : delegates) {
            try {
                delegate.filteredUpdated(filteredRecords);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void filteredRecorded(FilteredData filteredRecord) {
        for (Pusher delegate : delegates) {
            try {
                delegate.filteredRecorded(filteredRecord);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void filteredRecorded(List<FilteredData> filteredRecords) {
        for (Pusher delegate : delegates) {
            try {
                delegate.filteredRecorded(filteredRecords);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void triggeredUpdated(TriggeredData triggeredRecord) {
        for (Pusher delegate : delegates) {
            try {
                delegate.triggeredUpdated(triggeredRecord);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void triggeredUpdated(List<TriggeredData> triggeredRecords) {
        for (Pusher delegate : delegates) {
            try {
                delegate.triggeredUpdated(triggeredRecords);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void triggeredRecorded(TriggeredData triggeredRecord) {
        for (Pusher delegate : delegates) {
            try {
                delegate.triggeredRecorded(triggeredRecord);
            } catch (Exception e) {
                LOGGER.warn("代理推送器推送数据失败，异常信息如下: ", e);
            }
        }
    }

    @Override
    public void triggeredRecorded(List<TriggeredData> triggeredRecords) {
        for (Pusher delegate : delegates) {
            try {
                delegate.triggeredRecorded(triggeredRecords);
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
