package com.dwarfeng.fdr.stack.exception;

import com.dwarfeng.fdr.stack.struct.Data;

import java.util.List;

/**
 * 更新异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public class UpdateException extends KeepException {

    private static final long serialVersionUID = 8195614129648253151L;

    private final List<? extends Data> datas;

    public UpdateException(List<? extends Data> datas) {
        this.datas = datas;
    }

    public UpdateException(Throwable cause, List<? extends Data> datas) {
        super(cause);
        this.datas = datas;
    }

    @Override
    public String getMessage() {
        // 如果 datas 数量为 1，则返回单数形式的消息；否则，只输出数量。
        if (datas.size() == 1) {
            return "更新数据时发生异常: " + datas.get(0);
        } else {
            return "更新数据时发生异常: " + datas.size() + " 条数据";
        }
    }
}
