package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

/**
 * 抓取器本地缓存处理器。
 *
 * <p>
 * 该处理器实现了 <code>LocalCacheHandler&lt;LongIdKey, Fetcher&gt;</code> 接口，
 * 用于缓存无状态的抓取器对象。<br>
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
 *         <td>Fetcher</td>
 *         <td>抓取器</td>
 *     </tr>
 * </table>
 *
 * <p>
 * 该处理器的实现应该是线程安全的。
 *
 * @author DwArFeng
 * @see LocalCacheHandler
 * @see Fetcher
 * @since 3.1.0
 */
public interface FetcherLocalCacheHandler extends LocalCacheHandler<LongIdKey, Fetcher> {
}
