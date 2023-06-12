package com.dwarfeng.fdr.impl.handler.source.mock.realtime;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@interface RequiredPointType {

    String value();
}
