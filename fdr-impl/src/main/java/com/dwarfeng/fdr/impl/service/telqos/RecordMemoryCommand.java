package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.service.RecordMemoryQosService;
import com.dwarfeng.fdr.stack.struct.RecordMemory;
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
import java.util.Objects;

/**
 * 记录记忆查询与清理指令。
 *
 * <p>
 * 该指令用于查询和管理记录记忆数据，包括查看指定点位的记录记忆、移除指定点位的记录记忆以及清除所有记录记忆等操作。
 *
 * @author DwArFeng
 * @since 2.5.0
 */
@TelqosCommand
public class RecordMemoryCommand extends CliCommand {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordMemoryCommand.class);

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "rmem";

    // region 指令选项

    private static final String COMMAND_OPTION_LOOKUP = "l";
    private static final String COMMAND_OPTION_REMOVE = "r";
    private static final String COMMAND_OPTION_CLEAR = "c";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_REMOVE,
            COMMAND_OPTION_CLEAR
    };

    // endregion

    private final RecordMemoryQosService recordMemoryQosService;

    public RecordMemoryCommand(RecordMemoryQosService recordMemoryQosService) {
        super(IDENTITY);
        this.recordMemoryQosService = recordMemoryQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> "记录记忆查询与清理";
    }

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " point-id",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_REMOVE) + " point-id",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR)
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(
                Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(true).type(Number.class)
                        .argName("point-id").desc("查看指定点位的记录记忆").build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_REMOVE).optionalArg(true).hasArg(true).type(Number.class)
                        .argName("point-id").desc("移除指定的点位对应的记录记忆").build()
        );
        list.add(Option.builder(COMMAND_OPTION_CLEAR).optionalArg(true).hasArg(false).desc("清除记录记忆").build());
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
            case COMMAND_OPTION_REMOVE:
                handleRemove(context, cmd);
                break;
            case COMMAND_OPTION_CLEAR:
                handleClear(context);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    /**
     * 处理查询命令。
     */
    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Number pointIdNumber;
        try {
            pointIdNumber = (Number) cmd.getParsedOptionValue(COMMAND_OPTION_LOOKUP);
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                    CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " point-id");
            context.sendMessage("请留意选项 l 后接参数的类型应该是数字");
            return;
        }
        if (pointIdNumber == null) {
            context.sendMessage("命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                    CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " point-id");
            context.sendMessage("请留意选项 l 后接参数的类型应该是数字");
            return;
        }

        long pointId = pointIdNumber.longValue();
        List<RecordMemory> recordMemories = recordMemoryQosService.lookup(new LongIdKey(pointId));
        if (recordMemories.isEmpty()) {
            context.sendMessage("记录记忆为空");
            return;
        }
        while (true) {
            CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                    context, recordMemories, "记录记忆总数: " + recordMemories.size(),
                    command -> "输入 q 退出查询"
            );
            if (cropResult.isExitFlag()) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                RecordMemory recordMemory = recordMemories.get(i);
                printRecordMemory(i, cropResult.getEndIndex(), recordMemory, context);
            }
        }
    }

    /**
     * 打印单条记录记忆数据。
     */
    private void printRecordMemory(
            int i, int endIndex, RecordMemory recordMemory, CommandExecutor.Context context
    ) throws Exception {
        context.sendMessage(String.format(
                "索引: %d/%d",
                i, endIndex
        ));
        if (Objects.isNull(recordMemory)) {
            context.sendMessage("  null");
            context.sendMessage("");
            return;
        }

        context.sendMessage(String.format(
                "  pointId: %s", Objects.isNull(recordMemory.getPointKey()) ?
                        "null" : recordMemory.getPointKey().getLongId()
        ));
        context.sendMessage(String.format(
                "  happenedDate: %s", Objects.isNull(recordMemory.getHappenedDate()) ?
                        "null" :
                        String.format("%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL", recordMemory.getHappenedDate())
        ));
        context.sendMessage(String.format(
                "  happenedDateNanoOffset: %d",
                recordMemory.getHappenedDateNanoOffset()
        ));
        context.sendMessage(String.format(
                "  rawValueClass: %s", Objects.isNull(recordMemory.getRawValue()) ?
                        "null" : recordMemory.getRawValue().getClass().getCanonicalName()
        ));
        context.sendMessage(String.format("  rawValue: %s", recordMemory.getRawValue()));
        context.sendMessage(String.format("  passed: %s", recordMemory.isPassed()));
        context.sendMessage(String.format(
                "  valueClass: %s", Objects.isNull(recordMemory.getValue()) ?
                        "null" : recordMemory.getValue().getClass().getCanonicalName()
        ));
        context.sendMessage(String.format("  value: %s", recordMemory.getValue()));
        context.sendMessage("");
    }

    /**
     * 处理按点位清理命令。
     */
    private void handleRemove(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        Number pointIdNumber;
        try {
            pointIdNumber = (Number) cmd.getParsedOptionValue(COMMAND_OPTION_REMOVE);
        } catch (ParseException e) {
            LOGGER.warn("解析命令选项时发生异常，异常信息如下", e);
            context.sendMessage("命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                    CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR));
            context.sendMessage("请留意选项 c 后接参数的类型应该是数字");
            return;
        }
        if (pointIdNumber == null) {
            context.sendMessage("命令行格式错误，正确的格式为: " + context.getRuntimeIdentity() + " " +
                    CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_CLEAR));
            context.sendMessage("请留意选项 c 后接参数的类型应该是数字");
            return;
        }
        long pointId = pointIdNumber.longValue();
        recordMemoryQosService.remove(new LongIdKey(pointId));
        context.sendMessage("指定的点位对应的记录记忆已移除");
    }

    /**
     * 处理全量清理命令。
     */
    private void handleClear(CommandExecutor.Context context) throws Exception {
        recordMemoryQosService.clear();
        context.sendMessage("记录记忆已清除");
    }
}
