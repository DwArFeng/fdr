package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.MapperSupport;
import com.dwarfeng.subgrade.stack.bean.key.StringIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 映射器支持数据访问层。
 *
 * @author DwArFeng
 * @since 1.6.0
 */
public interface MapperSupportDao extends BatchBaseDao<StringIdKey, MapperSupport>, EntireLookupDao<MapperSupport>,
        PresetLookupDao<MapperSupport> {
}
