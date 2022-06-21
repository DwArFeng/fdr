package com.dwarfeng.fdr.impl.dao.nsql;

/**
 * MySQL8本地SQL查询工具类。
 *
 * @author DwArFeng
 * @since 1.9.4
 */
public final class MySQL8NSQLQueryUtil {

    public static void forceIndex(StringBuilder sqlBuilder, String indexName) {
        sqlBuilder.append("FORCE INDEX (");
        sqlBuilder.append(indexName);
        sqlBuilder.append(") ");
    }

    private MySQL8NSQLQueryUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
