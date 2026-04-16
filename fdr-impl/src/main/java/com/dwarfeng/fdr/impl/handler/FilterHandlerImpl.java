package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.sdk.handler.FilterMaker;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.UnsupportedFilterTypeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.FilterHandler;
import com.dwarfeng.fdr.stack.handler.RecordMemoryHandler;
import com.dwarfeng.fdr.stack.struct.RecordMemory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FilterHandlerImpl implements FilterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterHandlerImpl.class);

    private final RecordMemoryHandler recordMemoryHandler;

    private final List<FilterMaker> filterMakers;

    private final InternalFilterContext filterContext = new InternalFilterContext();

    public FilterHandlerImpl(List<FilterMaker> filterMakers, RecordMemoryHandler recordMemoryHandler) {
        this.recordMemoryHandler = recordMemoryHandler;
        this.filterMakers = Optional.ofNullable(filterMakers).orElse(Collections.emptyList());
    }

    @Override
    public Filter make(String type, String param) throws HandlerException {
        try {
            // 生成过滤器。
            LOGGER.debug("通过过滤器信息构建新的的过滤器...");
            FilterMaker filterMaker = filterMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedFilterTypeException(type));
            Filter filter = filterMaker.makeFilter(type, param);
            filter.init(filterContext);
            LOGGER.debug("过滤器构建成功!");
            LOGGER.debug("过滤器: {}", filter);
            return filter;
        } catch (FilterException e) {
            throw e;
        } catch (Exception e) {
            throw new FilterException(e);
        }
    }

    private final class InternalFilterContext implements Filter.Context {

        @Override
        public List<RecordMemory> lookupRecordMemory(LongIdKey pointKey) throws Exception {
            return recordMemoryHandler.lookup(pointKey);
        }
    }
}
