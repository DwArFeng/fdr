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
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
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
 * 观察指令。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class ViewCommand<D extends Data> extends CliCommand {

    private static final String COMMAND_OPTION_LATEST = "latest";
    private static final String COMMAND_OPTION_LOOKUP = "lookup";
    private static final String COMMAND_OPTION_NATIVE_QUERY = "nquery";
    private static final String COMMAND_OPTION_NATIVE_QUERY_LONG_OPT = "native-query";
    private static final String COMMAND_OPTION_QUERY = "query";

    private static final String[] COMMAND_OPTION_ARRAY = new String[]{
            COMMAND_OPTION_LATEST,
            COMMAND_OPTION_LOOKUP,
            COMMAND_OPTION_NATIVE_QUERY,
            COMMAND_OPTION_QUERY,
    };

    private static final String COMMAND_OPTION_JSON = "json";
    private static final String COMMAND_OPTION_JSON_FILE = "jf";
    private static final String COMMAND_OPTION_JSON_FILE_LONG_OPT = "json-file";

    @SuppressWarnings("ExtractMethodRecommender")
    private static String cmdLineSyntax(String identity) {
        final String cmdLineSyntaxLatest = identity + " " +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_LATEST) + " [" +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
        final String cmdLineSyntaxLookup = identity + " " +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_LOOKUP) + " [" +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
        final String cmdLineSyntaxNativeQuery = identity + " " +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_NATIVE_QUERY) + " [" +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";
        final String cmdLineSyntaxQuery = identity + " " +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_QUERY) + " [" +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON) + " json-string] [" +
                CommandUtil.concatOptionPrefix(COMMAND_OPTION_JSON_FILE) + " json-file]";

        final String[] cmdLineArray = new String[]{
                cmdLineSyntaxLatest,
                cmdLineSyntaxLookup,
                cmdLineSyntaxNativeQuery,
                cmdLineSyntaxQuery
        };

        return CommandUtil.syntax(cmdLineArray);
    }

    protected final ViewQosService<D> viewQosService;

    public ViewCommand(
            String identity, String description, ViewQosService<D> viewQosService
    ) {
        super(identity, description, cmdLineSyntax(identity));
        this.viewQosService = viewQosService;
    }

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder(COMMAND_OPTION_LATEST).desc("最新数据指令").build());
        list.add(Option.builder(COMMAND_OPTION_LOOKUP).desc("查看指令").build());
        list.add(
                Option.builder(COMMAND_OPTION_NATIVE_QUERY).longOpt(COMMAND_OPTION_NATIVE_QUERY_LONG_OPT)
                        .desc("原生查询指令").build()
        );
        list.add(Option.builder(COMMAND_OPTION_QUERY).desc("查询指令").build());
        list.add(
                Option.builder(COMMAND_OPTION_JSON).desc("JSON字符串").hasArg().type(String.class).build()
        );
        list.add(
                Option.builder(COMMAND_OPTION_JSON_FILE).longOpt(COMMAND_OPTION_JSON_FILE_LONG_OPT).desc("JSON文件")
                        .hasArg().type(File.class).build()
        );
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = CommandUtil.analyseCommand(cmd, COMMAND_OPTION_ARRAY);
            if (pair.getRight() != 1) {
                context.sendMessage(CommandUtil.optionMismatchMessage(COMMAND_OPTION_ARRAY));
                context.sendMessage(super.cmdLineSyntax);
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
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleLatest(Context context, CommandLine cmd) throws Exception {
        List<LongIdKey> pointKeys;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 pointKeys。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            pointKeys = JSON.parseArray(json, WebInputLongIdKey.class).stream().map(WebInputLongIdKey::toStackBean)
                    .collect(Collectors.toList());
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 pointKeys。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
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
            CropResult cropResult = cropData(datas, context, "输入 q 退出查询");
            if (cropResult.exitFlag) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.beginIndex; i < cropResult.endIndex; i++) {
                D data = datas.get(i);
                printLatestData(i, cropResult.endIndex, data, context);
            }
        }
    }

    protected abstract void printLatestData(int i, int endIndex, D data, Context context) throws Exception;

    private void handleLookup(Context context, CommandLine cmd) throws Exception {
        LookupInfo lookupInfo;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 lookupInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            lookupInfo = WebInputLookupInfo.toStackBean(JSON.parseObject(json, WebInputLookupInfo.class));
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 lookupInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
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
            CropResult cropResult = cropData(datas, context, "输入 q 退出查询");
            if (cropResult.exitFlag) {
                break;
            }
            context.sendMessage("");
            for (int i = cropResult.beginIndex; i < cropResult.endIndex; i++) {
                D data = datas.get(i);
                printQueryData(i, cropResult.endIndex, data, context);
            }
        }
    }

    protected abstract void printLookupData(int i, int endIndex, QueryResult.Item item, Context context)
            throws Exception;

    private void handleQuery(Context context, CommandLine cmd) throws Exception {
        QueryInfo queryInfo;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 queryInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            queryInfo = WebInputQueryInfo.toStackBean(JSON.parseObject(json, WebInputQueryInfo.class));
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 queryInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
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

    private void handleNativeQuery(Context context, CommandLine cmd) throws Exception {
        NativeQueryInfo nativeQueryInfo;

        // 如果有 -json 选项，则从选项中获取 JSON，转化为 queryInfo。
        if (cmd.hasOption(COMMAND_OPTION_JSON)) {
            String json = (String) cmd.getParsedOptionValue(COMMAND_OPTION_JSON);
            nativeQueryInfo = WebInputNativeQueryInfo.toStackBean(
                    JSON.parseObject(json, WebInputNativeQueryInfo.class)
            );
        }
        // 如果有 --json-file 选项，则从选项中获取 JSON 文件，转化为 queryInfo。
        else if (cmd.hasOption(COMMAND_OPTION_JSON_FILE)) {
            File jsonFile = (File) cmd.getParsedOptionValue(COMMAND_OPTION_JSON_FILE);
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

    private void processQueryResultSequence(Context context, List<QueryResult.Sequence> sequences) throws Exception {
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
                    "endDate: %3$tY-%3$tm-%3$td %3$tH:%3$tM:%3$tS.%3$tL";
            context.sendMessage(String.format(
                    sequenceFormat, sequence.getPointKey().getLongId(),
                    sequence.getStartDate(),
                    sequence.getEndDate()
            ));

            List<QueryResult.Item> items = sequence.getItems();

            while (true) {
                CropResult cropResult = cropData(items, context, "输入 q 返回至序列选择");
                if (cropResult.exitFlag) {
                    break;
                }
                context.sendMessage("");
                for (int i = cropResult.beginIndex; i < cropResult.endIndex; i++) {
                    QueryResult.Item item = items.get(i);
                    printLookupData(i, cropResult.endIndex, item, context);
                }
            }
        }
    }

    protected abstract void printQueryData(int i, int endIndex, D data, Context context) throws Exception;

    private <T> CropResult cropData(List<T> originData, Context context, String quitPrompt) throws Exception {
        int beginIndex;
        int endIndex;

        while (true) {
            context.sendMessage("数据总数: " + originData.size());
            context.sendMessage("");
            context.sendMessage("输入 all 查看所有数据");
            context.sendMessage("输入 begin-end 查看指定范围的数据");
            context.sendMessage(quitPrompt);
            context.sendMessage("");

            String message = context.receiveMessage();

            if (message.equalsIgnoreCase("q")) {
                return new CropResult(-1, -1, true);
            } else if (message.equalsIgnoreCase("all")) {
                beginIndex = 0;
                endIndex = originData.size();
            } else {
                String[] split = message.split("-");
                if (split.length != 2) {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                    continue;
                }
                try {
                    beginIndex = Integer.parseInt(split[0]);
                    endIndex = Integer.parseInt(split[1]);
                } catch (NumberFormatException e) {
                    context.sendMessage("输入格式错误");
                    context.sendMessage("");
                    continue;
                }
                if (beginIndex < 0 || endIndex > originData.size() || beginIndex >= endIndex) {
                    context.sendMessage("输入范围错误");
                    context.sendMessage("");
                    continue;
                }
            }
            break;
        }

        return new CropResult(beginIndex, endIndex, false);
    }

    protected static final class CropResult {

        private final int beginIndex;
        private final int endIndex;
        private final boolean exitFlag;

        public CropResult(int beginIndex, int endIndex, boolean exitFlag) {
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
            this.exitFlag = exitFlag;
        }

        public int getBeginIndex() {
            return beginIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public boolean isExitFlag() {
            return exitFlag;
        }

        @Override
        public String toString() {
            return "CropResult{" +
                    "beginIndex=" + beginIndex +
                    ", endIndex=" + endIndex +
                    ", exitFlag=" + exitFlag +
                    '}';
        }
    }
}
