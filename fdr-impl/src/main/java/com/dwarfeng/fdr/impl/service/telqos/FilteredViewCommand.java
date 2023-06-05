package com.dwarfeng.fdr.impl.service.telqos;

import com.dwarfeng.fdr.stack.bean.dto.FilteredData;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.service.FilteredViewQosService;
import com.dwarfeng.springtelqos.stack.command.Context;
import com.dwarfeng.springtelqos.stack.exception.TelqosException;
import org.springframework.stereotype.Component;

@Component
public class FilteredViewCommand extends ViewCommand<FilteredData> {

    public FilteredViewCommand(FilteredViewQosService filteredWatchQosService) {
        super("fv", "被过滤查看指令", filteredWatchQosService);
    }

    @Override
    protected void printInspectData(int i, int endIndex, FilteredData data, Context context) throws Exception {
        printFilteredData(i, endIndex, data, context);
    }

    @Override
    protected void printQueryData(int i, int endIndex, FilteredData data, Context context) throws Exception {
        printFilteredData(i, endIndex, data, context);
    }

    @SuppressWarnings("DuplicatedCode")
    private void printFilteredData(int i, int endIndex, FilteredData data, Context context) throws TelqosException {
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
        context.sendMessage(String.format(
                "  filterId: %s",
                data.getFilterKey().getLongId()
        ));
        context.sendMessage(String.format(
                "  message: %s",
                data.getMessage()
        ));
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
                item.getValue().getClass().getCanonicalName()
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
