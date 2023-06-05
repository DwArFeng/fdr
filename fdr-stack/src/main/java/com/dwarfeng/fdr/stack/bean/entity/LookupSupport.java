package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.LookupSupportKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

import java.util.Arrays;

/**
 * 查看支持。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LookupSupport implements Entity<LookupSupportKey> {

    private static final long serialVersionUID = 7701748644097971882L;

    private LookupSupportKey key;
    private String[] exampleParams;
    private String description;

    public LookupSupport() {
    }

    public LookupSupport(LookupSupportKey key, String[] exampleParams, String description) {
        this.key = key;
        this.exampleParams = exampleParams;
        this.description = description;
    }

    @Override
    public LookupSupportKey getKey() {
        return key;
    }

    @Override
    public void setKey(LookupSupportKey key) {
        this.key = key;
    }

    public String[] getExampleParams() {
        return exampleParams;
    }

    public void setExampleParams(String[] exampleParams) {
        this.exampleParams = exampleParams;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "LookupSupport{" +
                "key=" + key +
                ", exampleParams=" + Arrays.toString(exampleParams) +
                ", description='" + description + '\'' +
                '}';
    }
}
