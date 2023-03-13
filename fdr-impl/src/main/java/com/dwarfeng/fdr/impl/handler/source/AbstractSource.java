package com.dwarfeng.fdr.impl.handler.source;

import com.dwarfeng.fdr.stack.handler.Source;

/**
 * 数据源的抽象实现。
 *
 * @author DwArFeng
 * @since 1.0.0
 */
public abstract class AbstractSource implements Source {

    protected Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "AbstractSource{" +
                "context=" + context +
                '}';
    }
}
