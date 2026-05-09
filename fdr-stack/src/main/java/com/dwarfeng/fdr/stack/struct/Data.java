package com.dwarfeng.fdr.stack.struct;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import javax.annotation.Nonnull;
import java.util.Date;

/**
 * 数据。
 *
 * <p>
 * 该接口表示数据，包含了数据的点位主键、数据的值、数据的发生时间，以及发生时间在毫秒内的纳秒偏移。
 *
 * <p>
 * 在 3.0.0 版本中，新增了数据发生时间在毫秒内的纳秒偏移。
 * 数据发生时间由 {@link #getHappenedDate()} 和 {@link #getHappenedDateNanoOffset()} 共同唯一确定。
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

    /**
     * 获取数据发生时间在毫秒内的纳秒偏移。
     *
     * <p>
     * 偏移量与 {@link #getHappenedDate()} 共同唯一确定数据的真实发生时间。
     *
     * @return 数据发生时间在毫秒内的纳秒偏移。
     * @since 3.0.0
     */
    int getHappenedDateNanoOffset();
}
