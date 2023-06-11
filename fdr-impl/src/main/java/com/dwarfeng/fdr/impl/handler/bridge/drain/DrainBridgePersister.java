package com.dwarfeng.fdr.impl.handler.bridge.drain;

import com.dwarfeng.fdr.impl.handler.bridge.WriteOnlyPersister;
import com.dwarfeng.fdr.stack.struct.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Drain 桥接器持久化器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class DrainBridgePersister<D extends Data> extends WriteOnlyPersister<D> {

    @Override
    protected void doRecord(D data) {
    }

    @Override
    protected void doRecord(List<D> datas) {
    }
}
