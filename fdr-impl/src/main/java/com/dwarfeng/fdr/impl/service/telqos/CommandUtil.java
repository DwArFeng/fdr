package com.dwarfeng.fdr.impl.service.telqos;

import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

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

    private CommandUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
