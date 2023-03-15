package com.dwarfeng.fdr.impl.handler.resetter;

import org.springframework.stereotype.Component;

/**
 * 永远不执行重置的重置器。
 *
 * @author DwArFeng
 * @since 1.11.0
 */
@Component
public class NeverResetter extends AbstractResetter {

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public String toString() {
        return "NeverResetter{}";
    }
}