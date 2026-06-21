package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import com.dwarfeng.fdr.stack.handler.FetcherSession;
import com.dwarfeng.fdr.stack.service.FetcherSessionQosService;
import com.dwarfeng.fdr.stack.service.FetcherSessionQosService.FetcherSessionDescription;
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

/**
 * 抓取器会话操作指令。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@TelqosCommand
public class FetcherSessionCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetcherSessionCommand.class);

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLOSE_AND_CLEAR = "cnc";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLOSE_AND_CLEAR
    };

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    private static final String IDENTITY = "fs";
    private static final String DESCRIPTION = "抓取会话操作";

    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " fetcher-info-id";
    private static final String CMD_LINE_SYNTAX_CLOSE_AND_CLEAR = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CLOSE_AND_CLEAR);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP,
            CMD_LINE_SYNTAX_CLOSE_AND_CLEAR
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final FetcherSessionQosService fetcherSessionQosService;

    public FetcherSessionCommand(FetcherSessionQosService fetcherSessionQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.fetcherSessionQosService = fetcherSessionQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).desc("查询抓取会话").hasArg().type(Number.class).build());
        list.add(Option.builder(COMMAND_OPTION_CLOSE_AND_CLEAR).desc("关闭并清除抓取会话").build());
        return list;
    }

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
                case COMMAND_OPTION_CLOSE_AND_CLEAR:
                    fetcherSessionQosService.closeAndClearHolding();
                    context.sendMessage("本地缓存已清除");
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLookup(Context context, CommandLine cmd) throws Exception {
        long fetcherId;
        try {
            fetcherId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP)).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_LOOKUP);
            context.sendMessage("请留意选项 " + COMMAND_OPTION_LOOKUP + " 后接参数的类型应该是数字 ");
            return;
        }
        LongIdKey fetcherInfoKey = new LongIdKey(fetcherId);
        if (!fetcherSessionQosService.exists(fetcherInfoKey)) {
            context.sendMessage("not exists!");
            return;
        }
        FetcherSessionDescription description = fetcherSessionQosService.get(fetcherInfoKey);
        FetcherInfo fetcherInfo = description.getFetcherInfo();
        Fetcher fetcher = description.getFetcher();
        FetcherSession fetcherSession = description.getFetcherSession();
        context.sendMessage(String.format("fetcherInfo: %s", fetcherInfo));
        context.sendMessage(String.format("fetcher: %s", fetcher));
        context.sendMessage(String.format("fetcherSession: %s", fetcherSession));
    }
}
