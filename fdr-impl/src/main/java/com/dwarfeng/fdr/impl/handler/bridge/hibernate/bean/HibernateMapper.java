package com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.*;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Hibernate Bean 映射器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Mapper
public interface HibernateMapper {

    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "pointLongId", ignore = true)
    HibernateHibernateBridgeNormalData hibernateBridgeNormalDataToHibernate(
            HibernateBridgeNormalData hibernateBridgeNormalData
    );

    @InheritInverseConfiguration
    HibernateBridgeNormalData hibernateBridgeNormalDataFromHibernate(
            HibernateHibernateBridgeNormalData hibernateHibernateBridgeNormalData
    );

    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "pointLongId", ignore = true)
    @Mapping(target = "filterLongId", ignore = true)
    HibernateHibernateBridgeFilteredData hibernateBridgeFilteredDataToHibernate(
            HibernateBridgeFilteredData hibernateBridgeFilteredData
    );

    @InheritInverseConfiguration
    HibernateBridgeFilteredData hibernateBridgeFilteredDataFromHibernate(
            HibernateHibernateBridgeFilteredData hibernateHibernateBridgeFilteredData
    );

    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "pointLongId", ignore = true)
    @Mapping(target = "triggerLongId", ignore = true)
    HibernateHibernateBridgeTriggeredData hibernateBridgeTriggeredDataToHibernate(
            HibernateBridgeTriggeredData hibernateBridgeTriggeredData
    );

    @InheritInverseConfiguration
    HibernateBridgeTriggeredData hibernateBridgeTriggeredDataFromHibernate(
            HibernateHibernateBridgeTriggeredData hibernateHibernateBridgeTriggeredData
    );
}
