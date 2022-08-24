package com.dwarfeng.fdr.impl.dao.preset;

import com.dwarfeng.fdr.stack.service.FilteredValueMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
@Component
public class FilteredValuePresetCriteriaMaker implements PresetCriteriaMaker {

    @Override
    public void makeCriteria(DetachedCriteria criteria, String preset, Object[] objs) {
        switch (preset) {
            case FilteredValueMaintainService.BETWEEN:
                between(criteria, objs);
                break;
            case FilteredValueMaintainService.CHILD_FOR_POINT:
                childForPoint(criteria, objs);
                break;
            case FilteredValueMaintainService.CHILD_FOR_FILTER:
                childForFilter(criteria, objs);
                break;
            case FilteredValueMaintainService.CHILD_FOR_FILTER_SET:
                childForFilterSet(criteria, objs);
                break;
            case FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN:
                childForPointBetween(criteria, objs);
                break;
            case FilteredValueMaintainService.CHILD_FOR_POINT_BETWEEN_RB_OPEN:
                childForPointBetweenRbOpen(criteria, objs);
                break;
            case FilteredValueMaintainService.CHILD_FOR_FILTER_BETWEEN:
                childForFilterSetBetween(criteria, objs);
                break;
            case FilteredValueMaintainService.CHILD_FOR_POINT_PREVIOUS:
                childForPointPrevious(criteria, objs);
                break;
            case FilteredValueMaintainService.CHILD_FOR_POINT_REAR:
                childForPointRear(criteria, objs);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + preset);
        }
    }

    private void between(DetachedCriteria criteria, Object[] objs) {
        try {
            Date startDate = (Date) objs[0];
            Date endDate = (Date) objs[1];
            criteria.add(Restrictions.ge("happenedDate", startDate));
            criteria.add(Restrictions.le("happenedDate", endDate));
            criteria.addOrder(Order.asc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForPoint(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("pointLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("pointLongId", longIdKey.getLongId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForFilter(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("filterLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("filterLongId", longIdKey.getLongId()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForFilterSet(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("filterLongId"));
            } else {
                @SuppressWarnings("unchecked")
                List<LongIdKey> longIdKeys = (List<LongIdKey>) objs[0];
                if (longIdKeys.isEmpty()) {
                    criteria.add(Restrictions.isNull("longId"));
                } else {
                    criteria.add(Restrictions.in("filterLongId", longList(longIdKeys)));
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForPointBetween(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("pointLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("pointLongId", longIdKey.getLongId()));
            }
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];
            criteria.add(Restrictions.ge("happenedDate", startDate));
            criteria.add(Restrictions.le("happenedDate", endDate));
            criteria.addOrder(Order.asc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForPointBetweenRbOpen(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("pointLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("pointLongId", longIdKey.getLongId()));
            }
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];
            criteria.add(Restrictions.ge("happenedDate", startDate));
            criteria.add(Restrictions.lt("happenedDate", endDate));
            criteria.addOrder(Order.asc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForFilterSetBetween(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("filterLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("filterLongId", longIdKey.getLongId()));
            }
            Date startDate = (Date) objs[1];
            Date endDate = (Date) objs[2];
            criteria.add(Restrictions.ge("happenedDate", startDate));
            criteria.add(Restrictions.le("happenedDate", endDate));
            criteria.addOrder(Order.asc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForPointPrevious(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("pointLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("pointLongId", longIdKey.getLongId()));
            }
            Date date = (Date) objs[1];
            criteria.add(Restrictions.lt("happenedDate", date));
            criteria.addOrder(Order.desc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private void childForPointRear(DetachedCriteria criteria, Object[] objs) {
        try {
            if (Objects.isNull(objs[0])) {
                criteria.add(Restrictions.isNull("pointLongId"));
            } else {
                LongIdKey longIdKey = (LongIdKey) objs[0];
                criteria.add(Restrictions.eqOrIsNull("pointLongId", longIdKey.getLongId()));
            }
            Date date = (Date) objs[1];
            criteria.add(Restrictions.gt("happenedDate", date));
            criteria.addOrder(Order.asc("happenedDate"));
        } catch (Exception e) {
            throw new IllegalArgumentException("非法的参数:" + Arrays.toString(objs));
        }
    }

    private List<Long> longList(List<LongIdKey> list) {
        return list.stream().map(LongIdKey::getLongId).collect(Collectors.toList());
    }
}
