package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.dutil.basic.time.TimeUtil;
import com.dwarfeng.fdr.stack.struct.Data;

import java.time.Instant;
import java.util.Objects;

/**
 * 数据时间工具类。
 *
 * @author DwArFeng
 * @since 3.0.0
 */
public final class DataUtil {

    /**
     * 获取数据的发生瞬时时间。
     *
     * @param data 数据。
     * @return 数据的发生瞬时时间。
     */
    public static Instant getHappenedInstant(Data data) {
        Objects.requireNonNull(data, "data");
        return TimeUtil.toInstant(data.getHappenedDate(), data.getHappenedDateNanoOffset());
    }

    private DataUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
