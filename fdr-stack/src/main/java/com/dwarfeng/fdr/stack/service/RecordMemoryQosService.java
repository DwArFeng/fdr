package com.dwarfeng.fdr.stack.service;

import com.dwarfeng.fdr.stack.struct.RecordMemory;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.ServiceException;
import com.dwarfeng.subgrade.stack.service.Service;

import java.util.List;

/**
 * 记录记忆 QoS 服务。
 *
 * <p>
 * 该服务用于运维场景下对点位级记录记忆进行查询与清理。
 *
 * @author DwArFeng
 * @since 2.5.0
 */
public interface RecordMemoryQosService extends Service {

    /**
     * 查询指定点位的记录记忆。
     *
     * <p>
     * 返回列表中的记录记忆按时间从新到旧排列，索引 <code>0</code> 对应最新的记录记忆。
     *
     * <p>
     * 调用者有义务仅对返回结果进行查看操作，不应对其进行任何修改。
     *
     * @param pointKey 点位主键。
     * @return 指定点位的记录记忆。
     * @throws ServiceException 服务异常。
     */
    List<RecordMemory> lookup(LongIdKey pointKey) throws ServiceException;

    /**
     * 移除指定的点位对应的记录记忆。
     *
     * @param pointKey 点位主键。
     * @throws ServiceException 服务异常。
     */
    void remove(LongIdKey pointKey) throws ServiceException;

    /**
     * 清除记录记忆。
     *
     * @throws ServiceException 服务异常。
     */
    void clear() throws ServiceException;
}
