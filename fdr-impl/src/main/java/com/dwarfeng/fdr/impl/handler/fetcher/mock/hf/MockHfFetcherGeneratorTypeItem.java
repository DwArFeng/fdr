package com.dwarfeng.fdr.impl.handler.fetcher.mock.hf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 高频模拟抓取器值生成器类型条目。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MockHfFetcherGeneratorTypeItem {
}
