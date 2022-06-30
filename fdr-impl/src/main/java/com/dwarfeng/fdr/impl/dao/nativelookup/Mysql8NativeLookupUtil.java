package com.dwarfeng.fdr.impl.dao.nativelookup;

/**
 * MySQL8 本地工具类。
 *
 * @author DwArFeng
 * @since 1.10.0
 */
public final class Mysql8NativeLookupUtil {

    public static void forceIndex(StringBuilder sqlBuilder, String indexName) {
        sqlBuilder.append("FORCE INDEX (");
        sqlBuilder.append(indexName);
        sqlBuilder.append(") ");
    }

    private Mysql8NativeLookupUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
