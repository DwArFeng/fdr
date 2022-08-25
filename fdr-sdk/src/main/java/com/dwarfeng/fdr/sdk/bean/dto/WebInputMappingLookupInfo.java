package com.dwarfeng.fdr.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.fdr.stack.bean.dto.MappingLookupInfo;
import com.dwarfeng.subgrade.sdk.bean.key.WebInputLongIdKey;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * WebInput 映射查询信息。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public class WebInputMappingLookupInfo implements Dto {

    private static final long serialVersionUID = 5914080138648696701L;

    public static MappingLookupInfo toStackBean(WebInputMappingLookupInfo webInputMappingLookupInfo) {
        if (Objects.isNull(webInputMappingLookupInfo)) {
            return null;
        } else {
            return new MappingLookupInfo(
                    webInputMappingLookupInfo.getMapperType(),
                    WebInputLongIdKey.toStackBean(webInputMappingLookupInfo.getPointKey()),
                    webInputMappingLookupInfo.getStartDate(), webInputMappingLookupInfo.getEndDate(),
                    webInputMappingLookupInfo.mapperArgs
            );
        }
    }

    @JSONField(name = "mapper_type")
    @NotNull
    @NotEmpty
    private String mapperType;

    @JSONField(name = "point_key")
    @NotNull
    @Valid
    private WebInputLongIdKey pointKey;

    @JSONField(name = "start_date")
    @NotNull
    private Date startDate;

    @JSONField(name = "end_date")
    @NotNull
    private Date endDate;

    @JSONField(name = "mapper_args")
    private Object[] mapperArgs;

    public WebInputMappingLookupInfo() {
    }

    public String getMapperType() {
        return mapperType;
    }

    public void setMapperType(String mapperType) {
        this.mapperType = mapperType;
    }

    public WebInputLongIdKey getPointKey() {
        return pointKey;
    }

    public void setPointKey(WebInputLongIdKey pointKey) {
        this.pointKey = pointKey;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Object[] getMapperArgs() {
        return mapperArgs;
    }

    public void setMapperArgs(Object[] mapperArgs) {
        this.mapperArgs = mapperArgs;
    }

    @Override
    public String toString() {
        return "WebInputMappingLookupInfo{" +
                "mapperType='" + mapperType + '\'' +
                ", pointKey=" + pointKey +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", mapperArgs=" + Arrays.toString(mapperArgs) +
                '}';
    }
}
