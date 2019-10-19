package com.dwarfeng.fdr.impl.cache.redis.bean.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.entity.TriggerSetting;
import com.dwarfeng.fdr.stack.bean.key.NameKey;

public class TriggerSettingImpl implements TriggerSetting {

    private static final long serialVersionUID = -3656468063489794952L;

    @JSONField(name = "key", ordinal = 1)
    private NameKey key;

    @JSONField(name = "trigger_data", ordinal = 2)
    private String triggerData;

    @JSONField(name = "remark", ordinal = 3)
    private String remark;

    public TriggerSettingImpl() {
    }

    public TriggerSettingImpl(NameKey key, String triggerData, String remark) {
        this.key = key;
        this.triggerData = triggerData;
        this.remark = remark;
    }

    @Override
    public NameKey getKey() {
        return key;
    }

    public void setKey(NameKey key) {
        this.key = key;
    }

    @Override
    public String getTriggerData() {
        return triggerData;
    }

    public void setTriggerData(String triggerData) {
        this.triggerData = triggerData;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TriggerSettingImpl{" +
                "key=" + key +
                ", triggerData='" + triggerData + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
