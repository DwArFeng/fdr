package com.dwarfeng.fdr.impl.dao.preset;

import com.dwarfeng.fdr.stack.service.FilterSupportMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class FilterSupportPresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case FilterSupportMaintainService.ID_LIKE:
                idLike(detachedCriteria, objects);
                break;
            case FilterSupportMaintainService.LABEL_LIKE:
                labelLike(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    private void idLike(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("stringId", pattern, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }

    private void labelLike(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            String pattern = (String) objects[0];
            detachedCriteria.add(Restrictions.like("label", pattern, MatchMode.ANYWHERE));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
