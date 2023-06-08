package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.service.NormalViewQosService;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class NormalViewCommand extends ViewCommand<NormalData> {

    public NormalViewCommand(NormalViewQosService normalWatchQosService) {
        super("nv", "一般查看指令", normalWatchQosService);
    }

    @Override
    protected void printInspectData(int i, int endIndex, NormalData data, Context context) throws Exception {
        printNormalData(i, endIndex, data, context);
    }

    @Override
    protected void printQueryData(int i, int endIndex, NormalData data, Context context) throws Exception {
        printNormalData(i, endIndex, data, context);
    }

    @SuppressWarnings("DuplicatedCode")
    private void printNormalData(int i, int endIndex, NormalData data, Context context) throws TelqosException {
        context.sendMessage(String.format(
                "索引: %d/%d",
                i, endIndex
        ));
        context.sendMessage(String.format(
                "  pointId: %s",
                data.getPointKey().getLongId()
        ));
        context.sendMessage(String.format(
                "  valueClass: %s",
                data.getValue().getClass().getCanonicalName()
        ));
        context.sendMessage(String.format(
                "  value: %s",
                data.getValue()
        ));
        context.sendMessage(String.format(
                "  happenedDate: %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS.%1$tL",
                data.getHappenedDate()
        ));
        context.sendMessage("");
    }

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
