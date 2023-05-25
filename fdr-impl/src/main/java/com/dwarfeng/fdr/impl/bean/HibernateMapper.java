package com.dwarfeng.fdr.impl.bean;

import com.dwarfeng.fdr.impl.bean.entity.*;
import com.dwarfeng.fdr.impl.bean.key.HibernateQuerySupportKey;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.key.QuerySupportKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Hibernate Bean 映射器。
 *
 * @author DwArFeng
 * @since 1.11.0
 */
@Mapper
public interface HibernateMapper {

    HibernateLongIdKey longIdKeyToHibernate(LongIdKey longIdKey);

    @InheritInverseConfiguration
    LongIdKey longIdKeyFromHibernate(HibernateLongIdKey hibernateLongIdKey);

    HibernateStringIdKey stringIdKeyToHibernate(StringIdKey stringIdKey);

    @InheritInverseConfiguration
    StringIdKey stringIdKeyFromHibernate(HibernateStringIdKey hibernateStringIdKey);

    HibernateQuerySupportKey querySupportKeyToHibernate(QuerySupportKey querySupportKey);

    @InheritInverseConfiguration
    QuerySupportKey querySupportKeyFromHibernate(HibernateQuerySupportKey hibernateQuerySupportKey);

    @Mapping(target = "pointLongId", ignore = true)
    @Mapping(target = "point", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateFilterInfo filterInfoToHibernate(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromHibernate(HibernateFilterInfo hibernateFilterInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateFilterSupport filterSupportToHibernate(FilterSupport filterSupport);

    @InheritInverseConfiguration
    FilterSupport filterSupportFromHibernate(HibernateFilterSupport hibernateFilterSupport);

    @Mapping(target = "stringId", ignore = true)
    HibernateMapperSupport mapperSupportToHibernate(MapperSupport mapperSupport);

    @InheritInverseConfiguration
    MapperSupport mapperSupportFromHibernate(HibernateMapperSupport hibernateMapperSupport);

    @Mapping(target = "triggerInfos", ignore = true)
    @Mapping(target = "longId", ignore = true)
    @Mapping(target = "filterInfos", ignore = true)
    HibernatePoint pointToHibernate(Point point);

    @InheritInverseConfiguration
    Point pointFromHibernate(HibernatePoint hibernatePoint);

    @Mapping(target = "pointLongId", ignore = true)
    @Mapping(target = "point", ignore = true)
    @Mapping(target = "longId", ignore = true)
    HibernateTriggerInfo triggerInfoToHibernate(TriggerInfo triggerInfo);

    @InheritInverseConfiguration
    TriggerInfo triggerInfoFromHibernate(HibernateTriggerInfo hibernateTriggerInfo);

    @Mapping(target = "stringId", ignore = true)
    HibernateTriggerSupport triggerSupportToHibernate(TriggerSupport triggerSupport);

    @InheritInverseConfiguration
    TriggerSupport triggerSupportFromHibernate(HibernateTriggerSupport hibernateTriggerSupport);

    HibernateQuerySupport querySupportToHibernate(QuerySupport querySupport);

    @InheritInverseConfiguration
    QuerySupport querySupportFromHibernate(HibernateQuerySupport hibernateQuerySupport);
}
