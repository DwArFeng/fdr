package com.dwarfeng.fdr.impl.handler.mapper;

import java.util.*;

/**
 * 聚合映射器。
 *
 * <p>
 * 聚合映射器是一个抽象的映射器，它执行映射操作时，会遍历数据表中的所有序列，
 * 并将每个序列中的所有数据条目聚合为一个数据条目。<br>
 * 聚合后的数据条目的主键为序列的主键，数据条目的值由具体的聚合映射器决定，
 * 数据条目的发生时间为序列的开始时间和结束时间的中间值。
 *
 * <p>
 * 聚合映射器要求数据表中的所有序列的数据点主键不能为 null，否则会抛出异常。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
public abstract class AggregateMapper extends OneToOneMapper {

    protected static final Object VOID = new Object();

    @Override
    protected Sequence doOneToOneMap(MapParam mapParam, Sequence sequence) throws Exception {
        // 展开序列的参数。
        Date startDate = sequence.getStartDate();
        Date endDate = sequence.getEndDate();

        // 遍历数据表中的所有序列，对每个序列进行聚合运算。
        // 获取序列的数据条目列表。
        List<Item> items = sequence.getItems();

        // 调用聚合方法，获取聚合后的值。
        Object value = doAggregate(mapParam, items, startDate, endDate);

        if (Objects.equals(value, VOID)) {
            // 如果聚合后的值为 VOID，则不构造新的数据条目。
            items = Collections.emptyList();
        } else {
            // 构造新的数据条目，其中的发生时间为序列开始时间和结束时间的中间值，数据点主键为序列的数据点主键。
            Item item = new Item(
                    sequence.getPointKey(), value,
                    new Date((startDate.getTime() + endDate.getTime()) / 2)
            );

            // 将新的数据条目添加到数据条目列表中。
            items = new ArrayList<>();
            items.add(item);
        }

        // 返回新的序列。
        return new Sequence(sequence.getPointKey(), items, startDate, endDate);
    }

    /**
     * 执行聚合操作。
     *
     * <p>
     * 返回的对象如果为 {@link #VOID}，则不会构造新的数据条目。
     *
     * <p>
     * 需要注意的是，参数 item 的数据的发生时间不保证在 startDate 和 endDate 之间，聚合操作需要根据实际情况进行处理。
     *
     * @param mapParam  映射参数。
     * @param items     数据条目列表。
     * @param startDate 序列的开始时间。
     * @param endDate   序列的结束时间。
     * @return 聚合后的值。
     * @throws Exception 执行聚合操作时可能抛出的任何异常。
     */
    protected abstract Object doAggregate(
            MapParam mapParam, List<Item> items, Date startDate, Date endDate
    ) throws Exception;
}
