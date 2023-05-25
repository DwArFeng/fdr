package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.bean.key.FastJsonQuerySupportKey;
import com.dwarfeng.fdr.stack.bean.entity.QuerySupport;
import com.dwarfeng.subgrade.stack.bean.Bean;

import java.util.Arrays;
import java.util.Objects;

/**
 * FastJson 查询支持。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonQuerySupport implements Bean {

    private static final long serialVersionUID = -3204773651881528455L;

    public static FastJsonQuerySupport of(QuerySupport querySupport) {
        if (Objects.isNull(querySupport)) {
            return null;
        } else {
            return new FastJsonQuerySupport(
                    FastJsonQuerySupportKey.of(querySupport.getKey()),
                    querySupport.getExampleParams(),
                    querySupport.getDescription()
            );
        }
    }

    @JSONField(name = "key", ordinal = 1)
    private FastJsonQuerySupportKey key;

    @JSONField(name = "example_params", ordinal = 2)
    private String[] exampleParams;

    @JSONField(name = "description", ordinal = 3)
    private String description;

    public FastJsonQuerySupport() {
    }

    public FastJsonQuerySupport(FastJsonQuerySupportKey key, String[] exampleParams, String description) {
        this.key = key;
        this.exampleParams = exampleParams;
        this.description = description;
    }

    public FastJsonQuerySupportKey getKey() {
        return key;
    }

    public void setKey(FastJsonQuerySupportKey key) {
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
        return "FastJsonQuerySupport{" +
                "key=" + key +
                ", exampleParams=" + Arrays.toString(exampleParams) +
                ", description='" + description + '\'' +
                '}';
    }
}
