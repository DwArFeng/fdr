package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.nativelookup;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.bean.entity.HibernateBridgeNormalData;
import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeNormalDataMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.nativelookup.AbstractDialectNativeLookup;
import com.dwarfeng.subgrade.stack.bean.dto.PagingInfo;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class HibernateBridgeNormalDataNativeLookup extends AbstractDialectNativeLookup<HibernateBridgeNormalData> {

    public static final String SUPPORT_TYPE = "org.hibernate.dialect.MySQL8Dialect";

    public HibernateBridgeNormalDataNativeLookup() {
        super(SUPPORT_TYPE);
    }

    @Override
    public boolean supportPreset(String preset) {
        switch (preset) {
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE:
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN:
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE:
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN:
                return true;
            default:
                return false;
        }
    }

    @Override
    public List<HibernateBridgeNormalData> lookupEntity(Connection connection, String preset, Object[] args) {
        throw new IllegalArgumentException("不支持不带分页信息的查询");
    }

    @Override
    public List<HibernateBridgeNormalData> lookupEntity(
            Connection connection, String preset, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        switch (preset) {
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE:
                return childForPointBetweenCloseClose(connection, args, pagingInfo);
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN:
                return childForPointBetweenCloseOpen(connection, args, pagingInfo);
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE:
                return childForPointBetweenOpenClose(connection, args, pagingInfo);
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN:
                return childForPointBetweenOpenOpen(connection, args, pagingInfo);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeNormalData> childForPointBetweenCloseClose(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl",
                "id", "point_id", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseClose(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeNormalData> HibernateBridgeNormalDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeNormalDatas.add(new HibernateBridgeNormalData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    new Date(resultSet.getTimestamp(4).getTime())
            ));
        }
        return HibernateBridgeNormalDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeNormalData> childForPointBetweenCloseOpen(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl",
                "id", "point_id", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeNormalData> HibernateBridgeNormalDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeNormalDatas.add(new HibernateBridgeNormalData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    new Date(resultSet.getTimestamp(4).getTime())
            ));
        }
        return HibernateBridgeNormalDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeNormalData> childForPointBetweenOpenClose(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl",
                "id", "point_id", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenClose(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeNormalData> HibernateBridgeNormalDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeNormalDatas.add(new HibernateBridgeNormalData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    new Date(resultSet.getTimestamp(4).getTime())
            ));
        }
        return HibernateBridgeNormalDatas;
    }

    @SuppressWarnings("DuplicatedCode")
    private List<HibernateBridgeNormalData> childForPointBetweenOpenOpen(
            Connection connection, Object[] args, PagingInfo pagingInfo
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];
        int offset = pagingInfo.getPage() * pagingInfo.getRows();
        int limit = pagingInfo.getRows();

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectColumnsFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl",
                "id", "point_id", "value", "happened_date"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenOpen(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.orderByHappenedDateAsc(sqlBuilder, "tbl", "happened_date");
        Mysql8NativeLookupUtil.limit(sqlBuilder);

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));
        preparedStatement.setInt(4, offset);
        preparedStatement.setInt(5, limit);

        // 执行查询，构建结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        List<HibernateBridgeNormalData> HibernateBridgeNormalDatas = new ArrayList<>();
        while (resultSet.next()) {
            HibernateBridgeNormalDatas.add(new HibernateBridgeNormalData(
                    new LongIdKey(resultSet.getLong(1)),
                    new LongIdKey(resultSet.getLong(2)),
                    resultSet.getString(3),
                    new Date(resultSet.getTimestamp(4).getTime())
            ));
        }
        return HibernateBridgeNormalDatas;
    }

    @Override
    public int lookupCount(Connection connection, String preset, Object[] args) throws SQLException {
        switch (preset) {
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE:
                return childForPointBetweenCloseCloseCount(connection, args);
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN:
                return childForPointBetweenCloseOpenCount(connection, args);
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE:
                return childForPointBetweenOpenCloseCount(connection, args);
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN:
                return childForPointBetweenOpenOpenCount(connection, args);
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private int childForPointBetweenCloseCloseCount(
            Connection connection, Object[] args
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseClose(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    private int childForPointBetweenCloseOpenCount(
            Connection connection, Object[] args
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenCloseOpen(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    private int childForPointBetweenOpenCloseCount(
            Connection connection, Object[] args
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenClose(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }

    @SuppressWarnings("DuplicatedCode")
    private int childForPointBetweenOpenOpenCount(
            Connection connection, Object[] args
    ) throws SQLException {
        // 展开参数。
        long pointLongId = ((LongIdKey) args[0]).getLongId();
        Date startDate = (Date) args[1];
        Date endDate = (Date) args[2];

        // 构建SQL。
        StringBuilder sqlBuilder = new StringBuilder();
        Mysql8NativeLookupUtil.selectCountFromTable(
                sqlBuilder, "tbl_hibernate_bridge_normal_data", "tbl", "id"
        );
        Mysql8NativeLookupUtil.forceIndex(sqlBuilder, "idx_point_id_happened_date");
        Mysql8NativeLookupUtil.where(sqlBuilder);
        Mysql8NativeLookupUtil.pointLongIdEquals(sqlBuilder, "tbl", "point_id");
        Mysql8NativeLookupUtil.and(sqlBuilder);
        Mysql8NativeLookupUtil.happenedDateBetweenOpenOpen(sqlBuilder, "tbl", "happened_date");

        // 构建 PreparedStatement。
        PreparedStatement preparedStatement = connection.prepareStatement(sqlBuilder.toString());
        preparedStatement.setLong(1, pointLongId);
        preparedStatement.setTimestamp(2, new Timestamp(startDate.getTime()));
        preparedStatement.setTimestamp(3, new Timestamp(endDate.getTime()));

        // 执行查询，返回结果。
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return Long.valueOf(resultSet.getLong(1)).intValue();
    }
}
