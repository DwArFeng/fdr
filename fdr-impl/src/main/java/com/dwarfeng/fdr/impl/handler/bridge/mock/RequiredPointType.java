package com.dwarfeng.fdr.impl.handler.bridge.mock;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@interface RequiredPointType {

    String value();
}
