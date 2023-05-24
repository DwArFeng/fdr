package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSupport;
import com.dwarfeng.subgrade.sdk.bean.key.FastJsonStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Objects;

/**
 * FastJson触发器支持。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class FastJsonTriggerSupport implements Bean {

    private static final long serialVersionUID = 6207521205562891701L;

    public static FastJsonTriggerSupport of(TriggerSupport triggerSupport) {
        if (Objects.isNull(triggerSupport)) {
            return null;
        } else {
            return new FastJsonTriggerSupport(
                    FastJsonStringIdKey.of(triggerSupport.getKey()),
                    triggerSupport.getLabel(),
                    triggerSupport.getDescription(),
                    triggerSupport.getExampleParam()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonStringIdKey key;

    @JSONField(name = "label", ordinal = 2)
    private String label;

    @JSONField(name = "description", ordinal = 3)
    private String description;

    @JSONField(name = "example_param", ordinal = 4)
    private String exampleParam;

    public FastJsonTriggerSupport() {
    }

    public FastJsonTriggerSupport(
            FastJsonStringIdKey key, String label, String description, String exampleParam
    ) {
        this.key = key;
        this.label = label;
        this.description = description;
        this.exampleParam = exampleParam;
    }

    public FastJsonStringIdKey getKey() {
        return key;
    }

    public void setKey(FastJsonStringIdKey key) {
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
        return "FastJsonTriggerSupport{" +
                "key=" + key +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", exampleParam='" + exampleParam + '\'' +
                '}';
    }
}
