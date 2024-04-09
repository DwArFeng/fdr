package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.UnsupportedFilterTypeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.handler.FilterHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class FilterHandlerImpl implements FilterHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FilterHandlerImpl.class);

    @Autowired(required = false)
    private List<FilterMaker> filterMakers = Collections.emptyList();

    @Override
    public Filter make(FilterInfo filterInfo) throws FilterException {
        try {
            // 生成过滤器。
            LOGGER.debug("通过过滤器信息构建新的的过滤器...");
            FilterMaker filterMaker = filterMakers.stream().filter(maker -> maker.supportType(filterInfo.getType()))
                    .findFirst().orElseThrow(() -> new UnsupportedFilterTypeException(filterInfo.getType()));
            Filter filter = filterMaker.makeFilter(filterInfo);
            LOGGER.debug("过滤器构建成功!");
            LOGGER.debug("过滤器: " + filter);
            return filter;
        } catch (FilterException e) {
            throw e;
        } catch (Exception e) {
            throw new FilterException(e);
        }
    }
}
