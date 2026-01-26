package com.dwarfeng.fdr.impl.dao.preset;

import com.dwarfeng.fdr.stack.bean.dto.PointCompositeLookupInfo;
import com.dwarfeng.fdr.stack.service.PointMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

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
            case PointMaintainService.COMPOSITE_LOOKUP:
                compositeLookup(detachedCriteria, objects);
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

    private void compositeLookup(DetachedCriteria detachedCriteria, Object[] objects) {
        try {
            // 获取 PointCompositeLookupInfo 参数。
            PointCompositeLookupInfo info = (PointCompositeLookupInfo) objects[0];
            // 展开参数。
            String namePattern = info.getNamePattern();
            String remarkPattern = info.getRemarkPattern();
            Boolean normalKeepEnabled = info.getNormalKeepEnabled();
            Boolean normalPersistEnabled = info.getNormalPersistEnabled();
            Boolean filteredKeepEnabled = info.getFilteredKeepEnabled();
            Boolean filteredPersistEnabled = info.getFilteredPersistEnabled();
            Boolean triggeredKeepEnabled = info.getTriggeredKeepEnabled();
            Boolean triggeredPersistEnabled = info.getTriggeredPersistEnabled();
            String reservedStringAlphaPattern = info.getReservedStringAlphaPattern();
            String reservedStringBravoPattern = info.getReservedStringBravoPattern();
            String reservedStringCharliePattern = info.getReservedStringCharliePattern();
            String reservedStringDeltaPattern = info.getReservedStringDeltaPattern();
            Long reservedLongAlphaMin = info.getReservedLongAlphaMin();
            Long reservedLongAlphaMax = info.getReservedLongAlphaMax();
            Long reservedLongBravoMin = info.getReservedLongBravoMin();
            Long reservedLongBravoMax = info.getReservedLongBravoMax();
            Integer reservedIntegerAlphaMin = info.getReservedIntegerAlphaMin();
            Integer reservedIntegerAlphaMax = info.getReservedIntegerAlphaMax();
            Integer reservedIntegerBravoMin = info.getReservedIntegerBravoMin();
            Integer reservedIntegerBravoMax = info.getReservedIntegerBravoMax();
            Boolean reservedBooleanAlpha = info.getReservedBooleanAlpha();
            Boolean reservedBooleanBravo = info.getReservedBooleanBravo();
            Date reservedDateAlphaMin = info.getReservedDateAlphaMin();
            Date reservedDateAlphaMax = info.getReservedDateAlphaMax();
            Date reservedDateBravoMin = info.getReservedDateBravoMin();
            Date reservedDateBravoMax = info.getReservedDateBravoMax();
            // 根据字段添加条件。
            if (StringUtils.isNotBlank(namePattern)) {
                detachedCriteria.add(Restrictions.like("name", namePattern, MatchMode.ANYWHERE));
            }
            if (StringUtils.isNotBlank(remarkPattern)) {
                detachedCriteria.add(Restrictions.like("remark", remarkPattern, MatchMode.ANYWHERE));
            }
            if (Objects.nonNull(normalKeepEnabled)) {
                detachedCriteria.add(Restrictions.eq("normalKeepEnabled", normalKeepEnabled));
            }
            if (Objects.nonNull(normalPersistEnabled)) {
                detachedCriteria.add(Restrictions.eq("normalPersistEnabled", normalPersistEnabled));
            }
            if (Objects.nonNull(filteredKeepEnabled)) {
                detachedCriteria.add(Restrictions.eq("filteredKeepEnabled", filteredKeepEnabled));
            }
            if (Objects.nonNull(filteredPersistEnabled)) {
                detachedCriteria.add(Restrictions.eq("filteredPersistEnabled", filteredPersistEnabled));
            }
            if (Objects.nonNull(triggeredKeepEnabled)) {
                detachedCriteria.add(Restrictions.eq("triggeredKeepEnabled", triggeredKeepEnabled));
            }
            if (Objects.nonNull(triggeredPersistEnabled)) {
                detachedCriteria.add(Restrictions.eq("triggeredPersistEnabled", triggeredPersistEnabled));
            }
            if (StringUtils.isNotBlank(reservedStringAlphaPattern)) {
                detachedCriteria.add(Restrictions.like(
                        "reservedStringAlpha", reservedStringAlphaPattern, MatchMode.ANYWHERE
                ));
            }
            if (StringUtils.isNotBlank(reservedStringBravoPattern)) {
                detachedCriteria.add(Restrictions.like(
                        "reservedStringBravo", reservedStringBravoPattern, MatchMode.ANYWHERE
                ));
            }
            if (StringUtils.isNotBlank(reservedStringCharliePattern)) {
                detachedCriteria.add(Restrictions.like(
                        "reservedStringCharlie", reservedStringCharliePattern, MatchMode.ANYWHERE
                ));
            }
            if (StringUtils.isNotBlank(reservedStringDeltaPattern)) {
                detachedCriteria.add(Restrictions.like(
                        "reservedStringDelta", reservedStringDeltaPattern, MatchMode.ANYWHERE
                ));
            }
            if (Objects.nonNull(reservedLongAlphaMin)) {
                detachedCriteria.add(Restrictions.ge("reservedLongAlpha", reservedLongAlphaMin));
            }
            if (Objects.nonNull(reservedLongAlphaMax)) {
                detachedCriteria.add(Restrictions.le("reservedLongAlpha", reservedLongAlphaMax));
            }
            if (Objects.nonNull(reservedLongBravoMin)) {
                detachedCriteria.add(Restrictions.ge("reservedLongBravo", reservedLongBravoMin));
            }
            if (Objects.nonNull(reservedLongBravoMax)) {
                detachedCriteria.add(Restrictions.le("reservedLongBravo", reservedLongBravoMax));
            }
            if (Objects.nonNull(reservedIntegerAlphaMin)) {
                detachedCriteria.add(Restrictions.ge("reservedIntegerAlpha", reservedIntegerAlphaMin));
            }
            if (Objects.nonNull(reservedIntegerAlphaMax)) {
                detachedCriteria.add(Restrictions.le("reservedIntegerAlpha", reservedIntegerAlphaMax));
            }
            if (Objects.nonNull(reservedIntegerBravoMin)) {
                detachedCriteria.add(Restrictions.ge("reservedIntegerBravo", reservedIntegerBravoMin));
            }
            if (Objects.nonNull(reservedIntegerBravoMax)) {
                detachedCriteria.add(Restrictions.le("reservedIntegerBravo", reservedIntegerBravoMax));
            }
            if (Objects.nonNull(reservedBooleanAlpha)) {
                detachedCriteria.add(Restrictions.eq("reservedBooleanAlpha", reservedBooleanAlpha));
            }
            if (Objects.nonNull(reservedBooleanBravo)) {
                detachedCriteria.add(Restrictions.eq("reservedBooleanBravo", reservedBooleanBravo));
            }
            if (Objects.nonNull(reservedDateAlphaMin)) {
                detachedCriteria.add(Restrictions.ge("reservedDateAlpha", reservedDateAlphaMin));
            }
            if (Objects.nonNull(reservedDateAlphaMax)) {
                detachedCriteria.add(Restrictions.le("reservedDateAlpha", reservedDateAlphaMax));
            }
            if (Objects.nonNull(reservedDateBravoMin)) {
                detachedCriteria.add(Restrictions.ge("reservedDateBravo", reservedDateBravoMin));
            }
            if (Objects.nonNull(reservedDateBravoMax)) {
                detachedCriteria.add(Restrictions.le("reservedDateBravo", reservedDateBravoMax));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objects));
        }
    }
}
