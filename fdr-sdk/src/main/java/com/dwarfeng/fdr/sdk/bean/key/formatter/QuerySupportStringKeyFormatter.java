package com.dwarfeng.fdr.sdk.bean.key.formatter;

import com.dwarfeng.fdr.stack.bean.key.QuerySupportKey;
import com.dwarfeng.subgrade.sdk.common.Constants;
import com.dwarfeng.subgrade.sdk.redis.formatter.StringKeyFormatter;

import java.util.Objects;

/**
 * QuerySupportKey 的文本格式化转换器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class QuerySupportStringKeyFormatter implements StringKeyFormatter<QuerySupportKey> {

    private String prefix;

    public QuerySupportStringKeyFormatter(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String format(QuerySupportKey key) {
        Objects.requireNonNull(key);
        return prefix + key.getCategory() + "_" + key.getPreset();
    }

    @Override
    public String generalFormat() {
        return prefix + Constants.REDIS_KEY_WILDCARD_CHARACTER;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String toString() {
        return "QuerySupportStringKeyFormatter{" +
                "prefix='" + prefix + '\'' +
                '}';
    }
}