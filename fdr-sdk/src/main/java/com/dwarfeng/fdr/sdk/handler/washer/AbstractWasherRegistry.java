package com.dwarfeng.fdr.sdk.handler.washer;

import com.dwarfeng.fdr.sdk.handler.WasherMaker;
import com.dwarfeng.fdr.sdk.handler.WasherSupporter;

import java.util.Objects;

/**
 * 抽象清洗器注册。
 *
 * @author DwArFeng
 * @since 2.3.0
 */
public abstract class AbstractWasherRegistry implements WasherMaker, WasherSupporter {

    protected String washerType;

    public AbstractWasherRegistry() {
    }

    public AbstractWasherRegistry(String washerType) {
        this.washerType = washerType;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(washerType, type);
    }

    @Override
    public String provideType() {
        return washerType;
    }

    public String getWasherType() {
        return washerType;
    }

    public void setWasherType(String washerType) {
        this.washerType = washerType;
    }

    @Override
    public String toString() {
        return "AbstractWasherRegistry{" +
                "washerType='" + washerType + '\'' +
                '}';
    }
}
