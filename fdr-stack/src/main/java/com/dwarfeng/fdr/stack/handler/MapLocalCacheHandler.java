package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 映射本地缓存处理器。
 *
 * <p>
 * 该处理器实现了 <code>LocalCacheHandler&lt;String, Mapper&gt;</code> 接口，
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
 *         <td>String</td>
 *         <td>映射器类型</td>
 *     </tr>
 *     <tr>
 *         <td>V</td>
 *         <td>Mapper</td>
 *         <td>映射器</td>
 *     </tr>
 * </table>
 *
 * <p>
 * 该处理器的实现应该是线程安全的。
 *
 * @author DwArFeng
 * @since 1.9.0
 */
public interface MapLocalCacheHandler extends LocalCacheHandler<String, Mapper> {
}
