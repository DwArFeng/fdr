package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.fdr.stack.bean.entity.TriggerInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * WebInput 触发器信息。
 *
 * @author DwArFeng
 * @since 1.1.0
 */
public class WebInputTriggerInfo implements Bean {

    private static final long serialVersionUID = -3611862529027248181L;

    public static TriggerInfo toStackBean(WebInputTriggerInfo webInputTriggerInfo) {
        return new TriggerInfo(
                WebInputLongIdKey.toStackBean(webInputTriggerInfo.getKey()),
                WebInputLongIdKey.toStackBean(webInputTriggerInfo.getPointKey()),
                webInputTriggerInfo.getIndex(),
                webInputTriggerInfo.isEnabled(),
                webInputTriggerInfo.getType(),
                webInputTriggerInfo.getParam(),
                webInputTriggerInfo.getRemark()
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

    public WebInputTriggerInfo() {
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
        return "WebInputTriggerInfo{" +
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
