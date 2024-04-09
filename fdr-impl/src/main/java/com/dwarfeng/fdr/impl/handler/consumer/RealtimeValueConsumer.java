package com.dwarfeng.fdr.impl.handler.consumer;

import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.fdr.impl.handler.Consumer;
import com.dwarfeng.fdr.stack.bean.entity.RealtimeValue;
import com.dwarfeng.fdr.stack.service.RealtimeValueMaintainService;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RealtimeValueConsumer implements Consumer<RealtimeValue> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RealtimeValueConsumer.class);

    @Autowired
    private RealtimeValueMaintainService realtimeValueMaintainService;

    @SuppressWarnings("DuplicatedCode")
    @Override
    public void consume(List<RealtimeValue> realtimeValues) {
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        try {
            Map<LongIdKey, RealtimeValue> realtimeValueMap = new HashMap<>();
            try {
                for (RealtimeValue realtimeValue : realtimeValues) {
                    if (realtimeValueMap.containsKey(realtimeValue.getKey())) {
                        int compareResult = realtimeValue.getHappenedDate()
                                .compareTo(realtimeValueMap.get(realtimeValue.getKey()).getHappenedDate());
                        if (compareResult > 0) {
                            realtimeValueMap.put(realtimeValue.getKey(), realtimeValue);
                        }
                    } else {
                        realtimeValueMap.put(realtimeValue.getKey(), realtimeValue);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("处理数据时发生异常, 最多 " + realtimeValues.size() + " 个数据信息丢失", e);
                realtimeValues.forEach(realtimeValue -> LOGGER.debug(realtimeValue + ""));
                return;
            }

            List<RealtimeValue> failedList = new ArrayList<>();

            for (RealtimeValue realtimeValue : realtimeValueMap.values()) {
                try {
                    RealtimeValue ifExists = realtimeValueMaintainService.getIfExists(realtimeValue.getKey());
                    if (Objects.isNull(ifExists)) {
                        realtimeValueMaintainService.insert(realtimeValue);
                    } else {
                        int compareResult = realtimeValue.getHappenedDate().compareTo(ifExists.getHappenedDate());
                        if (compareResult > 0) {
                            realtimeValueMaintainService.update(realtimeValue);
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("数据插入失败, 放弃对数据的推送: " + realtimeValue, e);
                    failedList.add(realtimeValue);
                }
            }

            if (!failedList.isEmpty()) {
                LOGGER.error("记录数据时发生异常, 最多 " + failedList.size() + " 个数据信息丢失");
                failedList.forEach(realtimeValue -> LOGGER.debug(realtimeValue + ""));
            }
        } finally {
            tm.stop();
            LOGGER.debug("消费者处理了 " + realtimeValues.size() + " 条数据, 共用时 " + tm.getTimeMs() + " 毫秒");
        }
    }
}
