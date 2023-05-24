import com.dwarfeng.fdr.impl.handler.filter.GroovyFilterRegistry
import com.dwarfeng.fdr.stack.exception.FilterException
import com.dwarfeng.fdr.stack.handler.Filter
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 示例过滤器处理器。
 *
 * <p>
 * 如果数据的发生日期是奇数，则不过滤，否则过滤。
 *
 * @author Dwarfeng
 * @since 1.5.2
 */
@SuppressWarnings("GrPackage")
class ExampleFilterProcessor implements GroovyFilterRegistry.Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleFilterProcessor.class)

    @Override
    Filter.TestResult test(Filter.TestInfo testInfo) throws FilterException {
        // 获取数据的发生日期的时间戳，如果是奇数，则不过滤。
        if (testInfo.getHappenedDate().getTime() % 2 == 1) {
            return Filter.TestResult.NOT_FILTERED
        }

        // 否则过滤。
        String message = "数据的发生日期是偶数，被过滤。"
        LOGGER.debug("测试信息 {} 被过滤, 原因: {}", testInfo, message)
        return Filter.TestResult.filtered(message)
    }
}
