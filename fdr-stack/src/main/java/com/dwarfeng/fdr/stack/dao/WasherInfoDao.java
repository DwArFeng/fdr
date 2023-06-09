package com.dwarfeng.fdr.stack.dao;

import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.dao.BatchBaseDao;
import com.dwarfeng.subgrade.stack.dao.EntireLookupDao;
import com.dwarfeng.subgrade.stack.dao.PresetLookupDao;

/**
 * 清洗器信息数据访问层。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface WasherInfoDao extends BatchBaseDao<LongIdKey, WasherInfo>, EntireLookupDao<WasherInfo>,
        PresetLookupDao<WasherInfo> {
}
