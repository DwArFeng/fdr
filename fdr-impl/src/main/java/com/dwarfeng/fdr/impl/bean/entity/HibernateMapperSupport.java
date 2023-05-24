package com.dwarfeng.fdr.impl.bean.entity;

import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.util.Objects;

@javax.persistence.Entity
@IdClass(HibernateStringIdKey.class)
@Table(name = "tbl_mapper_support")
public class HibernateMapperSupport implements Bean {

    private static final long serialVersionUID = 5926287306531305775L;

    // -----------------------------------------------------------主键-----------------------------------------------------------
    @Id
    @Column(name = "id", length = Constraints.LENGTH_TYPE, nullable = false, unique = true)
    private String stringId;

    // -----------------------------------------------------------主属性字段-----------------------------------------------------------
    @Column(name = "label", length = 50, nullable = false)
    private String label;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "example_param", columnDefinition = "TEXT")
    private String exampleParam;

    public HibernateMapperSupport() {
    }

    // -----------------------------------------------------------映射用属性区-----------------------------------------------------------
    public HibernateStringIdKey getKey() {
        if (Objects.isNull(stringId)) {
            return null;
        }
        return new HibernateStringIdKey(stringId);
    }

    public void setKey(HibernateStringIdKey key) {
        if (Objects.isNull(key)) {
            this.stringId = null;
        }
        this.stringId = key.getStringId();
    }

    // -----------------------------------------------------------常规属性区-----------------------------------------------------------
    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
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
        return getClass().getSimpleName() + "(" +
                "stringId = " + stringId + ", " +
                "label = " + label + ", " +
                "description = " + description + ", " +
                "exampleParam = " + exampleParam + ")";
    }
}
