package com.dwarfeng.fdr.stack.structure;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * 数据。
 *
 * <p>
 * 该接口表示数据，包含了数据的点位主键、数据的值、数据的发生时间。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface Data {

    /**
     * 获取数据的点位主键。
     *
     * @return 数据的点位主键。
     */
    @Nonnull
    LongIdKey getPointKey();

    /**
     * 获取数据的值。
     *
     * @return 数据的值。
     */
    Object getValue();

    /**
     * 获取数据的发生时间。
     *
     * @return 数据的发生时间。
     */
    @Nonnull
    Date getHappenedDate();
}
