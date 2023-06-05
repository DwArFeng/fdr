package com.dwarfeng.fdr.sdk.bean.key;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.key.LookupSupportKey;
import com.dwarfeng.subgrade.stack.bean.key.Key;

import java.util.Objects;

/**
 * FastJson 查看支持主键。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class FastJsonLookupSupportKey implements Key {

    private static final long serialVersionUID = 473095105191369755L;

    public static FastJsonLookupSupportKey of(LookupSupportKey lookupSupportKey) {
        if (Objects.isNull(lookupSupportKey)) {
            return null;
        } else {
            return new FastJsonLookupSupportKey(
                    lookupSupportKey.getCategory(),
                    lookupSupportKey.getPreset()
            );
        }
    }

    @JSONField(name = "category", ordinal = 1)
    private String category;

    @JSONField(name = "preset", ordinal = 2)
    private String preset;

    public FastJsonLookupSupportKey() {
    }

    public FastJsonLookupSupportKey(String category, String preset) {
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
        return "FastJsonLookupSupportKey{" +
                "category='" + category + '\'' +
                ", preset='" + preset + '\'' +
                '}';
    }
}
