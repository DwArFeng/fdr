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
