package com.dwarfeng.fdr.impl.dao.preset;

import com.dwarfeng.fdr.stack.service.LookupSupportMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class LookupSupportPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case LookupSupportMaintainService.CATEGORY_EQUALS:
                categoryEquals(detachedCriteria, objects);
                break;
            case LookupSupportMaintainService.PRESET_LIKE:
                presetLike(detachedCriteria, objects);
                break;
            case LookupSupportMaintainService.CATEGORY_EQUALS_PRESET_LIKE:
                categoryEqualsPresetLike(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void categoryEquals(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String category = (String) objects[0];
            detachedCriteria.add(Restrictions.eq("category", category));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void presetLike(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("preset", pattern, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void categoryEqualsPresetLike(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String category = (String) objects[0];
            String pattern = (String) objects[1];
            detachedCriteria.add(Restrictions.eq("category", category));
            detachedCriteria.add(Restrictions.like("preset", pattern, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
