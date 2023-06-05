package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.dto.LookupInfo;
import com.dwarfeng.fdr.stack.bean.dto.LookupResult;
import com.dwarfeng.fdr.stack.exception.LookupNotSupportedException;
import com.dwarfeng.fdr.stack.struct.Data;
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
 * 对于只写的持久处理器，其 {@link #lookup(LookupInfo)} 方法以及 {@link #lookup(List)} 方法应该抛出
 * {@link LookupNotSupportedException} 异常。
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
     * @param data 数据。
     * @throws HandlerException 处理器异常。
     */
    void record(D data) throws HandlerException;

    /**
     * 记录数据。
     *
     * @param datas 数据组成的列表。
     * @throws HandlerException 处理器异常。
     */
    void record(List<D> datas) throws HandlerException;

    /**
     * 获取该持久处理器的查看指导。
     *
     * <p>
     * 需要注意的是，返回的列表中，{@link LookupGuide#getPreset()} 方法返回的字符串应该是唯一的。
     *
     * @return 查看指导组成的列表。
     */
    List<LookupGuide> lookupGuides();

    /**
     * 查看。
     *
     * @param lookupInfo 查看信息。
     * @return 查看结果。
     * @throws HandlerException 处理器异常。
     */
    LookupResult<D> lookup(LookupInfo lookupInfo) throws HandlerException;

    /**
     * 查看。
     *
     * @param lookupInfos 查看信息组成的列表。
     * @return 查看结果组成的列表。
     * @throws HandlerException 处理器异常。
     */
    List<LookupResult<D>> lookup(List<LookupInfo> lookupInfos) throws HandlerException;

    /**
     * 查看指导。
     *
     * @author DwArFeng
     * @since 2.0.0
     */
    final class LookupGuide {

        private final String preset;
        private final String[] exampleParams;
        private final String description;

        public LookupGuide(String preset, String[] exampleParams, String description) {
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
            return "LookupGuide{" +
                    "preset='" + preset + '\'' +
                    ", exampleParams=" + Arrays.toString(exampleParams) +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
