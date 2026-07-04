package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.Washer;
import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.fdr.stack.struct.RecordLocalCache;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 数据记录本地缓存操作指令。
 *
 * <p>
 * 该指令用于查看和清理记录功能的本地缓存，包括查看指定数据点的详细信息以及清除整个本地缓存等操作。
 *
 * @author DwArFeng
 * @since 1.8.0
 */
@TelqosCommand
public class RecordLocalCacheCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordLocalCacheCommand.class);

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "rlc";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    // endregion

    private final RecordQosService recordQosService;

    public RecordLocalCacheCommand(RecordQosService recordQosService) {
        super(IDENTITY);
        this.recordQosService = recordQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "数据记录本地缓存操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " point-id",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(true).type(Number.class)
                .argName("point-id").desc("查看指定数据点的详细信息，如果本地缓存中不存在，则尝试抓取").build());
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除缓存").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Pair<String, Integer> pair = CliCommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
        if (pair.getRight() != 1) {
            context.sendMessage(CliCommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
            context.sendMessage(context.getCommandManual(context.getRuntimeIdentity()));
            return;
        }
        switch (pair.getLeft()) {
            case COMMAND_OPTION_LOOKUP:
                handleLookup(context, cmd);
                break;
            case COMMAND_OPTION_CLEAR:
                handleClear(context);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        long pointId;
        try {
            pointId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP)).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                    CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " point-id");
            context.sendMessage("请留意选项 p 后接参数的类型应该是数字 ");
            return;
        }
        RecordLocalCache recordLocalCache = recordQosService.getRecordLocalCache(new LongIdKey(pointId));
        if (Objects.isNull(recordLocalCache)) {
            context.sendMessage("not exists!");
            return;
        }
        context.sendMessage(String.format("point: %s", recordLocalCache.getPoint().toString()));
        context.sendMessage("pre filter washers:");
        int index = 0;
        for (Map.Entry<LongIdKey, Washer> entry : recordLocalCache.getPreFilterWasherMap().entrySet()) {
            LongIdKey key = entry.getKey();
            Washer value = entry.getValue();
            context.sendMessage(String.format("  %-3d key:%s value:%s", ++index, key, value));
        }
        context.sendMessage("filters:");
        index = 0;
        for (Map.Entry<LongIdKey, Filter> entry : recordLocalCache.getFilterMap().entrySet()) {
            LongIdKey key = entry.getKey();
            Filter value = entry.getValue();
            context.sendMessage(String.format("  %-3d key:%s value:%s", ++index, key, value));
        }
        context.sendMessage("post filter washers:");
        index = 0;
        for (Map.Entry<LongIdKey, Washer> entry : recordLocalCache.getPostFilterWasherMap().entrySet()) {
            LongIdKey key = entry.getKey();
            Washer value = entry.getValue();
            context.sendMessage(String.format("  %-3d key:%s value:%s", ++index, key, value));
        }
    }

    private void handleClear(CommandExecutor.Context context) throws Exception {
        recordQosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }
}
