package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

import java.util.Map;

import static com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler.RecordContext;

/**
 * 记录本地缓存处理器。
 *
 * <p>处理器在本地保存数据，缓存中的数据可以保证与数据源保持同步。</p>
 * <p>数据存放在本地，必要时才与数据访问层通信，这有助于程序效率的提升。</p>
 * <p>该处理器线程安全。</p>
 *
 * @author DwArFeng
 * @since 1.2.0
 */
public interface RecordLocalCacheHandler extends LocalCacheHandler<LongIdKey, RecordContext> {

    /**
     * 数据记录上下文。
     *
     * <p>
     * 注意： 该类中的字段 filterMap 和 triggerMap 需要保证遍历顺序，建议使用 LinkedHashMap 实现。
     *
     * @author DwArFeng
     * @since 1.2.0
     */
    final class RecordContext {

        private final Point point;
        private final Map<LongIdKey, Filter> filterMap;
        private final Map<LongIdKey, Trigger> triggerMap;

        public RecordContext(Point point, Map<LongIdKey, Filter> filterMap, Map<LongIdKey, Trigger> triggerMap) {
            this.point = point;
            this.filterMap = filterMap;
            this.triggerMap = triggerMap;
        }

        public Point getPoint() {
            return point;
        }

        public Map<LongIdKey, Filter> getFilterMap() {
            return filterMap;
        }

        public Map<LongIdKey, Trigger> getTriggerMap() {
            return triggerMap;
        }

        @Override
        public String toString() {
            return "RecordContext{" +
                    "point=" + point +
                    ", filterMap=" + filterMap +
                    ", triggerMap=" + triggerMap +
                    '}';
        }
    }
}
