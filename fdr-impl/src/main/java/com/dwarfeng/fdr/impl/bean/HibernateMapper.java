package com.dwarfeng.fdr.impl.bean;

import com.dwarfeng.fdr.impl.bean.entity.*;
import com.dwarfeng.fdr.stack.bean.entity.*;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateLongIdKey;
import com.dwarfeng.subgrade.sdk.bean.key.HibernateStringIdKey;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

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

    HibernateFilteredValue filteredValueToHibernate(FilteredValue filteredValue);

    @InheritInverseConfiguration
    FilteredValue filteredValueFromHibernate(HibernateFilteredValue hibernateFilteredValue);

    HibernateFilterInfo filterInfoToHibernate(FilterInfo filterInfo);

    @InheritInverseConfiguration
    FilterInfo filterInfoFromHibernate(HibernateFilterInfo hibernateFilterInfo);

    HibernateFilterSupport filterSupportToHibernate(FilterSupport filterSupport);

    @InheritInverseConfiguration
    FilterSupport filterSupportFromHibernate(HibernateFilterSupport hibernateFilterSupport);

    HibernateMapperSupport mapperSupportToHibernate(MapperSupport mapperSupport);

    @InheritInverseConfiguration
    MapperSupport mapperSupportFromHibernate(HibernateMapperSupport hibernateMapperSupport);

    HibernatePersistenceValue persistenceValueToHibernate(PersistenceValue persistenceValue);

    @InheritInverseConfiguration
    PersistenceValue persistenceValueFromHibernate(HibernatePersistenceValue hibernatePersistenceValue);

    HibernatePoint pointToHibernate(Point point);

    @InheritInverseConfiguration
    Point pointFromHibernate(HibernatePoint hibernatePoint);

    HibernateTriggeredValue triggeredValueToHibernate(TriggeredValue triggeredValue);

    @InheritInverseConfiguration
    TriggeredValue triggeredValueFromHibernate(HibernateTriggeredValue hibernateTriggeredValue);

    HibernateTriggerInfo triggerInfoToHibernate(TriggerInfo triggerInfo);

    @InheritInverseConfiguration
    TriggerInfo triggerInfoFromHibernate(HibernateTriggerInfo hibernateTriggerInfo);

    HibernateTriggerSupport triggerSupportToHibernate(TriggerSupport triggerSupport);

    @InheritInverseConfiguration
    TriggerSupport triggerSupportFromHibernate(HibernateTriggerSupport hibernateTriggerSupport);
}
