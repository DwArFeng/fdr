import com.dwarfeng.fdr.impl.handler.mapper.GroovyMapperRegistry
import com.dwarfeng.fdr.stack.handler.Mapper

import static com.dwarfeng.fdr.stack.handler.Mapper.Item

/**
 * 截取 timedValues 前几个值的映射器脚本。
 *
 * <p>
 * 截取的个数由 params[0] 确定。
 *
 * @author Dwarfeng
 * @since 1.6.0
 */
@SuppressWarnings('GrPackage')
class ExampleMapperProcessor implements GroovyMapperRegistry.Processor {

    @Override
    List<Mapper.Sequence> map(String[] params, List<Mapper.Sequence> sequences) throws Exception {
        // 对每一个序列进行 map 操作。
        List<Mapper.Sequence> result = new ArrayList<>()
        for (Mapper.Sequence sequence : sequences) {
            result.add(mapSingleSequence(params, sequence))
        }
        return result
    }

    @SuppressWarnings('GrMethodMayBeStatic')
    private Mapper.Sequence mapSingleSequence(String[] params, Mapper.Sequence sequence) throws Exception {
        // 获取数据条目列表。
        List<Item> items = sequence.getItems()

        // 根据 params[0] 的值，对数据条目进行截取操作。
        int size = params[0] as int
        size = Math.min(items.size(), size)
        items = items.subList(0, size)

        // 返回新的序列。
        return new Mapper.Sequence(
                sequence.getPointKey(), Collections.unmodifiableList(items),
                sequence.getStartDate(), sequence.getEndDate()
        )
    }
}
