package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.bean.dto.NormalData;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.service.NormalViewQosService;
import com.dwarfeng.springtelqos.sdk.configuration.TelqosCommand;
import com.dwarfeng.springtelqos.stack.command.CommandExecutor;

import java.util.Objects;

/**
 * 一般数据查看指令。
 *
 * <p>
 * 该指令继承自 {@link ViewCommand}，用于查询和显示一般数据（{@link NormalData}）。
 * 支持查询最新数据、按 ID 查询、原生查询和通用查询等操作。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@TelqosCommand
public class NormalViewCommand extends ViewCommand<NormalData> {

    @SuppressWarnings({"SpellCheckingInspection", "GrazieInspectionRunner", "RedundantSuppression"})
    private static final String IDENTITY = "nv";

    public NormalViewCommand(NormalViewQosService normalWatchQosService) {
        super(IDENTITY, normalWatchQosService);
    }

    @Override
    protected String provideCommandDescription() {
        return "一般查看指令";
    }

    @Override
    protected void printLatestData(int i, int endIndex, NormalData data, CommandExecutor.Context context)
            throws Exception {
        printNormalData(i, endIndex, data, context);
    }

    @Override
    protected void printLookupData(int i, int endIndex, NormalData data, CommandExecutor.Context context)
            throws Exception {
        printNormalData(i, endIndex, data, context);
    }

    @SuppressWarnings("DuplicatedCode")
    private void printNormalData(int i, int endIndex, NormalData data, CommandExecutor.Context context)
            throws Exception {
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
                    "  happenedDateNanoOffset: %d",
                    data.getHappenedDateNanoOffset()
            ));
        }
        context.sendMessage("");
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    protected void printQueryData(int i, int endIndex, QueryResult.Item item, CommandExecutor.Context context)
            throws Exception {
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
        context.sendMessage(String.format(
                "  happenedDateNanoOffset: %d",
                item.getHappenedDateNanoOffset()
        ));
        context.sendMessage("");
    }
}
