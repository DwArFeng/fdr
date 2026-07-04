package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.bean.entity.FetcherInfo;
import com.dwarfeng.fdr.stack.handler.Fetcher;
import com.dwarfeng.fdr.stack.handler.FetcherSession;
import com.dwarfeng.fdr.stack.service.FetcherSessionQosService;
import com.dwarfeng.fdr.stack.service.FetcherSessionQosService.FetcherSessionDescription;
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

/**
 * 抓取器会话操作指令。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@TelqosCommand
public class FetcherSessionCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(FetcherSessionCommand.class);

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "fs";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_CLOSE_AND_CLEAR = "cnc";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_CLOSE_AND_CLEAR
    };

    // endregion

    private final FetcherSessionQosService fetcherSessionQosService;

    public FetcherSessionCommand(FetcherSessionQosService fetcherSessionQosService) {
        super(IDENTITY);
        this.fetcherSessionQosService = fetcherSessionQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "抓取会话操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " fetcher-info-id",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLOSE_AND_CLEAR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(true).type(Number.class)
                .desc("查询抓取会话").build());
        list.add(Option.builder(COMMAND_OPTION_CLOSE_AND_CLEAR).optionalArg(true).hasArg(false)
                .desc("关闭并清除抓取会话").build());
        return list;
    }

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
            case COMMAND_OPTION_CLOSE_AND_CLEAR:
                fetcherSessionQosService.closeAndClearHolding();
                context.sendMessage("本地缓存已清除");
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        long fetcherId;
        try {
            fetcherId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP)).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                    CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " fetcher-info-id");
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
