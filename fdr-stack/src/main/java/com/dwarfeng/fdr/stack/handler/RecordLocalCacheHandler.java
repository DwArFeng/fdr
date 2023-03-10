package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.fdr.stack.bean.entity.Point;
import com.dwarfeng.subgrade.stack.bean.Bean;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.handler.LocalCacheHandler;

import java.util.List;

import static com.dwarfeng.fdr.stack.handler.RecordLocalCacheHandler.RecordContext;

/**
 * 记录本地缓存处理器。
 *
 * <p>处理器在本地保存数据，缓存中的数据可以保证与数据源保持同步。</p>
 * <p>数据存放在本地，必要时才与数据访问层通信，这有助于程序效率的提升。</p>
 * <p>该处理器线程安全。</p>
 *
 * @author DwArFeng
 * @since 1.2.0.a
 */
public interface RecordLocalCacheHandler extends LocalCacheHandler<LongIdKey, RecordContext> {

    /**
     * 数据记录上下文。
     *
     * @author DwArFeng
     * @since 1.2.0.a
     */
    class RecordContext implements Bean {

        private static final long serialVersionUID = 7006527237602002570L;

        private Point point;
        private List<Filter> filters;
        private List<Trigger> triggers;

        public RecordContext() {
        }

        public RecordContext(Point point, List<Filter> filters, List<Trigger> triggers) {
            this.point = point;
            this.filters = filters;
            this.triggers = triggers;
        }

        public Point getPoint() {
            return point;
        }

        public void setPoint(Point point) {
            this.point = point;
        }

        public List<Filter> getFilters() {
            return filters;
        }

        public void setFilters(List<Filter> filters) {
            this.filters = filters;
        }

        public List<Trigger> getTriggers() {
            return triggers;
        }

        public void setTriggers(List<Trigger> triggers) {
            this.triggers = triggers;
        }

        @Override
        public String toString() {
            return "RecordContext{" +
                    "point=" + point +
                    ", filters=" + filters +
                    ", triggers=" + triggers +
                    '}';
        }
    }
}
