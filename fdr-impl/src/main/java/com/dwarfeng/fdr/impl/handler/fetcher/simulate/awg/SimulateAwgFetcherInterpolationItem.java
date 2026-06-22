package com.dwarfeng.fdr.impl.handler.fetcher.simulate.awg;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 任意波形模拟抓取器插值方式条目。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SimulateAwgFetcherInterpolationItem {
}
