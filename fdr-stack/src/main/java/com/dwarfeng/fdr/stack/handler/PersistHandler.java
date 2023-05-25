package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.fdr.stack.bean.dto.QueryResult;
import com.dwarfeng.fdr.stack.exception.MethodNotSupportedException;
import com.dwarfeng.fdr.stack.structure.Data;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import com.dwarfeng.subgrade.stack.handler.Handler;

import java.util.Arrays;
import java.util.List;

/**
 * 持久处理器。
 *
 * <p>
 * 该处理器用于持久数据，实现类似于历史数据的查询与更新。
 *
 * <p>
 * 持久处理器为每个数据点维护一系列历史数据，这些数据可以被查询。同时，新的历史数据可以被记录。
 *
 * <p>
 * 部分持久处理器可能只支持写入，不支持查询。此时，该持久处理器的 {@link #writeOnly()} 方法返回 true。<br>
 * 对于只写的持久处理器，其 {@link #query(QueryInfo)} 方法应该抛出{@link MethodNotSupportedException} 异常。<br>
 * 而且，由于组织处理器的查看方法依赖于持久处理器的查询方法，因此，对于只写的持久处理器，其组织处理器的查看方法也应该抛出
 * {@link MethodNotSupportedException} 异常。
 *
 * <p>
 * 有关持久的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface PersistHandler<D extends Data> extends Handler {

    /**
     * 获取该处理器是否为只写处理器。
     *
     * @return 该处理器是否为只写处理器。
     */
    boolean writeOnly();

    /**
     * 记录数据。
     *
     * @param dataRecord 数据记录。
     * @throws HandlerException 处理器异常。
     */
    void record(D dataRecord) throws HandlerException;

    /**
     * 记录数据。
     *
     * @param dataRecords 数据记录组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void record(List<D> dataRecords) throws HandlerException;

    /**
     * 获取该持久处理器的查询指导。
     *
     * <p>
     * 需要注意的是，返回的列表中，{@link QueryGuide#getPreset()} 方法返回的字符串应该是唯一的。
     *
     * @return 查询指导组成的列表。
     */
    List<QueryGuide> queryGuides();

    /**
     * 查询。
     *
     * @param queryInfo 查询信息。
     * @return 查询结果。
     * @throws HandlerException 处理器异常。
     */
    QueryResult<D> query(QueryInfo queryInfo) throws HandlerException;

    /**
     * 查询指导。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    final class QueryGuide {

        private final String preset;
        private final String[] exampleParams;
        private final String description;

        public QueryGuide(String preset, String[] exampleParams, String description) {
            this.preset = preset;
            this.exampleParams = exampleParams;
            this.description = description;
        }

        public String getPreset() {
            return preset;
        }

        public String[] getExampleParams() {
            return exampleParams;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return "QueryGuide{" +
                    "preset='" + preset + '\'' +
                    ", exampleParams=" + Arrays.toString(exampleParams) +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
