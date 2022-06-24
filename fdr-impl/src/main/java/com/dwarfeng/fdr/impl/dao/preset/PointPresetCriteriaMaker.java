package com.dwarfeng.fdr.impl.dao.preset;

import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class PointPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case PointMaintainService.NAME_LIKE:
                nameLike(detachedCriteria, objects);
                break;
            case PointMaintainService.REMARK_LIKE:
                remark_like(detachedCriteria, objects);
                break;
            case PointMaintainService.NAME_LIKE_PERSISTENCE_ENABLED:
                nameLikePersistenceEnabled(detachedCriteria, objects);
                break;
            case PointMaintainService.NAME_LIKE_REALTIME_ENABLED:
                nameLikeRealtimeEnabled(detachedCriteria, objects);
                break;
            case PointMaintainService.NAME_LIKE_PERSISTENCE_DISABLED:
                nameLikePersistenceDisabled(detachedCriteria, objects);
                break;
            case PointMaintainService.NAME_LIKE_REALTIME_DISABLED:
                nameLikeRealtimeDisabled(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void nameLike(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("name", pattern, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void remark_like(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("remark", pattern, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void nameLikePersistenceEnabled(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("name", pattern, MatchMode.ANYWHERE));
            detachedCriteria.add(Restrictions.eq("persistenceEnabled", true));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void nameLikeRealtimeEnabled(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("name", pattern, MatchMode.ANYWHERE));
            detachedCriteria.add(Restrictions.eq("realtimeEnabled", true));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void nameLikePersistenceDisabled(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("name", pattern, MatchMode.ANYWHERE));
            detachedCriteria.add(Restrictions.eq("persistenceEnabled", false));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void nameLikeRealtimeDisabled(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("name", pattern, MatchMode.ANYWHERE));
            detachedCriteria.add(Restrictions.eq("realtimeEnabled", false));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
