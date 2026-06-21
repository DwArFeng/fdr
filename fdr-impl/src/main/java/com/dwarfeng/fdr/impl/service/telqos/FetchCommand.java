package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.service.FetchQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * 抓取功能控制指令。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@TelqosCommand
public class FetchCommand extends CliCommand {

    private static final String COMMAND_OPTION_STATUS = "status";
    private static final String COMMAND_OPTION_START = "start";
    private static final String COMMAND_OPTION_STOP = "stop";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_STATUS,
            COMMAND_OPTION_START,
            COMMAND_OPTION_STOP
    };

    @SuppressWarnings({"SpellCheckingInspection", "RedundantSuppression"})
    private static final String IDENTITY = "fetch";
    private static final String DESCRIPTION = "抓取处理器操作/查看";

    private static final String CMD_LINE_SYNTAX_STATUS = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_STATUS);
    private static final String CMD_LINE_SYNTAX_START = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_START);
    private static final String CMD_LINE_SYNTAX_STOP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_STOP);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_STATUS,
            CMD_LINE_SYNTAX_START,
            CMD_LINE_SYNTAX_STOP
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final FetchQosService fetchQosService;

    public FetchCommand(FetchQosService fetchQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.fetchQosService = fetchQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_STATUS).desc("查看抓取处理器状态").build());
        list.add(Option.builder(COMMAND_OPTION_START).desc("启动抓取处理器").build());
        list.add(Option.builder(COMMAND_OPTION_STOP).desc("停止抓取处理器").build());
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
                case COMMAND_OPTION_STATUS:
                    context.sendMessage("抓取功能当前状态: " + (fetchQosService.isStarted() ? "运行中" : "已停止"));
                    break;
                case COMMAND_OPTION_START:
                    fetchQosService.start();
                    context.sendMessage("抓取功能已启动!");
                    break;
                case COMMAND_OPTION_STOP:
                    fetchQosService.stop();
                    context.sendMessage("抓取功能已停止!");
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }
}
