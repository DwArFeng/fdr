package com.dwarfeng.fdr.impl.dao.nsql;

import com.dwarfeng.fdr.impl.dao.NsqlLookup;
import org.springframework.lang.NonNull;

import java.sql.Connection;
import java.util.Objects;

/**
 * NsqlLookup 的抽象实现。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public abstract class AbstractNsqlLookup implements NsqlLookup {

    private final String type;

    public AbstractNsqlLookup(String type) {
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
        return "AbstractNsqlLookup{" +
                "type='" + type + '\'' +
                '}';
    }
}
