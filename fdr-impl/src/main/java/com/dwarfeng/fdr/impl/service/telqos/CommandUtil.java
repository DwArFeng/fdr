package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.bean.dto.MappingLookupSessionInfo;
import com.dwarfeng.springtelqos.stack.command.Context;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 指令工具类。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
final class CommandUtil {

    private static final int STRING_LENGTH_NULL = 4;

    /**
     * 拼接选项的前缀，用于生成选项说明书。
     *
     * <p>
     * online -> -online<br>
     * dump-file -> --dump-file。
     *
     * @param commandOption 指定的选项。
     * @return 拼接前缀之后的选项。
     */
    public static String concatOptionPrefix(@NotNull String commandOption) {
        if (commandOption.contains("-")) {
            return "--" + commandOption;
        }
        return "-" + commandOption;
    }

    public static String syntax(String... patterns) {
        StringJoiner sj = new StringJoiner(System.lineSeparator());
        for (String pattern : patterns) {
            sj.add(pattern);
        }
        return sj.toString();
    }

    public static String optionMismatchMessage(String... patterns) {
        StringJoiner sj = new StringJoiner(", --", "下列选项必须且只能含有一个: --", "");
        for (String pattern : patterns) {
            sj.add(pattern);
        }
        return sj.toString();
    }

    public static int maxStringLength(List<String> stringList, int offset) {
        int result = 0;
        for (String string : stringList) {
            int currentLength = Objects.isNull(string) ? STRING_LENGTH_NULL : string.length();
            result = Math.max(result, currentLength);
        }
        return result + offset;
    }

    public static String formatDate(Date date) {
        if (Objects.isNull(date)) {
            return "null";
        }
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

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
                    "no", "id", "mt", "point id", "created date", "cf", "ff"
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

    public static void printSessionInfoDetail(Context context, MappingLookupSessionInfo sessionInfo) throws Exception {
        if (Objects.isNull(sessionInfo)) {
            context.sendMessage("null!");
        } else {
            String format = "%-28s %s";
            context.sendMessage(String.format(format, "id:", sessionInfo.getKey().getLongId()));
            context.sendMessage(String.format(format, "mapper type:", sessionInfo.getMapperType()));
            context.sendMessage(String.format(format, "point id:", sessionInfo.getPointKey().getLongId()));
            context.sendMessage(String.format(format, "start date:", formatDate(sessionInfo.getStartDate())));
            context.sendMessage(String.format(format, "end date:", formatDate(sessionInfo.getEndDate())));
            context.sendMessage(String.format(format, "mapper args:", Arrays.toString(sessionInfo.getMapperArgs())));
            context.sendMessage(String.format(format, "created date:", formatDate(sessionInfo.getCreatedDate())));
            context.sendMessage(String.format(format, "canceled flag:", sessionInfo.isCanceledFlag()));
            context.sendMessage(String.format(format, "canceled date:", formatDate(sessionInfo.getCanceledDate())));
            context.sendMessage(String.format(format, "finished flag:", sessionInfo.isFinishedFlag()));
            context.sendMessage(String.format(format, "finished date:", formatDate(sessionInfo.getFinishedDate())));
            context.sendMessage(String.format(format, "fetched size:", sessionInfo.getFetchedSize()));
            context.sendMessage(String.format(
                    format, "current period start date:", formatDate(sessionInfo.getCurrentPeriodStartDate())
            ));
        }
    }

    private CommandUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
