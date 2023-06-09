package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.entity.WasherInfo;
import com.dwarfeng.fdr.stack.exception.WasherException;

/**
 * 清洗器。
 *
 * <p>
 * 清洗器用将指定的数据对象进行清洗，并获得一个新的数据对象。<br>
 * 通常情况下，数据清洗可以用于数据检查，包括检查数据一致性，处理无效值和缺失值等。
 *
 * <p>
 * 清洗器有两种类型，分别是过滤前清洗器和过滤后清洗器。<br>
 * 过滤前清洗器在数据过滤处理之前进行清洗，过滤后清洗器在数据过滤处理之后进行清洗。<br>
 * 过滤前清洗器通常用于数据检查，过滤后清洗器通常用于数据修正。<br>
 * 通过 {@link WasherInfo#setPreFilter(boolean)} 方法可以设置清洗器的类型。
 *
 * <p>
 * 需要注意的是，清洗器的作用仅限于数据清洗，不应该用于数据过滤。因此对于不合法的数据，
 * 不应该抛出异常（因为这样会中断整个数据处理流程，并触发记录失败相关的调度），而是应该返回一个特殊的值，
 * 例如 <code>null</code>，
 * 或是该项目的 sdk 模块提供的一个特殊的值 <code>com.dwarfeng.fdr.sdk.util.Constants#DATA_VALUE_ILLEGAL</code>。<br>
 * 随后配置对应的过滤器，识别特殊值，从而拒绝该数据。<br>
 * 与此对应的是，如果无法完成数据的清洗流程，利用无法调用外部服务等原因，应该抛出异常。
 *
 * <p>
 * 有关清洗的详细信息，请参阅术语。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public interface Washer {

    /**
     * 清洗指定的数据对象，并返回清洗后的数据对象。
     *
     * @param rawValue 指定的数据对象。
     * @return 清洗后的数据对象。
     * @throws WasherException 清洗器异常。
     */
    Object wash(Object rawValue) throws WasherException;
}
