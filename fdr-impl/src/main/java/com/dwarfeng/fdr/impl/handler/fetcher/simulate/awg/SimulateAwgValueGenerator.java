package com.dwarfeng.fdr.impl.handler.fetcher.simulate.awg;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 任意波形模拟抓取器值生成器。
 *
 * <p>
 * 根据样本序号、波表与 DDS 相位推进规则计算 <code>Double</code> 采样值，用于可解释、可复现的任意波表仿真。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class SimulateAwgValueGenerator {

    private final long sampleFrequency;
    private final double waveTableFrequency;
    private final double[] waveTable;
    private final String interpolation;
    private final double amplitude;
    private final double offset;

    public SimulateAwgValueGenerator(
            long sampleFrequency, double waveTableFrequency, double[] waveTable, String interpolation,
            double amplitude, double offset
    ) {
        this.sampleFrequency = sampleFrequency;
        this.waveTableFrequency = waveTableFrequency;
        this.waveTable = waveTable.clone();
        this.interpolation = interpolation;
        this.amplitude = amplitude;
        this.offset = offset;
    }

    /**
     * 按样本序号生成波表采样值。
     *
     * <p>
     * 本方法根据构造时注入的输出采样频率、波表点频率、内联波表及插值方式，将离散样本序号映射为
     * <code>Double</code> 采样值。同一组参数下，给定相同的 <code>sampleIndex</code> 始终得到相同结果。
     *
     * <p>
     * 公共中间量
     * <ul>
     *     <li><code>phaseStep = waveTableFrequency / sampleFrequency</code></li>
     *     <li><code>phase = sampleIndex * phaseStep</code></li>
     *     <li><code>tableIndex = phase mod waveTable.length</code>，归一化到 <code>[0, waveTable.length)</code></li>
     * </ul>
     *
     * <p>
     * 插值规则
     * <ul>
     *     <li><code>step</code>：取 <code>floor(tableIndex)</code> 对应波表值，零阶保持。</li>
     *     <li>
     *         <code>linear</code>：取 <code>floor(tableIndex)</code> 与下一个波表点按小数部分线性插值，
     *         末尾点的下一个点回绕到首点。
     *     </li>
     * </ul>
     *
     * <p>
     * 最终输出为 <code>offset + amplitude * tableValue</code>，不对波表原始值做自动归一化。
     *
     * @param sampleIndex 从 0 开始递增的样本序号。
     * @return 该序号对应的波表采样值。
     */
    public Double generateValue(long sampleIndex) {
        double phaseStep = waveTableFrequency / sampleFrequency;
        double phase = sampleIndex * phaseStep;
        double tableIndex = normalizePhase(phase, waveTable.length);
        double tableValue = interpolateTableValue(tableIndex);
        return offset + amplitude * tableValue;
    }

    private double interpolateTableValue(double tableIndex) {
        int baseIndex = (int) Math.floor(tableIndex);
        if (SimulateAwgFetcherConstants.INTERPOLATION_STEP.equals(interpolation)) {
            return waveTable[baseIndex];
        }
        double fraction = tableIndex - baseIndex;
        int nextIndex = (baseIndex + 1) % waveTable.length;
        return waveTable[baseIndex] * (1.0 - fraction) + waveTable[nextIndex] * fraction;
    }

    /**
     * 将相位归一化到 <code>[0, length)</code>，负相位与超长相位均能正确回绕。
     */
    private double normalizePhase(double phase, int length) {
        double result = phase % length;
        if (result < 0) {
            result += length;
        }
        return result;
    }
}
