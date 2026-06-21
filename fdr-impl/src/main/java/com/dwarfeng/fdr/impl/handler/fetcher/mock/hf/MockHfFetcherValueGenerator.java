package com.dwarfeng.fdr.impl.handler.fetcher.mock.hf;

import com.dwarfeng.subgrade.sdk.interceptor.analyse.BehaviorAnalyse;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 高频模拟抓取器值生成器。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MockHfFetcherValueGenerator {

    private final Random random;

    private final String generateType;

    public MockHfFetcherValueGenerator(Random random, String generateType) {
        this.random = random;
        this.generateType = generateType;
    }

    @BehaviorAnalyse
    public Object generateValue() {
        switch (generateType) {
            case MockHfFetcherConstants.GENERATOR_TYPE_INT:
                return random.nextInt();
            case MockHfFetcherConstants.GENERATOR_TYPE_LONG:
                return random.nextLong();
            case MockHfFetcherConstants.GENERATOR_TYPE_FLOAT:
                return random.nextFloat();
            case MockHfFetcherConstants.GENERATOR_TYPE_DOUBLE:
                return random.nextDouble();
            case MockHfFetcherConstants.GENERATOR_TYPE_GAUSSIAN:
                return random.nextGaussian();
            case MockHfFetcherConstants.GENERATOR_TYPE_BOOLEAN:
                return random.nextBoolean();
            case MockHfFetcherConstants.GENERATOR_TYPE_STRING:
                long value = Math.abs(random.nextLong());
                return Long.toString(value, Character.MAX_RADIX);
            case MockHfFetcherConstants.GENERATOR_TYPE_INT_STRING:
                return Integer.toString(random.nextInt());
            case MockHfFetcherConstants.GENERATOR_TYPE_LONG_STRING:
                return Long.toString(random.nextLong());
            case MockHfFetcherConstants.GENERATOR_TYPE_FLOAT_STRING:
                return Float.toString(random.nextFloat());
            case MockHfFetcherConstants.GENERATOR_TYPE_DOUBLE_STRING:
                return Double.toString(random.nextDouble());
            case MockHfFetcherConstants.GENERATOR_TYPE_GAUSSIAN_STRING:
                return Double.toString(random.nextGaussian());
            case MockHfFetcherConstants.GENERATOR_TYPE_BOOLEAN_STRING:
                return Boolean.toString(random.nextBoolean());
            default:
                throw new IllegalArgumentException("Unsupported generate type: " + generateType);
        }
    }
}
