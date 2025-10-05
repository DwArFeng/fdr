package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.struct.RecordLocalCache;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 记录本地缓存处理器。
 *
 * <p>
 * 该处理器实现了 <code>LocalCacheHandler&lt;LongIdKey, RecordLocalCache&gt;</code> 接口，
 * 用于处理与作业相关的本地缓存。<br>
 * 对于 <code>LocalCacheHandler&lt;K, V&gt;</code> 接口中的泛型参数:
 * <table>
 *     <tr>
 *         <th></th>
 *         <th>类型</th>
 *         <th>含义</th>
 *     </tr>
 *     <tr>
 *         <td>K</td>
 *         <td>LongIdKey</td>
 *         <td>数据点主键</td>
 *     </tr>
 *     <tr>
 *         <td>V</td>
 *         <td>RecordLocalCache</td>
 *         <td>作业本地缓存</td>
 *     </tr>
 * </table>
 *
 * <p>
 * 该处理器的实现应该是线程安全的。
 *
 * @author DwArFeng
 * @see LocalCacheHandler
 * @see RecordLocalCache
 * @since 1.2.0
 */
public interface RecordLocalCacheHandler extends LocalCacheHandler<LongIdKey, RecordLocalCache> {
}
