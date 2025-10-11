package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.service.SupportQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

@TelqosCommand
public class SupportCommand extends CliCommand {

    private static final String COMMAND_OPTION_RESET_FILTER = "reset-filter";
    private static final String COMMAND_OPTION_RESET_WASHER = "reset-washer";
    private static final String COMMAND_OPTION_RESET_TRIGGER = "reset-trigger";
    private static final String COMMAND_OPTION_RESET_MAPPER = "reset-mapper";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_RESET_FILTER,
            COMMAND_OPTION_RESET_WASHER,
            COMMAND_OPTION_RESET_TRIGGER,
            COMMAND_OPTION_RESET_MAPPER
    };

    private static final String IDENTITY = "support";
    private static final String DESCRIPTION = "支持操作";

    private static final String CMD_LINE_SYNTAX_RESET_FILTER = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_FILTER);
    private static final String CMD_LINE_SYNTAX_RESET_WASHER = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_WASHER);
    private static final String CMD_LINE_SYNTAX_RESET_TRIGGER = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_TRIGGER);
    private static final String CMD_LINE_SYNTAX_RESET_MAPPER = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_RESET_MAPPER);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_RESET_FILTER,
            CMD_LINE_SYNTAX_RESET_WASHER,
            CMD_LINE_SYNTAX_RESET_TRIGGER,
            CMD_LINE_SYNTAX_RESET_MAPPER
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final SupportQosService supportQosService;

    public SupportCommand(SupportQosService supportQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.supportQosService = supportQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_FILTER).desc("重置过滤器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_WASHER).desc("重置清洗器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_TRIGGER).desc("重置触发器支持").build());
        list.add(Option.builder().longOpt(COMMAND_OPTION_RESET_MAPPER).desc("重置映射器支持").build());
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
                case COMMAND_OPTION_RESET_FILTER:
                    supportQosService.resetFilter();
                    context.sendMessage("重置过滤器支持成功。");
                    break;
                case COMMAND_OPTION_RESET_WASHER:
                    supportQosService.resetWasher();
                    context.sendMessage("重置清洗器支持成功。");
                    break;
                case COMMAND_OPTION_RESET_TRIGGER:
                    supportQosService.resetTrigger();
                    context.sendMessage("重置触发器支持成功。");
                    break;
                case COMMAND_OPTION_RESET_MAPPER:
                    supportQosService.resetMapper();
                    context.sendMessage("重置映射器成功。");
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }
}
