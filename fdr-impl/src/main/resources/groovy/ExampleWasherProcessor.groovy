import com.dwarfeng.fdr.impl.handler.washer.GroovyWasherRegistry

/**
 * 示例清洗器处理器。
 *
 * <p>
 * 该处理器将使用 {@link Objects#toString(Object)} 方法将原始值转换为字符串。
 *
 * @author Dwarfeng
 * @since 2.0.0
 */
@SuppressWarnings("GrPackage")
class ExampleWasherProcessor implements GroovyWasherRegistry.Processor {

    @Override
    Object wash(Object rawValue) throws Exception {
        return Objects.toString(rawValue)
    }
}
