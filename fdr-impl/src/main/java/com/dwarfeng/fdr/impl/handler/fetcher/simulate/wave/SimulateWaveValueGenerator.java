package com.dwarfeng.fdr.impl.handler.fetcher.simulate.wave;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 函数波形模拟抓取器值生成器。
 *
 * <p>
 * 根据样本序号与波形参数计算 <code>Double</code> 采样值，用于可解释、可复现的基础函数波形仿真。
 *
 * @author DwArFeng
 * @since 3.1.0
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public final class SimulateWaveValueGenerator {

    private final long sampleFrequency;
    private final String waveType;
    private final double wavePeriod;
    private final double amplitude;
    private final double offset;
    private final double dutyCycle;

    public SimulateWaveValueGenerator(
            long sampleFrequency, String waveType, double wavePeriod, double amplitude, double offset, double dutyCycle
    ) {
        this.sampleFrequency = sampleFrequency;
        this.waveType = waveType;
        this.wavePeriod = wavePeriod;
        this.amplitude = amplitude;
        this.offset = offset;
        this.dutyCycle = dutyCycle;
    }

    /**
     * 按样本序号生成波形采样值。
     *
     * <p>
     * 本方法根据构造时注入的采样频率、波形类型及波形参数，将离散样本序号映射为 <code>Double</code> 采样值。
     * 同一组参数下，给定相同的 <code>sampleIndex</code> 始终得到相同结果，用于可解释、可复现的函数波形仿真。
     *
     * <p>
     * 构造参数（实例字段）
     * <table>
     *     <tr>
     *         <th>字段</th>
     *         <th>指标名</th>
     *         <th>说明</th>
     *     </tr>
     *     <tr>
     *         <td><code>sampleFrequency</code></td>
     *         <td><code>sample_frequency</code></td>
     *         <td>采样频率，必须大于 0。</td>
     *     </tr>
     *     <tr>
     *         <td><code>waveType</code></td>
     *         <td><code>wave_type</code></td>
     *         <td>波形类型：<code>sine</code>、<code>cosine</code>、<code>square</code>、
     *             <code>triangle</code>、<code>sawtooth</code>、<code>dc</code>。</td>
     *     </tr>
     *     <tr>
     *         <td><code>wavePeriod</code></td>
     *         <td><code>wave_period</code></td>
     *         <td>波形自身周期；<code>dc</code> 时不参与计算。</td>
     *     </tr>
     *     <tr>
     *         <td><code>amplitude</code></td>
     *         <td><code>amplitude</code></td>
     *         <td>幅值，必须大于等于 0；<code>dc</code> 时不参与计算。</td>
     *     </tr>
     *     <tr>
     *         <td><code>offset</code></td>
     *         <td><code>offset</code></td>
     *         <td>偏置；所有波形（含 <code>dc</code>）均叠加该常量。</td>
     *     </tr>
     *     <tr>
     *         <td><code>dutyCycle</code></td>
     *         <td><code>duty_cycle</code></td>
     *         <td>方波占空比，范围 <code>(0, 1)</code>；仅 <code>square</code> 使用。</td>
     *     </tr>
     * </table>
     *
     * <p>
     * 公共中间量（非 <code>dc</code> 波形）
     * <p>
     * 设输入参数 <code>sampleIndex</code> 为从 0 开始递增的样本序号，则：
     * <ul>
     *     <li><code>elapsedMillis = sampleIndex * 1000 / sampleFrequency</code> — 样本相对基线的时间。</li>
     *     <li><code>cycle = elapsedMillis / wavePeriod</code> — 无量纲周期计数。</li>
     *     <li><code>theta = 2 * PI * cycle</code> — 相位角（弧度），用于正弦/余弦。</li>
     *     <li>
     *         <code>fraction = cycle - floor(cycle)</code> — 当前周期内的归一化相位，取值 <code>[0, 1)</code>，
     *         用于方波、三角波、锯齿波。
     *     </li>
     * </ul>
     *
     * <p>
     * 各波形输出规则
     * <table>
     *     <tr>
     *         <th>wave_type</th>
     *         <th>输出公式</th>
     *         <th>说明</th>
     *     </tr>
     *     <tr>
     *         <td><code>dc</code></td>
     *         <td><code>offset</code></td>
     *         <td>
     *             固定直流；忽略 <code>amplitude</code>、<code>wavePeriod</code>、<code>dutyCycle</code>，
     *             不计算 <code>elapsedMillis</code>、<code>theta</code>、<code>fraction</code>。
     *         </td>
     *     </tr>
     *     <tr>
     *         <td><code>sine</code></td>
     *         <td><code>offset + amplitude * sin(theta)</code></td>
     *         <td>标准正弦波。</td>
     *     </tr>
     *     <tr>
     *         <td><code>cosine</code></td>
     *         <td><code>offset + amplitude * cos(theta)</code></td>
     *         <td>标准余弦波。</td>
     *     </tr>
     *     <tr>
     *         <td><code>square</code></td>
     *         <td>
     *             若 <code>fraction &lt; dutyCycle</code> 则 <code>offset + amplitude</code>，
     *             否则 <code>offset - amplitude</code>
     *         </td>
     *         <td>双电平方波；高电平持续占空比 <code>dutyCycle</code>，低电平为负幅值区间。</td>
     *     </tr>
     *     <tr>
     *         <td><code>triangle</code></td>
     *         <td><code>offset + amplitude * (1 - 4 * abs(fraction - 0.5))</code></td>
     *         <td>周期内线性升降；归一化因子 <code>(1 - 4 * abs(fraction - 0.5))</code>
     *             在 <code>[-1, 1]</code> 内变化。</td>
     *     </tr>
     *     <tr>
     *         <td><code>sawtooth</code></td>
     *         <td><code>offset + amplitude * (2 * fraction - 1)</code></td>
     *         <td>周期内从 <code>-amplitude</code> 线性增至 <code>+amplitude</code>（相对偏置），
     *             即归一化因子 <code>2 * fraction - 1</code> 取值 <code>[-1, 1]</code>。</td>
     *     </tr>
     * </table>
     *
     * <p>
     * 边界与异常
     * <ul>
     *     <li>
     *         <code>sampleIndex</code> 为负时，<code>elapsedMillis</code> 为负，三角/锯齿/方波仍按上述公式计算，
     *         行为由调用方保证序号非负；会话补样路径从 0 递增。
     *     </li>
     *     <li>不支持的 <code>waveType</code> 将抛出 <code>IllegalArgumentException</code>。</li>
     *     <li>返回值类型固定为 <code>Double</code>，与抓取器输出类型约定一致。</li>
     * </ul>
     *
     * @param sampleIndex 从 0 开始递增的样本序号。
     * @return 该序号对应的波形采样值。
     */
    public Double generateValue(long sampleIndex) {
        if (SimulateWaveFetcherConstants.WAVE_TYPE_DC.equals(waveType)) {
            return offset;
        }
        double elapsedMillis = (double) sampleIndex * 1000.0 / sampleFrequency;
        double cycle = elapsedMillis / wavePeriod;
        double fraction = cycle - Math.floor(cycle);
        double theta = 2.0 * Math.PI * cycle;
        switch (waveType) {
            case SimulateWaveFetcherConstants.WAVE_TYPE_SINE:
                return offset + amplitude * Math.sin(theta);
            case SimulateWaveFetcherConstants.WAVE_TYPE_COSINE:
                return offset + amplitude * Math.cos(theta);
            case SimulateWaveFetcherConstants.WAVE_TYPE_SQUARE:
                return fraction < dutyCycle ? offset + amplitude : offset - amplitude;
            case SimulateWaveFetcherConstants.WAVE_TYPE_TRIANGLE:
                return offset + amplitude * (1.0 - 4.0 * Math.abs(fraction - 0.5));
            case SimulateWaveFetcherConstants.WAVE_TYPE_SAWTOOTH:
                return offset + amplitude * (2.0 * fraction - 1.0);
            default:
                throw new IllegalArgumentException("Unsupported wave type: " + waveType);
        }
    }
}
