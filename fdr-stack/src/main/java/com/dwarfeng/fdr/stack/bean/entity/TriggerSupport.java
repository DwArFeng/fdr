package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.subgrade.stack.bean.entity.Entity;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;

/**
 * 触发器支持。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class TriggerSupport implements Entity<StringIdKey> {

    private static final long serialVersionUID = -8357178352676090724L;

    private StringIdKey key;
    private String label;
    private String description;
    private String exampleParam;

    public TriggerSupport() {
    }

    public TriggerSupport(StringIdKey key, String label, String description, String exampleParam) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.exampleParam = exampleParam;
    }

    @Override
    public StringIdKey getKey() {
        return key;
    }

    @Override
    public void setKey(StringIdKey key) {
        this.key = key;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExampleParam() {
        return exampleParam;
    }

    public void setExampleParam(String exampleParam) {
        this.exampleParam = exampleParam;
    }

    @Override
    public String toString() {
        return "TriggerSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleParam='" + exampleParam + '\'' +
                '}';
    }
}
