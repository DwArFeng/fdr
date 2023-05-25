package com.dwarfeng.fdr.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.key.QuerySupportKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 查询支持主键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonQuerySupportKey implements Key {

    private static final long serialVersionUID = 7886967192210220842L;

    public static FastJsonQuerySupportKey of(QuerySupportKey querySupportKey) {
        if (Objects.isNull(querySupportKey)) {
            return null;
        } else {
            return new FastJsonQuerySupportKey(
                    querySupportKey.getCategory(),
                    querySupportKey.getPreset()
            );
        }
    }

    @JSONField(name = "category", ordinal = 1)
    private String category;

    @JSONField(name = "preset", ordinal = 2)
    private String preset;

    public FastJsonQuerySupportKey() {
    }

    public FastJsonQuerySupportKey(String category, String preset) {
        this.category = category;
        this.preset = preset;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPreset() {
        return preset;
    }

    public void setPreset(String preset) {
        this.preset = preset;
    }

    @Override
    public String toString() {
        return "FastJsonQuerySupportKey{" +
                "category='" + category + '\'' +
                ", preset='" + preset + '\'' +
                '}';
    }
}
