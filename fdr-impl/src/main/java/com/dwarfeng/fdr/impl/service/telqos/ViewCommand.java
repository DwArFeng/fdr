package com.dwarfeng.fdr.impl.service.telqos;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.dutil.basic.io.IOUtil;
import com.dwarfeng.dutil.basic.io.StringOutputStream;
import com.dwarfeng.dutil.basic.mea.TimeMeasurer;
import com.dwarfeng.fdr.sdk.bean.dto.WebInputLookupInfo;
import com.dwarfeng.fdr.sdk.bean.dto.WebInputNativeQueryInfo;
import com.dwarfeng.fdr.sdk.bean.dto.WebInputQueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.*;
import com.dwarfeng.fdr.stack.service.ViewQosService;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.sdk.util.CliCommandUtil;
import com.dwarfeng.springtelqos.stack.command.CommandDescriptor;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 观察指令基类。
 *
 * <p>
 * 该抽象类为各种数据查询指令提供基础实现，包括查询最新数据、按 ID 查询、原生查询和通用查询等功能。
 * 支持通过 JSON 参数进行灵活的查询配置。
 *
 * @param <D> 数据类型，必须是 {@link Data} 的子类
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class ViewCommand<D extends Data> extends CliCommand {

    // region 指令选项

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_OPTION_LATEST = "latest";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_OPTION_LOOKUP = "lookup";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_OPTION_NATIVE_QUERY = "nquery";
    private static final String COMMAND_OPTION_NATIVE_QUERY_LONG_OPT = "native-query";
    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String COMMAND_OPTION_QUERY = "query";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LATEST,
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_NATIVE_QUERY,
            COMMAND_OPTION_QUERY,
    };

    private static final String COMMAND_SUB_OPTION_JSON = "json";
    private static final String COMMAND_SUB_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT = "json-file";

    // endregion

    protected final ViewQosService<D> viewQosService;

    public ViewCommand(String identity, ViewQosService<D> viewQosService) {
        super(identity);
        this.viewQosService = viewQosService;
    }

    @Override
    protected DescriptionProvider provideDescriptionProvider() {
        return context -> provideCommandDescription();
    }

    /**
     * 提供指令描述。
     *
     * @return 指令描述。
     */
    protected abstract String provideCommandDescription();

    @Override
    protected CliSyntaxProvider provideCliSyntaxProvider() {
        return this::cliSyntaxProvider;
    }

    private String cliSyntaxProvider(CommandDescriptor.Context context) throws Exception {
        String identity = context.getRuntimeIdentity();
        String[] patterns = new String[]{
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LATEST) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_NATIVE_QUERY) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]",
                identity + " " + CliCommandUtil.concatOptionPrefix(COMMAND_OPTION_QUERY) + " [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON) + " json-string] [" +
                        CliCommandUtil.concatOptionPrefix(COMMAND_SUB_OPTION_JSON_FILE) + " json-file]"
        };
        return CliCommandUtil.cliSyntax(patterns);
    }

    @Override
    protected List<Option> provideOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LATEST).optionalArg(true).hasArg(false).desc("最新数据指令").build());
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).optionalArg(true).hasArg(false).desc("查看指令").build());
        list.add(
                Option.builder(COMMAND_OPTION_NATIVE_QUERY).longOpt(COMMAND_OPTION_NATIVE_QUERY_LONG_OPT)
                        .optionalArg(true).hasArg(false).desc("原生查询指令").build()
        );
        list.add(Option.builder(COMMAND_OPTION_QUERY).optionalArg(true).hasArg(false).desc("查询指令").build());
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON).hasArg(true).type(String.class).desc("JSON 字符串").build()
        );
        list.add(
                Option.builder(COMMAND_SUB_OPTION_JSON_FILE).longOpt(COMMAND_SUB_OPTION_JSON_FILE_LONG_OPT)
                        .hasArg(true).type(File.class).desc("JSON 文件").build()
        );
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
            case COMMAND_OPTION_LATEST:
                handleLatest(context, cmd);
                break;
            case COMMAND_OPTION_LOOKUP:
                handleLookup(context, cmd);
                break;
            case COMMAND_OPTION_NATIVE_QUERY:
                handleNativeQuery(context, cmd);
                break;
            case COMMAND_OPTION_QUERY:
                handleQuery(context, cmd);
                break;
            default:
                throw new IllegalStateException("不应该执行到此处, 请联系开发人员");
        }
    }

    private void handleLatest(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        List<LongIdKey> pointKeys;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 pointKeys。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            pointKeys = JSON.parseArray(json, WebInputLongIdKey.class).stream().map(WebInputLongIdKey::toStackBean)
                    .collect(Collectors.toList());
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 pointKeys。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (
                    FileInputStream in = new FileInputStream(jsonFile);
                    StringOutputStream out = new StringOutputStream()
            ) {
                IOUtil.trans(in, out, 4096);
                out.flush();
                String json = out.toString();
                pointKeys = JSON.parseArray(json, WebInputLongIdKey.class).stream().map(WebInputLongIdKey::toStackBean)
                        .collect(Collectors.toList());
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        List<D> datas = viewQosService.latest(pointKeys);
        tm.stop();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        // 输出数据。
        while (true) {
            CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                    context, datas, "数据总数: " + datas.size(), command -> "输入 q 退出查询"
            );
            if (cropResult.isExitFlag()) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                D data = datas.get(i);
                printLatestData(i, cropResult.getEndIndex(), data, context);
            }
        }
    }

    protected abstract void printLatestData(
            int i, int endIndex, D data, CommandExecutor.Context context
    ) throws Exception;

    private void handleLookup(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        LookupInfo lookupInfo;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 lookupInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            lookupInfo = WebInputLookupInfo.toStackBean(JSON.parseObject(json, WebInputLookupInfo.class));
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 lookupInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                lookupInfo = WebInputLookupInfo.toStackBean(JSON.parseObject(in, WebInputLookupInfo.class));
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        LookupResult<D> lookupResult = viewQosService.query(lookupInfo);
        tm.stop();
        List<D> datas = lookupResult.getDatas();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        // 输出数据。
        while (true) {
            CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                    context, datas, "数据总数: " + datas.size(), command -> "输入 q 退出查询"
            );
            if (cropResult.isExitFlag()) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                D data = datas.get(i);
                printLookupData(i, cropResult.getEndIndex(), data, context);
            }
        }
    }

    protected abstract void printLookupData(
            int i, int endIndex, D data, CommandExecutor.Context context
    ) throws Exception;

    private void handleQuery(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        QueryInfo queryInfo;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 queryInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            queryInfo = WebInputQueryInfo.toStackBean(JSON.parseObject(json, WebInputQueryInfo.class));
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 queryInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                queryInfo = WebInputQueryInfo.toStackBean(JSON.parseObject(in, WebInputQueryInfo.class));
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        QueryResult queryResult = viewQosService.lookup(queryInfo);
        tm.stop();
        List<QueryResult.Sequence> sequences = queryResult.getSequences();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        processQueryResultSequence(context, sequences);
    }

    private void handleNativeQuery(CommandExecutor.Context context, CommandLine cmd) throws Exception {
        NativeQueryInfo nativeQueryInfo;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 queryInfo。
        if (cmd.hasOption(COMMAND_SUB_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON);
            nativeQueryInfo = WebInputNativeQueryInfo.toStackBean(
                    JSON.parseObject(json, WebInputNativeQueryInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 queryInfo。
        else if (cmd.hasOption(COMMAND_SUB_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_SUB_OPTION_JSON_FILE);
            try (FileInputStream in = new FileInputStream(jsonFile)) {
                nativeQueryInfo = WebInputNativeQueryInfo.toStackBean(
                        JSON.parseObject(in, WebInputNativeQueryInfo.class)
                );
            }
        } else {
            // 暂时未实现。
            throw new UnsupportedOperationException("not supported yet");
        }

        // 查询数据，并计时。
        TimeMeasurer tm = new TimeMeasurer();
        tm.start();
        QueryResult queryResult = viewQosService.nativeQuery(nativeQueryInfo);
        tm.stop();
        List<QueryResult.Sequence> sequences = queryResult.getSequences();

        // 输出执行时间。
        context.sendMessage("");
        context.sendMessage("执行时间：" + tm.getTimeMs() + "ms");
        context.sendMessage("");

        // 输出数据。
        processQueryResultSequence(context, sequences);
    }

    private void processQueryResultSequence(
            CommandExecutor.Context context, List<QueryResult.Sequence> sequences
    ) throws Exception {
        // 输出数据。
        int sequenceIndex;
        while (true) {
            context.sendMessage("序列总数: " + sequences.size());
            context.sendMessage("");
            context.sendMessage("输入序列索引");
            context.sendMessage("输入 q 退出查询");
            context.sendMessage("");

            String message = context.receiveMessage();

            if (message.equalsIgnoreCase("q")) {
                break;
            } else {
                try {
                    sequenceIndex = Integer.parseInt(message);
                } catch (NumberFormatException e) {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                    continue;
                }
                if (sequenceIndex < 0 || sequenceIndex >= sequences.size()) {
                    context.sendMessage("输入范围错误");
                    context.sendMessage("");
                    continue;
                }
            }

            QueryResult.Sequence sequence = sequences.get(sequenceIndex);

            context.sendMessage("");
            context.sendMessage("序列信息: ");
            String sequenceFormat = "pointId: %1$s    startDate: %2$tY-%2$tm-%2$td %2$tH:%2$tM:%2$tS.%2$tL    " +
                    "startDateNanoOffset: %4$d    endDate: %3$tY-%3$tm-%3$td %3$tH:%3$tM:%3$tS.%3$tL    " +
                    "endDateNanoOffset: %5$d";
            context.sendMessage(String.format(
                    sequenceFormat, sequence.getPointKey().getLongId(),
                    sequence.getStartDate(),
                    sequence.getEndDate(),
                    sequence.getStartDateNanoOffset(),
                    sequence.getEndDateNanoOffset()
            ));

            List<QueryResult.Item> items = sequence.getItems();

            while (true) {
                CliCommandUtil.CropResult cropResult = CliCommandUtil.cropData(
                        context, items, "数据总数: " + items.size(), command -> "输入 q 返回至序列选择"
                );
                if (cropResult.isExitFlag()) {
                    break;
                }
                context.sendMessage("");
                for (int i = cropResult.getBeginIndex(); i < cropResult.getEndIndex(); i++) {
                    QueryResult.Item item = items.get(i);
                    printQueryData(i, cropResult.getEndIndex(), item, context);
                }
            }
        }
    }

    protected abstract void printQueryData(
            int i, int endIndex, QueryResult.Item item, CommandExecutor.Context context
    ) throws Exception;
}
