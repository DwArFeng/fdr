package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;

/**
 * 随机数生成器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class MockBridgeRandomGenerator {

    private Random random;

    @Value("${bridge.mock.random_seed}")
    private Long randomSeed;

    @PostConstruct
    public void init() {
        if (randomSeed == null) {
            random = new Random();
        } else {
            random = new Random(randomSeed);
        }
    }

    public LongIdKey nextLongIdKey() {
        return new LongIdKey(Math.abs(random.nextLong()));
    }

    @RequiredPointType("int")
    public int nextInt() {
        return random.nextInt();
    }

    @RequiredPointType("long")
    public long nextLong() {
        return random.nextLong();
    }

    @RequiredPointType("float")
    public float nextFloat() {
        return random.nextFloat();
    }

    @RequiredPointType("double")
    public double nextDouble() {
        return random.nextDouble();
    }

    @RequiredPointType("gaussian")
    public double nextGaussian() {
        return random.nextGaussian();
    }

    @RequiredPointType("boolean")
    public boolean nextBoolean() {
        return random.nextBoolean();
    }

    @RequiredPointType("string")
    public String nextString() {
        long value = Math.abs(nextLong());
        return Long.toString(value, Character.MAX_RADIX);
    }

    @RequiredPointType("int_string")
    public String nextIntString() {
        int value = nextInt();
        return Integer.toString(value);
    }

    @RequiredPointType("long_string")
    public String nextLongString() {
        long value = nextLong();
        return Long.toString(value);
    }

    @RequiredPointType("float_string")
    public String nextFloatString() {
        float value = nextFloat();
        return Float.toString(value);
    }

    @RequiredPointType("double_string")
    public String nextDoubleString() {
        double value = nextDouble();
        return Double.toString(value);
    }

    @RequiredPointType("gaussian_string")
    public String nextGaussianString() {
        double value = nextGaussian();
        return Double.toString(value);
    }
}
