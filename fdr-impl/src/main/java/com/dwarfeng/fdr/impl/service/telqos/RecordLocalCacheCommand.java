package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.Washer;
import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.fdr.stack.struct.RecordLocalCache;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
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

@TelqosCommand
public class RecordLocalCacheCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordLocalCacheCommand.class);

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLEAR
    };

    private static final String IDENTITY = "rlc";
    private static final String DESCRIPTION = "数据记录本地缓存操作";
    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " point-id";
    private static final String CMD_LINE_SYNTAX_CLEAR = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP,
            CMD_LINE_SYNTAX_CLEAR
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final RecordQosService recordQosService;

    public RecordLocalCacheCommand(RecordQosService recordQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.recordQosService = recordQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(true).type(Number.class)
                .argName("point-id").desc("查看指定数据点的详细信息，如果本地缓存中不存在，则尝试抓取").build());
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除缓存").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_LOOKUP:
                    handleLookup(context, cmd);
                    break;
                case COMMAND_OPTION_CLEAR:
                    handleClear(context);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleLookup(Context context, CommandLine cmd) throws Exception {
        long pointId;
        try {
            pointId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP)).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_LOOKUP);
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

    private void handleClear(Context context) throws Exception {
        recordQosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }
}
