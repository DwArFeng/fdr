package com.dwarfeng.fdr.stack.handler;

import com.dwarfeng.dcti.stack.bean.dto.TimedValue;
import com.dwarfeng.fdr.stack.exception.MapperException;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 映射器。
 *
 * @author DwArFeng
 * @since 1.4.1
 */
public interface Mapper {

    /**
     * 映射拥有发生时间的数据值。
     *
     * @param mapData 待映射的数据。
     * @return 映射后的拥有发生时间的数据值。
     * @throws MapperException 映射器异常。
     */
    List<TimedValue> map(MapData mapData) throws MapperException;

    /**
     * 映射数据。
     *
     * @author DwArFeng
     * @since 1.9.0
     */
    class MapData implements Dto {

        private static final long serialVersionUID = 5280142375890070814L;

        private List<TimedValue> timedValues;
        private TimedValue previous;
        /**
         * @since 1.10.0
         */
        private TimedValue rear;
        private Date startDate;
        private Date endDate;
        private Object[] args;

        public MapData() {
        }

        public MapData(
                List<TimedValue> timedValues, TimedValue previous, TimedValue rear, Date startDate, Date endDate,
                Object[] args
        ) {
            this.timedValues = timedValues;
            this.previous = previous;
            this.rear = rear;
            this.startDate = startDate;
            this.endDate = endDate;
            this.args = args;
        }

        public List<TimedValue> getTimedValues() {
            return timedValues;
        }

        public void setTimedValues(List<TimedValue> timedValues) {
            this.timedValues = timedValues;
        }

        public TimedValue getPrevious() {
            return previous;
        }

        public void setPrevious(TimedValue previous) {
            this.previous = previous;
        }

        public TimedValue getRear() {
            return rear;
        }

        public void setRear(TimedValue rear) {
            this.rear = rear;
        }

        public Date getStartDate() {
            return startDate;
        }

        public void setStartDate(Date startDate) {
            this.startDate = startDate;
        }

        public Date getEndDate() {
            return endDate;
        }

        public void setEndDate(Date endDate) {
            this.endDate = endDate;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        @Override
        public String toString() {
            return "MapData{" +
                    "timedValues=" + timedValues +
                    ", previous=" + previous +
                    ", rear=" + rear +
                    ", startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
