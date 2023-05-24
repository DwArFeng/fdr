package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.fdr.stack.bean.entity.FilterInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * WebInput 过滤器信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputFilterInfo implements Bean {

    private static final long serialVersionUID = 3686959907670275934L;

    public static FilterInfo toStackBean(WebInputFilterInfo webInputFilterInfo) {
        return new FilterInfo(
                WebInputLongIdKey.toStackBean(webInputFilterInfo.getKey()),
                WebInputLongIdKey.toStackBean(webInputFilterInfo.getPointKey()),
                webInputFilterInfo.getIndex(),
                webInputFilterInfo.isEnabled(),
                webInputFilterInfo.getType(),
                webInputFilterInfo.getParam(),
                webInputFilterInfo.getRemark()
        );
    }

    @JSONField(name = "key")
    @Valid
    @NotNull(groups = Default.class)
    private WebInputLongIdKey key;

    @JSONField(name = "point_key")
    @Valid
    private WebInputLongIdKey pointKey;

    @JSONField(name = "index")
    private int index;

    @JSONField(name = "enabled")
    private boolean enabled;

    @JSONField(name = "type")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_TYPE)
    private String type;

    @JSONField(name = "param")
    private String param;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    public WebInputFilterInfo() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public WebInputLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(WebInputLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "WebInputFilterInfo{" +
                "key=" + key +
                ", pointKey=" + pointKey +
                ", index=" + index +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", param='" + param + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
