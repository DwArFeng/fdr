package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;

import java.util.List;

/**
 * 查询数据点最新数据异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class LatestException extends KeepException {

    private static final long serialVersionUID = 9002930984857670384L;
    
    private final List<LongIdKey> pointKeys;

    public LatestException(List<LongIdKey> pointKeys) {
        this.pointKeys = pointKeys;
    }

    public LatestException(Throwable cause, List<LongIdKey> pointKeys) {
        super(cause);
        this.pointKeys = pointKeys;
    }

    @Override
    public String getMessage() {
        // 如果 pointKeys 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (pointKeys.size() == 1) {
            return "查询数据点最新数据时发生异常: " + pointKeys.get(0);
        } else {
            return "查询数据点最新数据时发生异常: " + pointKeys.size() + " 条数据";
        }
    }
}
