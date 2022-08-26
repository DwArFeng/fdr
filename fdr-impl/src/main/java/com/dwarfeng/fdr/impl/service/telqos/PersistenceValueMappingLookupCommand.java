package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.bean.dto.MappingLookupSessionInfo;
import com.dwarfeng.fdr.stack.handler.PersistenceValueMappingLookupHandler;
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
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersistenceValueMappingLookupCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceValueMappingLookupCommand.class);

    private static final String COMMAND_OPTION_LIST = "l";
    private static final String COMMAND_OPTION_CLEANUP = "c";
    private static final String COMMAND_OPTION_LOOKUP_DETAIL = "ld";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LIST, COMMAND_OPTION_CLEANUP, COMMAND_OPTION_LOOKUP_DETAIL
    };

    private static final String IDENTITY = "pml";
    private static final String DESCRIPTION = "持久值映射查询操作";

    private static final String CMD_LINE_SYNTAX_LIST = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LIST);
    private static final String CMD_LINE_SYNTAX_CLEANUP = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEANUP);
    private static final String CMD_LINE_SYNTAX_LOOKUP_DETAIL = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP_DETAIL) + " session-id";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_LIST, CMD_LINE_SYNTAX_CLEANUP, CMD_LINE_SYNTAX_LOOKUP_DETAIL
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final PersistenceValueMappingLookupHandler handler;

    public PersistenceValueMappingLookupCommand(PersistenceValueMappingLookupHandler handler) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.handler = handler;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LIST).desc("查询会话信息").build());
        list.add(Option.builder(COMMAND_OPTION_CLEANUP).desc("清理会话信息").build());
        list.add(Option.builder(COMMAND_OPTION_LOOKUP_DETAIL).desc("查询会话详细信息").hasArg(true).type(Number.class).
                argName("session-id").build());
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
                case COMMAND_OPTION_LOOKUP_DETAIL:
                    handleLookupDetail(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleList(Context context) throws Exception {
        List<MappingLookupSessionInfo> sessionInfos = handler.getSessionInfos();
        CommandUtil.printSessionInfos(context, sessionInfos);
    }

    private void handleCleanup(Context context) throws Exception {
        handler.cleanup();
        context.sendMessage("清理完成!");
    }

    @SuppressWarnings("DuplicatedCode")
    private void handleLookupDetail(Context context, CommandLine cmd) throws Exception {
        long sessionId;
        try {
            sessionId = ((Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP_DETAIL)).longValue();
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_LOOKUP_DETAIL);
            context.sendMessage("请留意选项 " + COMMAND_OPTION_LOOKUP_DETAIL + " 后接参数的类型应该是数字 ");
            return;
        }

        MappingLookupSessionInfo sessionInfo = handler.getSessionInfo(new LongIdKey(sessionId));
        CommandUtil.printSessionInfoDetail(context, sessionInfo);
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
        if (cmd.hasOption(COMMAND_OPTION_LOOKUP_DETAIL)) {
            i++;
            subCmd = COMMAND_OPTION_LOOKUP_DETAIL;
        }
        return Pair.of(subCmd, i);
    }
}
