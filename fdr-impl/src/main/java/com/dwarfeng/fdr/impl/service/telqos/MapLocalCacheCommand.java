package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.service.MapQosService;
import com.dwarfeng.springtelqos.sdk.command.CliCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MapLocalCacheCommand extends CliCommand {

    private static final String IDENTITY = "mlc";
    private static final String DESCRIPTION = "映射查询本地缓存操作";
    private static final String CMD_LINE_SYNTAX_C = "mlc -c";
    private static final String CMD_LINE_SYNTAX_P = "mlc -t mapper-type";
    private static final String CMD_LINE_SYNTAX = CMD_LINE_SYNTAX_C + System.lineSeparator() + CMD_LINE_SYNTAX_P;

    public MapLocalCacheCommand() {
        super(IDENTITY, DESCRIPTION, CMD_LINE_SYNTAX);
    }

    @Autowired
    private MapQosService mapQosService;

    @Override
    protected List<Option> buildOptions() {
        List<Option> list = new ArrayList<>();
        list.add(Option.builder("c").optionalArg(true).hasArg(false).desc("清除缓存").build());
        list.add(Option.builder("t").optionalArg(true).hasArg(true).type(Number.class).argName("mapper-type")
                .desc("查看指定映射类型的映射器，如果本地缓存中不存在，则尝试抓取").build());
        return list;
    }

    @Override
    protected void executeWithCmd(Context context, CommandLine cmd) throws TelqosException {
        try {
            Pair<String, Integer> pair = analyseCommand(cmd);
            if (pair.getRight() != 1) {
                context.sendMessage("下列选项必须且只能含有一个: -c -t");
                context.sendMessage(CMD_LINE_SYNTAX);
                return;
            }
            switch (pair.getLeft()) {
                case "c":
                    handleC(context);
                    break;
                case "t":
                    handleT(context, cmd);
                    break;
            }
        } catch (Exception e) {
            throw new TelqosException(e);
        }
    }

    private void handleC(Context context) throws Exception {
        mapQosService.clearLocalCache();
        context.sendMessage("缓存已清空");
    }

    private void handleT(Context context, CommandLine cmd) throws Exception {
        String mapperType = cmd.getOptionValue("t");
        Mapper mapper = mapQosService.getMapper(mapperType);
        if (Objects.isNull(mapper)) {
            context.sendMessage("not exists!");
            return;
        }
        context.sendMessage(String.format("mapper: %s", mapper));
    }

    private Pair<String, Integer> analyseCommand(CommandLine cmd) {
        int i = 0;
        String subCmd = null;
        if (cmd.hasOption("c")) {
            i++;
            subCmd = "c";
        }
        if (cmd.hasOption("t")) {
            i++;
            subCmd = "t";
        }
        return Pair.of(subCmd, i);
    }
}
