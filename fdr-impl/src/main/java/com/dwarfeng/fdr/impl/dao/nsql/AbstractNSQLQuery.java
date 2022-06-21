package com.dwarfeng.fdr.impl.dao.nsql;

import com.dwarfeng.fdr.impl.dao.NSQLQuery;
import org.springframework.lang.NonNull;

import java.sql.Connection;
import java.util.Objects;

/**
 * NSQLQuery 的抽象实现。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public abstract class AbstractNSQLQuery implements NSQLQuery {

    private final String type;

    public AbstractNSQLQuery(String type) {
        this.type = type;
    }

    @Override
    public boolean supportType(String type) {
        return Objects.equals(this.type, type);
    }

    @Override
    public void init(@NonNull Connection connection) {
    }

    @Override
    public String toString() {
        return "AbstractNSQLQuery{" +
                "type='" + type + '\'' +
                '}';
    }
}
