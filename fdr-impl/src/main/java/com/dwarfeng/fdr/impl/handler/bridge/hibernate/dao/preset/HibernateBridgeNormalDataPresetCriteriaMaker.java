package com.dwarfeng.fdr.impl.handler.bridge.hibernate.dao.preset;

import com.dwarfeng.fdr.impl.handler.bridge.hibernate.service.HibernateBridgeNormalDataMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class HibernateBridgeNormalDataPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_CLOSE:
                childForPointBetweenCloseClose(detachedCriteria, objects);
                break;
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_CLOSE_OPEN:
                childForPointBetweenCloseOpen(detachedCriteria, objects);
                break;
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_CLOSE:
                childForPointBetweenOpenClose(detachedCriteria, objects);
                break;
            case HibernateBridgeNormalDataMaintainService.CHILD_FOR_POINT_BETWEEN_OPEN_OPEN:
                childForPointBetweenOpenOpen(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForPointBetweenCloseClose(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            Long pointLongId = ((LongIdKey) objects[0]).getLongId();
            Date startDate = (Date) objects[1];
            Date endDate = (Date) objects[2];

            detachedCriteria.add(Restrictions.eqOrIsNull("pointLongId", pointLongId));
            detachedCriteria.add(Restrictions.ge("happenedDate", startDate));
            detachedCriteria.add(Restrictions.le("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForPointBetweenCloseOpen(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            Long pointLongId = ((LongIdKey) objects[0]).getLongId();
            Date startDate = (Date) objects[1];
            Date endDate = (Date) objects[2];

            detachedCriteria.add(Restrictions.eqOrIsNull("pointLongId", pointLongId));
            detachedCriteria.add(Restrictions.ge("happenedDate", startDate));
            detachedCriteria.add(Restrictions.lt("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForPointBetweenOpenClose(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            Long pointLongId = ((LongIdKey) objects[0]).getLongId();
            Date startDate = (Date) objects[1];
            Date endDate = (Date) objects[2];

            detachedCriteria.add(Restrictions.eqOrIsNull("pointLongId", pointLongId));
            detachedCriteria.add(Restrictions.gt("happenedDate", startDate));
            detachedCriteria.add(Restrictions.le("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    @SuppressWarnings("DuplicatedCode")
    private void childForPointBetweenOpenOpen(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            Long pointLongId = ((LongIdKey) objects[0]).getLongId();
            Date startDate = (Date) objects[1];
            Date endDate = (Date) objects[2];

            detachedCriteria.add(Restrictions.eqOrIsNull("pointLongId", pointLongId));
            detachedCriteria.add(Restrictions.gt("happenedDate", startDate));
            detachedCriteria.add(Restrictions.lt("happenedDate", endDate));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
