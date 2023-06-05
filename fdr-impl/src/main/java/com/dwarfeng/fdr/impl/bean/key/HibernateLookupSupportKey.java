package com.dwarfeng.fdr.impl.bean.key;

import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * Hibernate 查看支持主键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class HibernateLookupSupportKey implements Key {

    private static final long serialVersionUID = 1748882619491063090L;

    private String category;
    private String preset;

    public HibernateLookupSupportKey() {
    }

    public HibernateLookupSupportKey(String category, String preset) {
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

        HibernateLookupSupportKey that = (HibernateLookupSupportKey) o;

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
        return "HibernateLookupSupportKey{" +
                "category='" + category + '\'' +
                ", preset='" + preset + '\'' +
                '}';
    }
}
