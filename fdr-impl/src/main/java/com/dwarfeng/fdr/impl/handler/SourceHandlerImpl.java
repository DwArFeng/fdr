package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.RecordInfo;
import com.dwarfeng.fdr.stack.handler.Source;
import com.dwarfeng.fdr.stack.handler.SourceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class SourceHandlerImpl implements SourceHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceHandlerImpl.class);

    private final RecordProcessor recordProcessor;

    private final List<Source> sources;

    private final InternalSourceContext sourceContext = new InternalSourceContext();

    public SourceHandlerImpl(RecordProcessor recordProcessor, List<Source> sources) {
        this.recordProcessor = recordProcessor;
        this.sources = Optional.ofNullable(sources).orElse(Collections.emptyList());
    }

    @PostConstruct
    public void init() {
        LOGGER.info("初始化数据源...");
        sources.forEach(source -> source.init(sourceContext));
    }

    @Override
    public List<Source> all() {
        return sources;
    }

    private class InternalSourceContext implements Source.Context {

        @Override
        public void record(RecordInfo recordInfo) throws Exception {
            recordProcessor.record(recordInfo);
        }
    }
}
