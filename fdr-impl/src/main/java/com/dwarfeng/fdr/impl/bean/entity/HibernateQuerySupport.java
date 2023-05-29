package com.dwarfeng.fdr.impl.bean.entity;

import com.dwarfeng.fdr.impl.bean.key.HibernateQuerySupportKey;
import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@IdClass(HibernateQuerySupportKey.class)
@Table(name = "tbl_query_support")
public class HibernateQuerySupport implements Bean {

    private static final long serialVersionUID = -622679147980932923L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "category", length = Constraints.LENGTH_CATEGORY, nullable = false)
    private String category;

    @Id
    @Column(name = "preset", length = Constraints.LENGTH_PRESET, nullable = false)
    private String preset;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @SuppressWarnings("JpaAttributeTypeInspection")
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "tbl_query_support_example_param",
            joinColumns = {@JoinColumn(name = "category"), @JoinColumn(name = "preset")}
    )
    @Column(name = "example_param", length = Constraints.LENGTH_PARAM)
    @OrderColumn(name = "column_index")
    private String[] exampleParams;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateQuerySupportKey getKey() {
        if (null == category || null == preset) {
            return null;
        }
        return new HibernateQuerySupportKey(category, preset);
    }

    public void setKey(HibernateQuerySupportKey key) {
        if (Objects.isNull(key)) {
            this.category = null;
            this.preset = null;
        } else {
            this.category = key.getCategory();
            this.preset = key.getPreset();
        }
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
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
        return getClass().getSimpleName() + "(" +
                "category = " + category + ", " +
                "preset = " + preset + ", " +
                "exampleParams = " + Arrays.toString(exampleParams) + ", " +
                "description = " + description + ")";
    }
}
