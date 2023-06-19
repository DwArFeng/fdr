package com.dwarfeng.fdr.impl.handler.mapper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.util.CompareUtil;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.fdr.stack.exception.MapperMakeException;
import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 裁剪映射器注册。
 *
 * <p>
 * 用于裁剪序列的起始时间和结束时间。映射器工作时会寻找序列中发生时间最早和最晚的数据，
 * 然后将这两个数据的发生时间作为序列的起始时间和结束时间。
 *
 * @author mooyuan
 * @since 2.0.0
 */
@Component
public class TrimMapperRegistry extends AbstractMapperRegistry {

    public static final String MAPPER_TYPE = "trim_mapper";

    private final ApplicationContext ctx;

    public TrimMapperRegistry(ApplicationContext ctx) {
        super(MAPPER_TYPE);
        this.ctx = ctx;
    }

    @Override
    public Mapper makeMapper() throws MapperException {
        try {
            return ctx.getBean(TrimMapper.class);
        } catch (Exception e) {
            throw new MapperMakeException(e);
        }
    }

    @Override
    public String provideLabel() {
        return "裁剪映射器";
    }

    @Override
    public String provideDescription() {
        return "映射器工作时会寻找序列中发生时间最早和最晚的数据，然后将这两个数据的发生时间作为序列的起始时间和结束时间,若使用 only_trim_start 配置项可以只裁剪序列的起始时间。";
    }

    @Override
    public String provideExampleParam() {
        return JSON.toJSONString(new Config(false));
    }

    @Component
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static class TrimMapper extends OneToOneMapper {

        @Override
        protected Sequence doOneToOneMap(MapParam mapParam, Sequence sequence) {

            // 获得配置。
            Config config = JSON.parseObject(mapParam.getParam(), Config.class);

            return trimSequence(sequence, config.getOnlyTrimStart());
        }

        // 排序并截取序列
        private Sequence trimSequence(Sequence sequence, boolean only_trim_start) {
            // 获取序列的起始时间与结束时间。
            List<Item> items = new ArrayList<>(sequence.getItems());
            items.sort(CompareUtil.DATA_HAPPENED_DATE_ASC_COMPARATOR);

            Date startDate = items.get(0).getHappenedDate();
            Date endDate = items.get(items.size() - 1).getHappenedDate();

            // 如果起始时间等于结束时间只取一个值即可
            if (only_trim_start) {

                endDate = sequence.getEndDate();
            }

            // 返回新的序列
            return new Sequence(sequence.getPointKey(), items, startDate, endDate);
        }

        @Override
        public String toString() {
            return "TrimMapper{}";
        }

    }

    public static class Config implements Bean {

        private static final long serialVersionUID = 3688860693739279015L;

        @JSONField(name = "only_trim_start", ordinal = 1)
        private boolean onlyTrimStart = false;

        @JSONField(name = "#only_trim_start", ordinal = 2, deserialize = false)
        private String onlyTrimStartRem = "当onlyTrimStart为true时只剪裁序列的起始时间，false裁剪序列的起始时间和结束时间";

        public Config() {
        }

        public Config(boolean onlyTrimStart) {
            this.onlyTrimStart = onlyTrimStart;
        }

        public boolean getOnlyTrimStart() {
            return onlyTrimStart;
        }

        public void setOnlyTrimStart(boolean onlyTrimStart) {
            this.onlyTrimStart = onlyTrimStart;
        }

        public String getOnlyTrimStartRem() {
            return onlyTrimStartRem;
        }

        public void setOnlyTrimStartRem(String onlyTrimStartRem) {
            this.onlyTrimStartRem = onlyTrimStartRem;
        }

        @Override
        public String toString() {
            return "Config{" + "onlyTrimStart=" + onlyTrimStart + ", onlyTrimStartRem='" + onlyTrimStartRem + '\'' + '}';
        }
    }
}
