package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.exception.UnsupportedWasherTypeException;
import com.dwarfeng.fdr.stack.exception.WasherException;
import com.dwarfeng.fdr.stack.handler.Washer;
import com.dwarfeng.fdr.stack.handler.WasherHandler;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class WasherHandlerImpl implements WasherHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WasherHandlerImpl.class);

    private final List<WasherMaker> washerMakers;

    public WasherHandlerImpl(List<WasherMaker> washerMakers) {
        this.washerMakers = Optional.ofNullable(washerMakers).orElse(Collections.emptyList());
    }

    @Override
    public Washer make(String type, String param) throws HandlerException {
        try {
            // 生成清洗器。
            LOGGER.debug("通过清洗器信息构建新的的清洗器...");
            WasherMaker washerMaker = washerMakers.stream().filter(maker -> maker.supportType(type))
                    .findFirst().orElseThrow(() -> new UnsupportedWasherTypeException(type));
            Washer washer = washerMaker.makeWasher(type, param);
            LOGGER.debug("清洗器构建成功!");
            LOGGER.debug("清洗器: {}", washer);
            return washer;
        } catch (WasherException e) {
            throw e;
        } catch (Exception e) {
            throw new WasherException(e);
        }
    }
}
