package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.fdr.stack.struct.Data;

import java.util.List;

/**
 * 记录异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class RecordException extends PersistException {

    private static final long serialVersionUID = 1667735534391737438L;
    
    private final List<? extends Data> datas;

    public RecordException(List<? extends Data> datas) {
        this.datas = datas;
    }

    public RecordException(Throwable cause, List<? extends Data> datas) {
        super(cause);
        this.datas = datas;
    }

    @Override
    public String getMessage() {
        // 如果 datas 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (datas.size() == 1) {
            return "记录数据时发生异常: " + datas.get(0);
        } else {
            return "记录数据时发生异常: " + datas.size() + " 条数据";
        }
    }
}
