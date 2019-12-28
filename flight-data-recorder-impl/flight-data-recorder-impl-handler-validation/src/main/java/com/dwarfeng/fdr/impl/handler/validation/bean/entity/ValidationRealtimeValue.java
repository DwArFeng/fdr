package com.dwarfeng.fdr.impl.handler.validation.bean.entity;

import com.dwarfeng.fdr.impl.handler.validation.bean.key.ValidationUuidKey;
import com.dwarfeng.fdr.sdk.util.Constraints;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 实时数据。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class ValidationRealtimeValue implements Serializable {

    private static final long serialVersionUID = -8543135755592442070L;

    @NotNull
    @Valid
    private ValidationUuidKey key;

    @NotNull
    private Date happenedDate;

    @Length(max = Constraints.LENGTH_VALUE)
    private String value;

    public ValidationRealtimeValue() {
    }

    public ValidationRealtimeValue(ValidationUuidKey key, ValidationUuidKey pointKey, Date happenedDate, String value) {
        this.key = key;
        this.happenedDate = happenedDate;
        this.value = value;
    }

    public ValidationUuidKey getKey() {
        return key;
    }

    public void setKey(ValidationUuidKey key) {
        this.key = key;
    }

    public Date getHappenedDate() {
        return happenedDate;
    }

    public void setHappenedDate(Date happenedDate) {
        this.happenedDate = happenedDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ValidationRealtimeValue{" +
                "key=" + key +
                ", happenedDate=" + happenedDate +
                ", value='" + value + '\'' +
                '}';
    }
}