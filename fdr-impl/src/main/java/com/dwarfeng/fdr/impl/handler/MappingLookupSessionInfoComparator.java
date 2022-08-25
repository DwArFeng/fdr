package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.bean.dto.MappingLookupSessionInfo;

import java.util.Comparator;

/**
 * LongIdKey 比较器。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
class MappingLookupSessionInfoComparator implements Comparator<MappingLookupSessionInfo> {

    public static final MappingLookupSessionInfoComparator INSTANCE = new MappingLookupSessionInfoComparator();

    private MappingLookupSessionInfoComparator() {
    }

    @Override
    public int compare(MappingLookupSessionInfo o1, MappingLookupSessionInfo o2) {
        return Long.compare(o1.getKey().getLongId(), o2.getKey().getLongId());
    }
}
