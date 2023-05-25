package com.dwarfeng.fdr.stack.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * 查询支持主键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class QuerySupportKey implements Key {

    private static final long serialVersionUID = 13514381209424644L;

    private String category;
    private String preset;

    public QuerySupportKey() {
    }

    public QuerySupportKey(String category, String preset) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuerySupportKey that = (QuerySupportKey) o;

        if (!Objects.equals(category, that.category)) return false;
        return Objects.equals(preset, that.preset);
    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (preset != null ? preset.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuerySupportKey{" +
                "category='" + category + '\'' +
                ", preset='" + preset + '\'' +
                '}';
    }
}
