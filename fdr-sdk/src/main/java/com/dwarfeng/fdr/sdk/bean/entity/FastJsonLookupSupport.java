package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.bean.key.FastJsonLookupSupportKey;
import com.dwarfeng.fdr.stack.bean.entity.LookupSupport;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Arrays;
import java.util.Objects;

/**
 * FastJson 查看支持。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonLookupSupport implements Bean {

    private static final long serialVersionUID = 2258628780816890114L;

    public static FastJsonLookupSupport of(LookupSupport lookupSupport) {
        if (Objects.isNull(lookupSupport)) {
            return null;
        } else {
            return new FastJsonLookupSupport(
                    FastJsonLookupSupportKey.of(lookupSupport.getKey()),
                    lookupSupport.getExampleParams(),
                    lookupSupport.getDescription()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonLookupSupportKey key;

    @JSONField(name = "example_params", ordinal = 2)
    private String[] exampleParams;

    @JSONField(name = "description", ordinal = 3)
    private String description;

    public FastJsonLookupSupport() {
    }

    public FastJsonLookupSupport(FastJsonLookupSupportKey key, String[] exampleParams, String description) {
        this.key = key;
        this.exampleParams = exampleParams;
        this.description = description;
    }

    public FastJsonLookupSupportKey getKey() {
        return key;
    }

    public void setKey(FastJsonLookupSupportKey key) {
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
        return "FastJsonLookupSupport{" +
                "key=" + key +
                ", exampleParams=" + Arrays.toString(exampleParams) +
                ", description='" + description + '\'' +
                '}';
    }
}
