package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.bean.dto.MappingLookupSessionInfo;
import com.dwarfeng.springtelqos.stack.command.Context;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 映射查询工具类。
 *
 * @author DwArFeng
 * @since 1.10.1
 */
final class MappingCommandUtil {

    public static void printSessionInfos(
            Context context, List<MappingLookupSessionInfo> sessionInfos
    ) throws Exception {
        if (sessionInfos.isEmpty()) {
            context.sendMessage("没有数据!");
        } else {
            int index = 0;
            int maxMapperTypeLength = CommandUtil.maxStringLength(
                    sessionInfos.stream().map(MappingLookupSessionInfo::getMapperType).collect(Collectors.toList()), 3
            );
            context.sendMessage(String.format(
                    "%1$-6s %2$-23s %3$-" + maxMapperTypeLength + "s %4$-23s %5$-22s %6$-7s %7$-7s",
                    "no", "key", "mt", "point key", "created date", "cf", "ff"
            ));
            for (MappingLookupSessionInfo sessionInfo : sessionInfos) {
                context.sendMessage(String.format(
                        "%1$-6d %2$-23d %3$-" + maxMapperTypeLength + "s %4$-23d %5$-22s %6$-7b %7$-7b",
                        ++index, sessionInfo.getKey().getLongId(), sessionInfo.getMapperType(),
                        sessionInfo.getPointKey().getLongId(), CommandUtil.formatDate(sessionInfo.getCreatedDate()),
                        sessionInfo.isCanceledFlag(), sessionInfo.isFinishedFlag()
                ));
            }
        }
    }

    private MappingCommandUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
