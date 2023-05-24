package com.dwarfeng.fdr.impl.handler.mapper;

import com.dwarfeng.fdr.sdk.util.CompareUtil;
import com.dwarfeng.fdr.stack.handler.Mapper;

import java.util.Date;
import java.util.List;

/**
 * 映射器工具类。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
final class MapperUtil {

    /**
     * 对 items 按照时间进行升序排序，并且过滤在 startDate 和 endDate 之外的数据。
     *
     * <p>
     * 如果 extendItem 为 true，则会保留至多一个发生时间在 startDate 之前的数据条目，
     * 以及至多一个发生时间在 endDate 之后的数据条目。
     *
     * @param items      指定的数据条目序列。
     * @param startDate  指定的开始时间。
     * @param endDate    指定的结束时间。
     * @param extendItem 指定是否扩展数据条目。
     * @return 排序并且过滤后的数据条目序列。
     */
    public static List<Mapper.Item> sortAndFilterItems(
            List<Mapper.Item> items, Date startDate, Date endDate, boolean extendItem
    ) {
        // 对 items 按照时间进行排序。
        items.sort(CompareUtil.DATA_HAPPENED_DATE_ASC_COMPARATOR);

        // 从前向后遍历 items。
        int firstIndex = 0;
        for (int i = 0; i < items.size(); i++) {
            Mapper.Item item = items.get(i);
            // 如果 extendItem 为 true，保证至多只有一个元素的发生时间小于等于 startDate。
            if (extendItem) {
                if (item.getHappenedDate().compareTo(startDate) <= 0) {
                    firstIndex = i;
                } else {
                    break;
                }
            }
            // 如果 extendItem 为 false，保证所有元素的发生时间大于等于 startDate。
            else {
                if (item.getHappenedDate().compareTo(startDate) >= 0) {
                    firstIndex = i;
                    break;
                }
            }
        }
        // 从后向前遍历 items。
        int lastIndex = items.size() - 1;
        for (int i = items.size() - 1; i >= 0; i--) {
            Mapper.Item item = items.get(i);
            // 如果 extendItem 为 true，保证至多只有一个元素的发生时间大于等于 endDate。
            if (extendItem) {
                if (item.getHappenedDate().compareTo(endDate) >= 0) {
                    lastIndex = i;
                } else {
                    break;
                }
            }
            // 如果 extendItem 为 false，保证所有元素的发生时间小于等于 endDate。
            else {
                if (item.getHappenedDate().compareTo(endDate) <= 0) {
                    lastIndex = i;
                    break;
                }
            }
        }

        // 判断特殊情况：如果 firstIndex 大于 lastIndex，则返回空列表。
        if (firstIndex > lastIndex) {
            return items.subList(0, 0);
        }

        // 返回过滤后的数据条目序列。
        return items.subList(firstIndex, lastIndex + 1);
    }

    private MapperUtil() {
        throw new IllegalStateException("禁止实例化");
    }
}
