package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.service.RecordQosService;
import com.dwarfeng.fdr.stack.service.RecordQosService.RecorderStatus;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ScheduledFuture;

@TelqosCommand
public class LogicConsumerCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogicConsumerCommand.class);

    private static final String COMMAND_OPTION_L = "l";
    private static final String COMMAND_OPTION_S = "s";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_L,
            COMMAND_OPTION_S
    };

    private static final String COMMAND_OPTION_H = "h";

    private static final String IDENTITY = "lcsu";
    private static final String DESCRIPTION = "逻辑侧消费者操作";
    private static final String CMD_LINE_SYNTAX_L = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_L) + " [" +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_H) + "]";
    private static final String CMD_LINE_SYNTAX_S = IDENTITY + " " +
            CommandUtil.concatOptionPrefix(COMMAND_OPTION_S) + " [-b val] [-t val]";

    private static final String[] CMD_LINE_ARRAY = new String[]{
            CMD_LINE_SYNTAX_L,
            CMD_LINE_SYNTAX_S
    };

    private static final String CMD_LINE_SYNTAX = CommandUtil.syntax(CMD_LINE_ARRAY);

    private final RecordQosService recordQosService;

    private final ThreadPoolTaskScheduler scheduler;

    public LogicConsumerCommand(
            RecordQosService recordQosService,
            ThreadPoolTaskScheduler scheduler
    ) {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
        this.recordQosService = recordQosService;
        this.scheduler = scheduler;
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_L).optionalArg(true).hasArg(false).desc("查看消费者状态").build());
        list.add(Option.builder(COMMAND_OPTION_H).desc("持续输出").build());
        list.add(Option.builder(COMMAND_OPTION_S).optionalArg(true).hasArg(false).desc("设置消费者参数").build());
        list.add(Option.builder("b").optionalArg(true).hasArg(true).type(Number.class)
                .argName("buffer-size").desc("缓冲器的大小").build());
        list.add(Option.builder("t").optionalArg(true).hasArg(true).type(Number.class)
                .argName("thread").desc("消费者的线程数量").build());
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
                case COMMAND_OPTION_L:
                    handleL(context, cmd);
                    break;
                case COMMAND_OPTION_S:
                    handleS(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleL(Context context, CommandLine cmd) throws Exception {
        // 如果命令行中包含 COMMAND_OPTION_H 选项，则持续输出。
        if (cmd.hasOption(COMMAND_OPTION_H)) {
            ScheduledFuture<?> future = scheduler.scheduleWithFixedDelay(
                    () -> {
                        try {
                            printConsumerStatus(context);
                        } catch (Exception e) {
                            LOGGER.warn("持续输出消费者状态时发生异常, 异常信息如下: ", e);
                        }
                    },
                    1000
            );
            // 等待用户输入任意字符，然后停止持续输出。
            context.sendMessage("输入任意字符停止持续输出");
            context.receiveMessage();
            future.cancel(true);
        } else {
            printConsumerStatus(context);
        }
    }

    private void handleS(Context context, CommandLine cmd) throws Exception {
        Integer newBufferSize = null;
        Integer newThread = null;
        try {
            if (cmd.hasOption("b")) newBufferSize = Integer.parseInt(cmd.getOptionValue("b"));
            if (cmd.hasOption("t")) newThread = Integer.parseInt(cmd.getOptionValue("t"));
        } catch (Exception e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + CMD_LINE_SYNTAX_S);
            context.sendMessage("请留意选项 b,t 后接参数的类型应该是数字 ");
            return;
        }

        RecorderStatus recorderStatus = recordQosService.getRecorderStatus();
        int bufferSize = Objects.nonNull(newBufferSize) ? newBufferSize : recorderStatus.getBufferSize();
        int thread = Objects.nonNull(newThread) ? newThread : recorderStatus.getThread();
        recordQosService.setRecorderParameters(bufferSize, thread);

        context.sendMessage("设置完成，记录者新的参数为: ");
        printConsumerStatus(context);
    }

    private void printConsumerStatus(Context context) throws ServiceException, TelqosException {
        RecorderStatus recorderStatus = recordQosService.getRecorderStatus();
        context.sendMessage(String.format("buffered-size:%-7d buffer-size:%-7d thread:%-3d idle:%b",
                recorderStatus.getBufferedSize(), recorderStatus.getBufferSize(), recorderStatus.getThread(),
                recorderStatus.isIdle())
        );
    }
}
