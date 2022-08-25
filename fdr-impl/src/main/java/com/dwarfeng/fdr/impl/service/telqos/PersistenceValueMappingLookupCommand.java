package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.bean.dto.MappingLookupSessionInfo;
import com.dwarfeng.fdr.stack.handler.PersistenceValueMappingLookupHandler;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PersistenceValueMappingLookupCommand extends CliCommand {

    private static final String COMMAND_OPTION_LIST = "l";
    private static final String COMMAND_OPTION_CLEANUP = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LIST,
            COMMAND_OPTION_CLEANUP
    };

    private static final String IDENTITY = "pml";
    private static final String DESCRIPTION = "持久值映射查询操作";

    private static final String CMD_LINE_SYNTAX_LIST = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LIST);
    private static final String CMD_LINE_SYNTAX_CLEANUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEANUP);

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LIST,
            CMD_LINE_SYNTAX_CLEANUP
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final PersistenceValueMappingLookupHandler handler;

    public PersistenceValueMappingLookupCommand(PersistenceValueMappingLookupHandler handler) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.handler = handler;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LIST).desc("查询会话信息").build());
        list.add(Option.builder(COMMAND_OPTION_CLEANUP).desc("清理会话信息").build());
        return list;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = analyseCommand(cmd);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case COMMAND_OPTION_LIST:
                    handleList(context);
                    break;
                case COMMAND_OPTION_CLEANUP:
                    handleCleanup(context);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleList(Context context) throws Exception {
        List<MappingLookupSessionInfo> sessionInfos = handler.getSessionInfos();
        if (sessionInfos.isEmpty()) {
            context.sendMessage("没有数据!");
        } else {
            int index = 0;
            int maxMapperTypeLength = CommandUtil.maxStringLength(
                    sessionInfos.stream().map(MappingLookupSessionInfo::getMapperType).collect(Collectors.toList()), 3
            );
            context.sendMessage(String.format(
                    "%1$-6s %2$-23s %3$-" + maxMapperTypeLength + "s %4$-22s %5$-22s %6$-22s %7$-7s %8$-22s %9$-7s " +
                            "%10$-22s",
                    "key", "mt", "pkey", "start date", "end date", "created date", "cf", "canceled date", "ff", "" +
                            "finished date"
            ));
            for (MappingLookupSessionInfo sessionInfo : sessionInfos) {
                context.sendMessage(String.format(
                        "%1$-6d %2$-23d %3$-" + maxMapperTypeLength + "s %4$-22s %5$-22s %6$-22s %7$-7b %8$-22s " +
                                "%9$-7b %10$-22s",
                        ++index, sessionInfo.getKey().getLongId(), sessionInfo.getMapperType(),
                        CommandUtil.formatDate(sessionInfo.getStartDate()),
                        CommandUtil.formatDate(sessionInfo.getEndDate()),
                        CommandUtil.formatDate(sessionInfo.getCreatedDate()),
                        sessionInfo.isCanceledFlag(), CommandUtil.formatDate(sessionInfo.getCanceledDate()),
                        sessionInfo.isFinishedFlag(), CommandUtil.formatDate(sessionInfo.getFinishedDate())
                ));
            }
        }
    }

    private void handleCleanup(Context context) throws Exception {
        handler.cleanup();
        context.sendMessage("清理完成!");
    }

    @SuppressWarnings("DuplicatedCode")
    private Pair<String, Integer> analyseCommand(CommandLine cmd) {
        int i = 0;
        String subCmd = null;
        if (cmd.hasOption(COMMAND_OPTION_LIST)) {
            i++;
            subCmd = COMMAND_OPTION_LIST;
        }
        if (cmd.hasOption(COMMAND_OPTION_CLEANUP)) {
            i++;
            subCmd = COMMAND_OPTION_CLEANUP;
        }
        return Pair.of(subCmd, i);
    }
}
