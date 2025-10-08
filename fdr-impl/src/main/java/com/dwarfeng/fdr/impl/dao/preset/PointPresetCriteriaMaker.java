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
                remarkLike(detachedCriteria, objects);
                break;
            case PointMaintainService.NORMAL_KEEP_ENABLED_EQ:
                normalKeepEnabledEq(detachedCriteria, objects);
                break;
            case PointMaintainService.NORMAL_PERSIST_ENABLED_EQ:
                normalPersistEnabledEq(detachedCriteria, objects);
                break;
            case PointMaintainService.FILTERED_KEEP_ENABLED_EQ:
                filteredKeepEnabledEq(detachedCriteria, objects);
                break;
            case PointMaintainService.FILTERED_PERSIST_ENABLED_EQ:
                filteredPersistEnabledEq(detachedCriteria, objects);
                break;
            case PointMaintainService.TRIGGERED_KEEP_ENABLED_EQ:
                triggeredKeepEnabledEq(detachedCriteria, objects);
                break;
            case PointMaintainService.TRIGGERED_PERSIST_ENABLED_EQ:
                triggeredPersistEnabledEq(detachedCriteria, objects);
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

    private void remarkLike(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("remark", pattern, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void normalKeepEnabledEq(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            boolean enabled = (boolean) objects[0];
            detachedCriteria.add(Restrictions.eq("normalKeepEnabled", enabled));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void normalPersistEnabledEq(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            boolean enabled = (boolean) objects[0];
            detachedCriteria.add(Restrictions.eq("normalPersistEnabled", enabled));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void filteredKeepEnabledEq(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            boolean enabled = (boolean) objects[0];
            detachedCriteria.add(Restrictions.eq("filteredKeepEnabled", enabled));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void filteredPersistEnabledEq(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            boolean enabled = (boolean) objects[0];
            detachedCriteria.add(Restrictions.eq("filteredPersistEnabled", enabled));

        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void triggeredKeepEnabledEq(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            boolean enabled = (boolean) objects[0];
            detachedCriteria.add(Restrictions.eq("triggeredKeepEnabled", enabled));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void triggeredPersistEnabledEq(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            boolean enabled = (boolean) objects[0];
            detachedCriteria.add(Restrictions.eq("triggeredPersistEnabled", enabled));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
