package com.dwarfeng.fdr.impl.handler.fetcher.simulate.wave;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 函数波形模拟抓取器波形类型条目。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SimulateWaveFetcherWaveTypeItem {
}
