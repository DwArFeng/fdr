package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.fdr.stack.bean.dto.QueryInfo;
import com.dwarfeng.subgrade.stack.exception.HandlerException;

import java.util.List;

/**
 * 查询异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class QueryException extends HandlerException {

    private static final long serialVersionUID = 7976510743461985711L;
    
    private final List<QueryInfo> queryInfos;

    public QueryException(List<QueryInfo> queryInfos) {
        this.queryInfos = queryInfos;
    }

    public QueryException(Throwable cause, List<QueryInfo> queryInfos) {
        super(cause);
        this.queryInfos = queryInfos;
    }

    @Override
    public String getMessage() {
        // 如果 queryInfos 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (queryInfos.size() == 1) {
            return "查询数据时发生异常: " + queryInfos.get(0);
        } else {
            return "查询数据时发生异常: " + queryInfos.size() + " 条数据";
        }
    }
}
