package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.service.SupportQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持操作指令。
 *
 * <p>
 * 该指令用于重置各种支持类，包括过滤器支持、清洗器支持、触发器支持和映射器支持。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
@TelqosCommand
public class SupportCommand extends CliCommand {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "support";

    // region 指令选项

    private static final String COMMAND_OPTION_RESET_FILTER = "reset-filter";
    private static final String COMMAND_OPTION_RESET_WASHER = "reset-washer";
    private static final String COMMAND_OPTION_RESET_TRIGGER = "reset-trigger";
    private static final String COMMAND_OPTION_RESET_MAPPER = "reset-mapper";
    private static final String COMMAND_OPTION_RESET_FETCHER = "reset-fetcher";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_RESET_FILTER,
            COMMAND_OPTION_RESET_WASHER,
            COMMAND_OPTION_RESET_TRIGGER,
            COMMAND_OPTION_RESET_MAPPER,
            COMMAND_OPTION_RESET_FETCHER
    };

    // endregion

    private final SupportQosService supportQosService;

    public SupportCommand(SupportQosService supportQosService) {
        super(IDENTITY);
        this.supportQosService = supportQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "支持操作";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_FILTER),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_WASHER),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_TRIGGER),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_MAPPER),
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_FETCHER)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_FILTER).optionalArg(true).hasArg(false)
                .desc("重置过滤器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_WASHER).optionalArg(true).hasArg(false)
                .desc("重置清洗器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_TRIGGER).optionalArg(true).hasArg(false)
                .desc("重置触发器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_MAPPER).optionalArg(true).hasArg(false)
                .desc("重置映射器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_FETCHER).optionalArg(true).hasArg(false)
                .desc("重置抓取器支持").build());
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
            case COMMAND_OPTION_RESET_FILTER:
                supportQosService.resetFilter();
                context.sendMessage("重置过滤器支持成功");
                break;
            case COMMAND_OPTION_RESET_WASHER:
                supportQosService.resetWasher();
                context.sendMessage("重置清洗器支持成功");
                break;
            case COMMAND_OPTION_RESET_TRIGGER:
                supportQosService.resetTrigger();
                context.sendMessage("重置触发器支持成功");
                break;
            case COMMAND_OPTION_RESET_MAPPER:
                supportQosService.resetMapper();
                context.sendMessage("重置映射器支持成功");
                break;
            case COMMAND_OPTION_RESET_FETCHER:
                supportQosService.resetFetcher();
                context.sendMessage("重置抓取器支持成功");
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }
}
