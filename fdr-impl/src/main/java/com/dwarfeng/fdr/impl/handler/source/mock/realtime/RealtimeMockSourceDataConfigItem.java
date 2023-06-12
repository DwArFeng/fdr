package com.dwarfeng.fdr.impl.handler.source.mock.realtime;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 实时模拟数据配置项。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RealtimeMockSourceDataConfigItem implements Bean {

    private static final long serialVersionUID = -721550085312004643L;

    @JSONField(name = "point_id")
    private Long pointId;

    @JSONField(name = "point_type")
    private String pointType;

    public RealtimeMockSourceDataConfigItem() {
    }

    public RealtimeMockSourceDataConfigItem(Long pointId, String pointType) {
        this.pointId = pointId;
        this.pointType = pointType;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    @Override
    public String toString() {
        return "RealtimeMockSourceDataConfigItem{" +
                "pointId=" + pointId +
                ", pointType='" + pointType + '\'' +
                '}';
    }
}
