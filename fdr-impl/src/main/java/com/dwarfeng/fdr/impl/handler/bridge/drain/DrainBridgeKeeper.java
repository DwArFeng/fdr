package com.dwarfeng.fdr.impl.handler.bridge.drain;

import com.dwarfeng.fdr.impl.handler.bridge.WriteOnlyKeeper;
import com.dwarfeng.fdr.stack.struct.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Drain 桥接器保持器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class DrainBridgeKeeper<D extends Data> extends WriteOnlyKeeper<D> {

    @Override
    protected void doUpdate(D data) {
    }

    @Override
    protected void doUpdate(List<D> datas) {
    }
}
