package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.nativelookup;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.HibernateBridgeFilteredData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeFilteredDataMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.nativelookup.AbstractDialectNativeLookup;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Hibernate 桥接器被过滤数据本地查询。
 *
 * @author DwArFeng
 * @since 2.2.0
 */
public abstract class HibernateBridgeFilteredDataNativeLookup extends
        AbstractDialectNativeLookup<HibernateBridgeFilteredData> {

    public HibernateBridgeFilteredDataNativeLookup(String supportDialect) {
        super(supportDialect);
    }

    /*
     * 在 HibernateBridge 中，所有的数据查询业务均不需要调用该方法。
     * 因此为了简化实现，该方法直接抛出异常。
     */
    @Override
    public List<HibernateBridgeFilteredData> lookupEntity(Connection connection, String preset, Object[] args) {
        throw new IllegalArgumentException("不支持不带分页信息的查询");
    }

    @Override
    public boolean supportPreset(String preset) {
        switch (preset) {
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE:
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN:
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE:
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN:
                return true;
            default:
                return false;
        }
    }

    @Override
    public List<HibernateBridgeFilteredData> lookupEntity(
            Connection connection, String preset, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        switch (preset) {
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE:
                return childForPointBetweenCloseClose(connection, args, pagingInfo);
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN:
                return childForPointBetweenCloseOpen(connection, args, pagingInfo);
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE:
                return childForPointBetweenOpenClose(connection, args, pagingInfo);
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN:
                return childForPointBetweenOpenOpen(connection, args, pagingInfo);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeFilteredData> childForPointBetweenCloseClose(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 查询数据。
        return lookupChildForPointBetweenCloseClose(connection, pointLongId, startDate, endDate, offset, limit);
    }

    /**
     * 查询指定点位在指定时间范围内的数据。
     *
     * <p>
     * 该方法查询的数据的时间范围为 [startDate, endDate]，即包含起始时间，包含结束时间。
     *
     * <p>
     * 返回的数据从 offset（以 0 开始） 开始，最多返回 limit 条数据。
     *
     * @param connection  数据库连接。
     * @param pointLongId 点位 ID。
     * @param startDate   起始时间。
     * @param endDate     结束时间。
     * @param offset      偏移量。
     * @param limit       限制量。
     * @return 查询得到的数据。
     * @throws SQLException SQL 异常。
     */
    protected abstract List<HibernateBridgeFilteredData> lookupChildForPointBetweenCloseClose(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException;

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeFilteredData> childForPointBetweenCloseOpen(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 查询数据。
        return lookupChildForPointBetweenCloseOpen(connection, pointLongId, startDate, endDate, offset, limit);
    }

    /**
     * 查询指定点位在指定时间范围内的数据。
     *
     * <p>
     * 该方法查询的数据的时间范围为 [startDate, endDate)，即包含起始时间，不包含结束时间。
     *
     * <p>
     * 返回的数据从 offset（以 0 开始） 开始，最多返回 limit 条数据。
     *
     * @param connection  数据库连接。
     * @param pointLongId 点位 ID。
     * @param startDate   起始时间。
     * @param endDate     结束时间。
     * @param offset      偏移量。
     * @param limit       限制量。
     * @return 查询得到的数据。
     * @throws SQLException SQL 异常。
     */
    protected abstract List<HibernateBridgeFilteredData> lookupChildForPointBetweenCloseOpen(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException;

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeFilteredData> childForPointBetweenOpenClose(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 查询数据。
        return lookupChildForPointBetweenOpenClose(connection, pointLongId, startDate, endDate, offset, limit);
    }

    /**
     * 查询指定点位在指定时间范围内的数据。
     *
     * <p>
     * 该方法查询的数据的时间范围为 (startDate, endDate]，即不包含起始时间，包含结束时间。
     *
     * <p>
     * 返回的数据从 offset（以 0 开始） 开始，最多返回 limit 条数据。
     *
     * @param connection  数据库连接。
     * @param pointLongId 点位 ID。
     * @param startDate   起始时间。
     * @param endDate     结束时间。
     * @param offset      偏移量。
     * @param limit       限制量。
     * @return 查询得到的数据。
     * @throws SQLException SQL 异常。
     */
    protected abstract List<HibernateBridgeFilteredData> lookupChildForPointBetweenOpenClose(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException;

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeFilteredData> childForPointBetweenOpenOpen(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 查询数据。
        return lookupChildForPointBetweenOpenOpen(connection, pointLongId, startDate, endDate, offset, limit);
    }

    /**
     * 查询指定点位在指定时间范围内的数据。
     *
     * <p>
     * 该方法查询的数据的时间范围为 (startDate, endDate)，即不包含起始时间，不包含结束时间。
     *
     * <p>
     * 返回的数据从 offset（以 0 开始） 开始，最多返回 limit 条数据。
     *
     * @param connection  数据库连接。
     * @param pointLongId 点位 ID。
     * @param startDate   起始时间。
     * @param endDate     结束时间。
     * @param offset      偏移量。
     * @param limit       限制量。
     * @return 查询得到的数据。
     * @throws SQLException SQL 异常。
     */
    protected abstract List<HibernateBridgeFilteredData> lookupChildForPointBetweenOpenOpen(
            Connection connection, long pointLongId, Date startDate, Date endDate, int offset, int limit
    ) throws SQLException;

    @Override
    public int lookupCount(Connection connection, String preset, Object[] args) throws SQLException {
        switch (preset) {
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE:
                return childForPointBetweenCloseCloseCount(connection, args);
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN:
                return childForPointBetweenCloseOpenCount(connection, args);
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE:
                return childForPointBetweenOpenCloseCount(connection, args);
            case HibernateBridgeFilteredDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN:
                return childForPointBetweenOpenOpenCount(connection, args);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private int childForPointBetweenCloseCloseCount(Connection connection, Object[] args) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 查询数据。
        return lookupChildForPointBetweenCloseCloseCount(connection, pointLongId, startDate, endDate);
    }

    /**
     * 查询指定点位在指定时间范围内的数据数量。
     *
     * <p>
     * 该方法查询的数据的时间范围为 [startDate, endDate]，即包含起始时间，包含结束时间。
     *
     * @param connection  数据库连接。
     * @param pointLongId 点位 ID。
     * @param startDate   起始时间。
     * @param endDate     结束时间。
     * @return 查询得到的数据数量。
     * @throws SQLException SQL 异常。
     */
    protected abstract int lookupChildForPointBetweenCloseCloseCount(
            Connection connection, long pointLongId, Date startDate, Date endDate
    ) throws SQLException;

    private int childForPointBetweenCloseOpenCount(Connection connection, Object[] args) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 查询数据。
        return lookupChildForPointBetweenCloseOpenCount(connection, pointLongId, startDate, endDate);
    }

    /**
     * 查询指定点位在指定时间范围内的数据数量。
     *
     * <p>
     * 该方法查询的数据的时间范围为 [startDate, endDate)，即包含起始时间，不包含结束时间。
     *
     * @param connection  数据库连接。
     * @param pointLongId 点位 ID。
     * @param startDate   起始时间。
     * @param endDate     结束时间。
     * @return 查询得到的数据数量。
     * @throws SQLException SQL 异常。
     */
    protected abstract int lookupChildForPointBetweenCloseOpenCount(
            Connection connection, long pointLongId, Date startDate, Date endDate
    ) throws SQLException;

    private int childForPointBetweenOpenCloseCount(Connection connection, Object[] args) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 查询数据。
        return lookupChildForPointBetweenOpenCloseCount(connection, pointLongId, startDate, endDate);
    }

    /**
     * 查询指定点位在指定时间范围内的数据数量。
     *
     * <p>
     * 该方法查询的数据的时间范围为 (startDate, endDate]，即不包含起始时间，包含结束时间。
     *
     * @param connection  数据库连接。
     * @param pointLongId 点位 ID。
     * @param startDate   起始时间。
     * @param endDate     结束时间。
     * @return 查询得到的数据数量。
     * @throws SQLException SQL 异常。
     */
    protected abstract int lookupChildForPointBetweenOpenCloseCount(
            Connection connection, long pointLongId, Date startDate, Date endDate
    ) throws SQLException;

    private int childForPointBetweenOpenOpenCount(Connection connection, Object[] args) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 查询数据。
        return lookupChildForPointBetweenOpenOpenCount(connection, pointLongId, startDate, endDate);
    }

    /**
     * 查询指定点位在指定时间范围内的数据数量。
     *
     * <p>
     * 该方法查询的数据的时间范围为 (startDate, endDate)，即不包含起始时间，不包含结束时间。
     *
     * @param connection  数据库连接。
     * @param pointLongId 点位 ID。
     * @param startDate   起始时间。
     * @param endDate     结束时间。
     * @return 查询得到的数据数量。
     * @throws SQLException SQL 异常。
     */
    protected abstract int lookupChildForPointBetweenOpenOpenCount(
            Connection connection, long pointLongId, Date startDate, Date endDate
    ) throws SQLException;

    @Override
    public String toString() {
        return "HibernateBridgeFilteredDataNativeLookup{" +
                "supportDialect='" + supportDialect + '\'' +
                '}';
    }
}
