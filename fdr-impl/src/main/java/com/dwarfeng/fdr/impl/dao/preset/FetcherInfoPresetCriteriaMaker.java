package com.dwarfeng.fdr.impl.dao.preset;

import com.dwarfeng.fdr.stack.service.FetcherInfoMaintainService;
import com.dwarfeng.subgrade.sdk.hibernate.criteria.PresetCriteriaMaker;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

@Component
public class FetcherInfoPresetCriteriaMaker implements PresetCriteriaMaker {

    @SuppressWarnings("SwitchStatementWithTooFewBranches")
    @Override
    public void makeCriteria(DetachedCriteria detachedCriteria, String s, Object[] objects) {
        switch (s) {
            case FetcherInfoMaintainService.ENABLED:
                enabled(detachedCriteria, objects);
                break;
            default:
                throw new IllegalArgumentException("无法识别的预设: " + s);
        }
    }

    // 为了代码的可读性，此处不简化代码。
    @SuppressWarnings("unused")
    private void enabled(DetachedCriteria detachedCriteria, Object[] objects) {
        detachedCriteria.add(Restrictions.eqOrIsNull("enabled", true));
    }
}
