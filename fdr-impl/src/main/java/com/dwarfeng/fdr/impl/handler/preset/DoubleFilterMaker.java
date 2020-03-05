package com.dwarfeng.fdr.impl.handler.preset;

import com.dwarfeng.fdr.impl.handler.FilterMaker;
import com.dwarfeng.fdr.stack.bean.dto.DataInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.fdr.stack.bean.entity.FilterSupport;
import com.dwarfeng.fdr.stack.bean.entity.FilteredValue;
import com.dwarfeng.fdr.stack.exception.FilterException;
import com.dwarfeng.fdr.stack.exception.FilterMakeException;
import com.dwarfeng.fdr.stack.handler.Filter;
import com.dwarfeng.fdr.stack.service.FilterSupportMaintainService;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Double过滤器制造器。
 *
 * @author DwArFeng
 * @since 1.1.0.a
 */
@Component
public class DoubleFilterMaker implements FilterMaker {

    public static final String SUPPORT_TYPE = "double_filter";
    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleFilterMaker.class);

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private FilterSupportMaintainService service;

    @PostConstruct
    public void init() {
        try {
            String label = "双精度浮点过滤器";
            String description = "如果数据值是双精度浮点数，则通过过滤。";
            String exampleContent = "";
            service.insertIfNotExists(
                    new FilterSupport(
                            new StringIdKey(SUPPORT_TYPE),
                            label,
                            description,
                            exampleContent
                    )
            );
        } catch (Exception e) {
            LOGGER.warn("未能向 FilterSupportMaintainService 中确认或添加过滤器信息", e);
        }
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(SUPPORT_TYPE, type);
    }

    @Override
    public Filter makeFilter(FilterInfo filterInfo) throws FilterException {
        try {
            DoubleFilter filter = ctx.getBean(DoubleFilter.class);
            filter.setPointKey(filterInfo.getPointKey());
            filter.setFilterInfoKey(filterInfo.getKey());
            return filter;
        } catch (Exception e) {
            throw new FilterMakeException(e);
        }
    }

    @Component
    @Scope("prototype")
    public static class DoubleFilter implements Filter, Bean {

        private static final long serialVersionUID = -3161256701795506170L;
        private static final Logger LOGGER = LoggerFactory.getLogger(DoubleFilter.class);

        private LongIdKey pointKey;
        private LongIdKey filterInfoKey;

        public DoubleFilter() {
        }

        @Override
        public void test(DataInfo dataInfo, Consumer<? super FilteredValue> consumer) throws FilterException {
            try {
                String value = dataInfo.getValue();
                try {
                    Double.parseDouble(value);
                } catch (NumberFormatException e) {
                    LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 不是数字或超过双精度浮点数范围, 不能通过过滤...", e);
                    FilteredValue filteredValue = new FilteredValue(
                            null,
                            pointKey,
                            filterInfoKey,
                            dataInfo.getHappenedDate(),
                            dataInfo.getValue(),
                            "数据值不是数字或超过双精度浮点数范围"
                    );
                    if (Objects.nonNull(consumer)) {
                        consumer.accept(filteredValue);
                    }
                    return;
                }
                LOGGER.debug("测试数据值 " + dataInfo.getValue() + " 通过过滤器...");
            } catch (Exception e) {
                throw new FilterException(e);
            }
        }

        public LongIdKey getPointKey() {
            return pointKey;
        }

        public void setPointKey(LongIdKey pointKey) {
            this.pointKey = pointKey;
        }

        public LongIdKey getFilterInfoKey() {
            return filterInfoKey;
        }

        public void setFilterInfoKey(LongIdKey filterInfoKey) {
            this.filterInfoKey = filterInfoKey;
        }

        @Override
        public String toString() {
            return "DoubleFilter{" +
                    "pointKey=" + pointKey +
                    ", filterInfoKey=" + filterInfoKey +
                    '}';
        }
    }
}
