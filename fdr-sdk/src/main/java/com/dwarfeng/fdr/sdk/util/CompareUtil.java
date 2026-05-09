package com.dwarfeng.fdr.sdk.util;

import com.dwarfeng.fdr.stack.handler.Mapper;
import com.dwarfeng.fdr.stack.struct.Data;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.time.Instant;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Function;

/**
 * 比较工具类。
 *
 * <p>
 * 该工具类提供了一些比较器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public final class CompareUtil {

    // region 主键比较

    /**
     * 长整型主键比较器，按照主键的升序进行比较。
     */
    public static final Comparator<LongIdKey> LONG_ID_KEY_ASC_COMPARATOR =
            Comparator.comparing(LongIdKey::getLongId);

    /**
     * 长整型主键比较器，按照主键的降序进行比较。
     */
    public static final Comparator<LongIdKey> LONG_ID_KEY_DESC_COMPARATOR =
            LONG_ID_KEY_ASC_COMPARATOR.reversed();

    // endregion

    // region 日期比较

    /**
     * 日期比较器，按照日期的升序进行比较。
     *
     * <p>
     * 该比较器精度较低，无法区分同一毫秒内不同纳秒偏移的日期，因此不建议使用。
     * 使用 {@link #INSTANT_ASC_COMPARATOR} 代替。
     *
     * @see #INSTANT_ASC_COMPARATOR
     * @deprecated 该比较器精度较低，使用 {@link #INSTANT_ASC_COMPARATOR} 代替。
     */
    @Deprecated
    public static final Comparator<Date> DATE_ASC_COMPARATOR = Comparator.comparing(Function.identity());

    /**
     * 日期比较器，按照日期的降序进行比较。
     *
     * <p>
     * 该比较器精度较低，无法区分同一毫秒内不同纳秒偏移的日期，因此不建议使用。
     * 使用 {@link #INSTANT_DESC_COMPARATOR} 代替。
     *
     * @see #INSTANT_DESC_COMPARATOR
     * @deprecated 该比较器精度较低，使用 {@link #INSTANT_DESC_COMPARATOR} 代替。
     */
    @Deprecated
    public static final Comparator<Date> DATE_DESC_COMPARATOR = DATE_ASC_COMPARATOR.reversed();

    /**
     * Instant 比较器，按照 Instant 的升序进行比较。
     *
     * @since 3.0.0
     */
    public static final Comparator<Instant> INSTANT_ASC_COMPARATOR = Comparator.comparing(Function.identity());

    /**
     * Instant 比较器，按照 Instant 的降序进行比较。
     *
     * @since 3.0.0
     */
    public static final Comparator<Instant> INSTANT_DESC_COMPARATOR = INSTANT_ASC_COMPARATOR.reversed();

    // endregion

    // region 数据比较

    /**
     * 数据比较器，按照数据的默认顺序进行比较。
     */
    public static final Comparator<Data> DATA_DEFAULT_COMPARATOR =
            Comparator.comparing(Data::getPointKey, LONG_ID_KEY_ASC_COMPARATOR)
                    .thenComparing(DataUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 数据比较器，按照数据的发生日期的升序进行比较。
     *
     * <p>
     * 该比较器精度较低，无法区分同一毫秒内不同纳秒偏移的数据，因此不建议使用。
     * 使用 {@link #DATA_HAPPENED_INSTANT_ASC_COMPARATOR} 代替。
     *
     * @see #DATA_HAPPENED_INSTANT_ASC_COMPARATOR
     * @deprecated 该比较器精度较低，使用 {@link #DATA_HAPPENED_INSTANT_ASC_COMPARATOR} 代替。
     */
    @Deprecated
    public static final Comparator<Data> DATA_HAPPENED_DATE_ASC_COMPARATOR =
            Comparator.comparing(Data::getHappenedDate, DATE_ASC_COMPARATOR);

    /**
     * 数据比较器，按照数据的发生日期的降序进行比较。
     *
     * <p>
     * 该比较器精度较低，无法区分同一毫秒内不同纳秒偏移的数据，因此不建议使用。
     * 使用 {@link #DATA_HAPPENED_INSTANT_DESC_COMPARATOR} 代替。
     *
     * @see #DATA_HAPPENED_INSTANT_DESC_COMPARATOR
     * @deprecated 该比较器精度较低，使用 {@link #DATA_HAPPENED_INSTANT_DESC_COMPARATOR} 代替。
     */
    @Deprecated
    public static final Comparator<Data> DATA_HAPPENED_DATE_DESC_COMPARATOR =
            Comparator.comparing(Data::getHappenedDate, DATE_DESC_COMPARATOR);

    /**
     * 数据比较器，按照数据的发生 Instant 的升序进行比较。
     *
     * @since 3.0.0
     */
    public static final Comparator<Data> DATA_HAPPENED_INSTANT_ASC_COMPARATOR =
            Comparator.comparing(DataUtil::getHappenedInstant, INSTANT_ASC_COMPARATOR);

    /**
     * 数据比较器，按照数据的发生 Instant 的降序进行比较。
     *
     * @since 3.0.0
     */
    public static final Comparator<Data> DATA_HAPPENED_INSTANT_DESC_COMPARATOR =
            Comparator.comparing(DataUtil::getHappenedInstant, INSTANT_DESC_COMPARATOR);

    // endregion

    // region 序列比较

    /**
     * 序列比较器，按照序列的默认顺序进行比较。
     */
    public static final Comparator<Mapper.Sequence> SEQUENCE_DEFAULT_COMPARATOR =
            Comparator.comparing(Mapper.Sequence::getPointKey, LONG_ID_KEY_ASC_COMPARATOR);

    /**
     * 序列比较器，按照序列的数据点主键的升序进行比较。
     */
    public static final Comparator<Mapper.Sequence> SEQUENCE_POINT_KEY_ASC_COMPARATOR =
            Comparator.comparing(Mapper.Sequence::getPointKey, LONG_ID_KEY_ASC_COMPARATOR);

    /**
     * 序列比较器，按照序列的数据点主键的降序进行比较。
     */
    public static final Comparator<Mapper.Sequence> SEQUENCE_POINT_KEY_DESC_COMPARATOR =
            Comparator.comparing(Mapper.Sequence::getPointKey, LONG_ID_KEY_DESC_COMPARATOR);

    // endregion

    private CompareUtil() {
        throw new IllegalStateException("禁止外部实例化");
    }
}
