package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.handler.Source;
import com.dwarfeng.fdr.stack.service.SourceQosService;
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
public class SourceCommand extends CliCommand {

    private static final String COMMAND_OPTION_LOOKUP = "l";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP
    };

    private static final String IDENTITY = "source";
    private static final String DESCRIPTION = "数据源查看";

    private static final String CMD_LINE_SYNTAX_LOOKUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LOOKUP
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final SourceQosService sourceQosService;

    public SourceCommand(SourceQosService sourceQosService) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.sourceQosService = sourceQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder().longOpt(COMMAND_OPTION_LOOKUP).desc("查看数据源").build());
        return list;
    }

    // 为了后续指令扩展，方法内的 switch 语句不做简化。
    @SuppressWarnings("SwitchStatementWithTooFewBranches")
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
                    printSources(context);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void printSources(Context context) throws Exception {
        List<Source> sources = sourceQosService.all();
        for (int i = 0; i < sources.size(); i++) {
            context.sendMessage(String.format("%02d. %s", i + 1, sources.get(i)));
        }
    }
}
