package com.dwarfeng.fdr.impl.handler;

import com.dwarfeng.fdr.stack.exception.KeeperNotSupportedException;
import com.dwarfeng.fdr.stack.exception.PersisterNotSupportedException;

/**
 * 桥接器。
 *
 * <p>
 * 用于提供保持器和持久器。
 *
 * <p>
 * 需要注意的是，不是所有的桥接器都支持所有的数据类型，因此如果<code>getXXXDataKeeper</code> 或
 * <code>getXXXDataPersist</code> 被调用时，如果桥接器不支持持久器/保持器，则可以抛出相应的异常。<br>
 * <ul>
 *     <li>如果桥接器不支持保持器，则应抛出 {@link KeeperNotSupportedException}</li>
 *     <li>如果桥接器不支持持久器，则应抛出 {@link PersisterNotSupportedException}</li>
 * </ul>
 *
 * @author DwArFeng
 * @see com.dwarfeng.fdr.sdk.handler.Bridge
 * @since 2.0.0
 * @deprecated 该对象已经被废弃，请使用 sdk 模块下的对应对象代替。
 */
@Deprecated
public interface Bridge extends com.dwarfeng.fdr.sdk.handler.Bridge {
}
