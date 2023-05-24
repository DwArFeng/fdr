package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.subgrade.stack.bean.Bean;

/**
 * 模拟桥接数据配置项。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class MockBridgeDataConfigItem implements Bean {

    private static final long serialVersionUID = 3382304026907473112L;

    @JSONField(name = "point_id")
    private Long pointId;

    @JSONField(name = "point_type")
    private String pointType;

    public MockBridgeDataConfigItem() {
    }

    public MockBridgeDataConfigItem(Long pointId, String pointType) {
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
