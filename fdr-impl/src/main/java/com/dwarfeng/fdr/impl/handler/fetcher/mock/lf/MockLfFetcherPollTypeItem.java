package com.dwarfeng.fdr.impl.handler.fetcher.mock.lf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 低频模拟抓取器轮询类型条目。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MockLfFetcherPollTypeItem {
}
