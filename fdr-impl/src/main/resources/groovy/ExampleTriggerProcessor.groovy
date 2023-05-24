import com.dwarfeng.fdr.impl.handler.trigger.GroovyTriggerRegistry
import com.dwarfeng.fdr.stack.exception.TriggerException
import com.dwarfeng.fdr.stack.handler.Trigger
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 示例触发器处理器。
 *
 * <p>
 * 如果数据的发生日期是偶数，则不触发，否则触发。
 *
 * @author Dwarfeng
 * @since 1.5.2
 */
@SuppressWarnings("GrPackage")
class ExampleTriggerProcessor implements GroovyTriggerRegistry.Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExampleTriggerProcessor.class)

    @Override
    Trigger.TestResult test(Trigger.TestInfo testInfo) throws TriggerException {
        // 获取数据的发生日期的时间戳，如果是偶数，则不触发。
        if (testInfo.getHappenedDate().getTime() % 2 == 0) {
            return Trigger.TestResult.NOT_TRIGGERED
        }

        // 否则触发。
        String message = "数据的发生日期是奇数，被触发。"
        LOGGER.debug("测试信息 {} 被触发, 原因: {}", testInfo, message)
        return Trigger.TestResult.triggered(message)
    }
}
