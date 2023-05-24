package com.dwarfeng.fdr.sdk.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.sdk.util.Constraints;
import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.Bean;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;
import java.util.Objects;

/**
 * WebInput 数据点。
 *
 * @author DwArFeng
 * @since 0.0.1-alpha
 */
public class WebInputPoint implements Bean {

    private static final long serialVersionUID = 7514909111583453251L;

    public static Point toStackBean(WebInputPoint webInputPoint) {
        if (Objects.isNull(webInputPoint)) {
            return null;
        } else {
            return new Point(
                    WebInputLongIdKey.toStackBean(webInputPoint.getKey()),
                    webInputPoint.getName(),
                    webInputPoint.getRemark(),
                    webInputPoint.isNormalKeepEnabled(),
                    webInputPoint.isNormalPersistEnabled(),
                    webInputPoint.isFilteredKeepEnabled(),
                    webInputPoint.isFilteredPersistEnabled(),
                    webInputPoint.isTriggeredKeepEnabled(),
                    webInputPoint.isTriggeredPersistEnabled()
            );
        }
    }

    @JSONField(name = "key")
    @Valid
    @NotNull(groups = Default.class)
    private WebInputLongIdKey key;

    @JSONField(name = "name")
    @NotNull
    @NotEmpty
    @Length(max = Constraints.LENGTH_NAME)
    private String name;

    @JSONField(name = "remark")
    @Length(max = Constraints.LENGTH_REMARK)
    private String remark;

    @JSONField(name = "normal_keep_enabled")
    private boolean normalKeepEnabled;

    @JSONField(name = "normal_persist_enabled")
    private boolean normalPersistEnabled;

    @JSONField(name = "filtered_keep_enabled")
    private boolean filteredKeepEnabled;

    @JSONField(name = "filtered_persist_enabled")
    private boolean filteredPersistEnabled;

    @JSONField(name = "triggered_keep_enabled")
    private boolean triggeredKeepEnabled;

    @JSONField(name = "triggered_persist_enabled")
    private boolean triggeredPersistEnabled;

    public WebInputPoint() {
    }

    public WebInputLongIdKey getKey() {
        return key;
    }

    public void setKey(WebInputLongIdKey key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isNormalKeepEnabled() {
        return normalKeepEnabled;
    }

    public void setNormalKeepEnabled(boolean normalKeepEnabled) {
        this.normalKeepEnabled = normalKeepEnabled;
    }

    public boolean isNormalPersistEnabled() {
        return normalPersistEnabled;
    }

    public void setNormalPersistEnabled(boolean normalPersistEnabled) {
        this.normalPersistEnabled = normalPersistEnabled;
    }

    public boolean isFilteredKeepEnabled() {
        return filteredKeepEnabled;
    }

    public void setFilteredKeepEnabled(boolean filteredKeepEnabled) {
        this.filteredKeepEnabled = filteredKeepEnabled;
    }

    public boolean isFilteredPersistEnabled() {
        return filteredPersistEnabled;
    }

    public void setFilteredPersistEnabled(boolean filteredPersistEnabled) {
        this.filteredPersistEnabled = filteredPersistEnabled;
    }

    public boolean isTriggeredKeepEnabled() {
        return triggeredKeepEnabled;
    }

    public void setTriggeredKeepEnabled(boolean triggeredKeepEnabled) {
        this.triggeredKeepEnabled = triggeredKeepEnabled;
    }

    public boolean isTriggeredPersistEnabled() {
        return triggeredPersistEnabled;
    }

    public void setTriggeredPersistEnabled(boolean triggeredPersistEnabled) {
        this.triggeredPersistEnabled = triggeredPersistEnabled;
    }

    @Override
    public String toString() {
        return "WebInputPoint{" +
                "key=" + key +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", normalKeepEnabled=" + normalKeepEnabled +
                ", normalPersistEnabled=" + normalPersistEnabled +
                ", filteredKeepEnabled=" + filteredKeepEnabled +
                ", filteredPersistEnabled=" + filteredPersistEnabled +
                ", triggeredKeepEnabled=" + triggeredKeepEnabled +
                ", triggeredPersistEnabled=" + triggeredPersistEnabled +
                '}';
    }
}
