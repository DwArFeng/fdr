package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.bean.dto.TriggeredData;
import com.dwarfeng.fdr.stack.service.TriggeredViewQosService;
import com.dwarfeng.springtelqos.node.config.TelqosCommand;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;

import java.util.Objects;

@TelqosCommand
public class TriggeredViewCommand extends ViewCommand<TriggeredData> {

    public TriggeredViewCommand(TriggeredViewQosService triggeredWatchQosService) {
        super("tv", "被触发查看指令", triggeredWatchQosService);
    }

    @Override
    protected void printLatestData(int i, int endIndex, TriggeredData data, Context context) throws Exception {
        printTriggeredData(i, endIndex, data, context);
    }

    @Override
    protected void printQueryData(int i, int endIndex, TriggeredData data, Context context) throws Exception {
        printTriggeredData(i, endIndex, data, context);
    }

    @SuppressWarnings("DuplicatedCode")
    private void printTriggeredData(int i, int endIndex, TriggeredData data, Context context) throws TelqosException {
        context.sendMessage(String.format(
                "索引: %d/%d",
                i, endIndex
        ));
        if (Objects.isNull(data)) {
            context.sendMessage("  null");
        } else {
            context.sendMessage(String.format(
                    "  pointId: %s",
                    data.getPointKey().getLongId()
            ));
            context.sendMessage(String.format(
                    "  valueClass: %s",
                    Objects.isNull(data.getValue()) ? "null" : data.getValue().getClass().getCanonicalName()
            ));
            context.sendMessage(String.format(
                    "  value: %s",
                    data.getValue()
            ));
            context.sendMessage(String.format(
                    "  happenedDate: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL",
                    data.getHappenedDate()
            ));
            context.sendMessage(String.format(
                    "  triggerId: %s",
                    data.getTriggerKey().getLongId()
            ));
            context.sendMessage(String.format(
                    "  message: %s",
                    data.getMessage()
            ));
        }
        context.sendMessage("");
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void printLookupData(int i, int endIndex, QueryResult.Item item, Context context) throws Exception {
        context.sendMessage(String.format(
                "索引: %d/%d",
                i, endIndex
        ));
        context.sendMessage(String.format(
                "  pointId: %s",
                item.getPointKey().getLongId()
        ));
        context.sendMessage(String.format(
                "  valueClass: %s",
                Objects.isNull(item.getValue()) ? "null" : item.getValue().getClass().getCanonicalName()
        ));
        context.sendMessage(String.format(
                "  value: %s",
                item.getValue()
        ));
        context.sendMessage(String.format(
                "  happenedDate: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL",
                item.getHappenedDate()
        ));
        context.sendMessage("");
    }
}
