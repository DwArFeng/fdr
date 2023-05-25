package com.dwarfeng.fdr.stack.bean.entity;

import com.dwarfeng.fdr.stack.bean.key.QuerySupportKey;
import com.dwarfeng.subgrade.stack.bean.entity.Entity;

import java.util.Arrays;

/**
 * 查询支持。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class QuerySupport implements Entity<QuerySupportKey> {

    private static final long serialVersionUID = 1329328129625291669L;

    private QuerySupportKey key;
    private String[] exampleParams;
    private String description;

    public QuerySupport() {
    }

    public QuerySupport(QuerySupportKey key, String[] exampleParams, String description) {
        this.key = key;
        this.exampleParams = exampleParams;
        this.description = description;
    }

    @Override
    public QuerySupportKey getKey() {
        return key;
    }

    @Override
    public void setKey(QuerySupportKey key) {
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
        return "QuerySupport{" +
                "key=" + key +
                ", exampleParams=" + Arrays.toString(exampleParams) +
                ", description='" + description + '\'' +
                '}';
    }
}
