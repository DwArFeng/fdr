package com.dwarfeng.fdr.impl.handler.source.mock;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 模拟数据配置项。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class MockSourceDataConfigItem implements Bean {

    private static final long serialVersionUID = 212897411936848123L;

    @JSONField(name = "point_id")
    private Long pointId;

    @JSONField(name = "point_type")
    private String pointType;

    public MockSourceDataConfigItem() {
    }

    public MockSourceDataConfigItem(Long pointId, String pointType) {
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
        return "MockSourceDataConfigItem{" +
                "pointId=" + pointId +
                ", pointType='" + pointType + '\'' +
                '}';
    }
}
