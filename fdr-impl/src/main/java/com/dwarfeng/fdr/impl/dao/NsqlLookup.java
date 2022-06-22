package com.dwarfeng.fdr.impl.dao;

import com.dwarfeng.subgrade.stack.exception.DaoException;
import org.springframework.lang.NonNull;

import java.sql.Connection;

/**
 * 本地SQL查询。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public interface NsqlLookup {

    boolean supportType(String type);

    /**
     * @since 1.9.4
     */
    void init(@NonNull Connection connection) throws DaoException;
}
