# Preset Mapper Implements - 预设映射器实现

## 说明

本文档详细说明了本项目内置的所有映射器。

所有内置数据源的代码均位于 `com.dwarfeng.fdr.impl.handler.mapper` 包的下属包中，
您可以通过查看该包下的所有类以及其子包下的所有类来了解本项目内置的所有映射器。

项目中所有的内置映射器如下：

| 名称                                                  | 说明               |
|-----------------------------------------------------|------------------|
| [AlignMapper](#alignmapper)                         | 对齐映射器            |
| [AvgMapper](#avgmapper)                             | 平均值映射器           |
| [CountMapper](#countmapper)                         | 计数映射器            |
| [EnableRatioMapper](#enableratiomapper)             | 使能比例映射器          |
| [FirstMapper](#firstmapper)                         | 首元素映射器           |
| [GroovyMapper](#groovymapper)                       | 使用 Groovy 脚本的过滤器 |
| [HighPassCounterMapper](#highpasscountermapper)     | 高通计数映射器          |
| [HighPassExistenceMapper](#highpassexistencemapper) | 高通存在映射器          |
| [HighPassMapper](#highpassmapper)                   | 高通映射器            |
| [IdentifyMapper](#identifymapper)                   | 本征映射器            |
| [LastMapper](#lastmapper)                           | 末元素映射器           |
| [LowPassCounterMapper](#lowpasscountermapper)       | 低通计数映射器          |
| [LowPassExistenceMapper](#lowpassexistencemapper)   | 低通存在映射器          |
| [LowPassMapper](#lowpassmapper)                     | 低通映射器            |
| [MergeMapper](#mergemapper)                         | 合并映射器            |
| [SortMapper](#sortmapper)                           | 排序映射器            |
| [TimeWeightedAgvMapper](#timeweightedagvmapper)     | 时间加权平均映射器        |
| [ToBooleanMapper](#tobooleanmapper)                 | 转换为布尔值的映射器       |
| [ToDoubleMapper](#todoublemapper)                   | 转换为双精度浮点数的映射器    |
| [TrimMapper](#trimmapper)                           | 裁剪映射器            |
| [WindowMapper](#windowmapper)                       | 开窗映射器            |

## AlignMapper

### 描述

```text
对序列中的所有数据条目进行对齐操作，修改序列中数据条目的发生时间，使其对齐到序列的起始时间与结束时间之间的某个时间。
该映射器的参数是一个介于 0 与 1 之间的浮点数，表示对齐的位置。
0 代表对齐到序列的起始时间；1代表对齐到序列的结束时间。
```

### 示例参数

```text
0.5(对齐到序列的中间时间)
```

## AvgMapper

### 描述

```text
对序列中的所有数据条目进行平均值计算，得到的值作为新的序列中的数据条目。新的数据条目的发生时间为序列开始时间和结束时间的中间值，并且数据条目的数据点主键会被替换为序列的数据点主键。
如果序列中没有数据条目，则平均值为 0。
如果序列的数据点主键为 null，则抛出异常。
如果序列中有任何一个数据条目的值是 null，或其类型不是 Number，则抛出异常。
该映射器将数据转换为 double 类型，因此，该映射器适用于精度要求不高且数据不越界的情况。
该映射器是聚合映射器。
```

### 示例参数

无参数。

## CountMapper

### 描述

```text
对序列中的所有数据条目进行计数。
```

### 示例参数

无参数。

## EnableRatioMapper

### 描述

```text
用于计算布尔类型数据的使能比例，即数据中真值的占用时间与序列的总时间的比值: 
invert 用于控制计算的是真值的比例还是假值的比例： 
  false：计算的是真值的比例 
  true：计算的是假值的比例 

```

### 示例参数

```json
{
  "#invert":"true：计算的是假值的比例;false：计算的是真值的比例",
  "invert":false
}
```

## FirstMapper

### 描述

```text
对序列中的所有数据条目进行操作，取出第一个数据条目作为新的序列中的数据条目列表中的元素。
取出的数据条目的发生时间为该序列的起始时间与结束时间的中间值，数据点主键的值为该序列的数据点主键的值。
该映射器是聚合映射器。
```

### 示例参数

```json
{
  "#empty_items_strategy":"0: 返回 VOID, 1: 返回 null",
  "empty_items_strategy":0
}
```

## GroovyMapper

### 描述

```text
通过自定义的groovy脚本，实现对带有时间数据的映射。
```

### 示例参数

```json
{
  "groovy_script":"groovy 脚本如下所示",
  "params":["0"]
}
```

以下内容为示例参数中 `groovy_script` 字段的示例：

```groovy
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
```

## HighPassCounterMapper

### 描述

```text
用于计算高于阈值的数据的个数: 
invert 用于控制计算的是低于阈值的数据还是高于阈值的数据: 
  false：过滤的是高于阈值的数据 
  true：过滤的是低于阈值的数据 
threshold用于过滤的阈值 
can_equal是否包含等于阈值的数据
```

### 示例参数

```json
{
  "#threshold":"阈值，对数据进行筛选的标准",
  "threshold":0.0,
  "#can_equal":"true：包含阈值，false：不包含阈值",
  "can_equal":true,
  "#invert":"true：过滤掉高于阈值的数据，false：过滤掉低于阈值的数据",
  "invert":false
}
```

## HighPassExistenceMapper

### 描述

```text
用于判断是否存在高于阈值的数据: 
invert 用于控制计算的是低于阈值的数据还是高于阈值的数据: 
  false：过滤的是高于阈值的数据 
  true：过滤的是低于阈值的数据 
threshold用于过滤的阈值 
can_equal是否包含等于阈值的数据
```

### 示例参数

```json
{
  "#threshold":"阈值，对数据进行筛选的标准",
  "threshold":0.0,
  "#can_equal":"true：包含阈值，false：不包含阈值",
  "can_equal":true,
  "#invert":"true：过滤掉高于阈值的数据，false：过滤掉低于阈值的数据",
  "invert":false
}
```

## HighPassMapper

### 描述

```text
用于保留高于阈值的数据: 
invert 用于控制计算的是低于阈值的数据还是高于阈值的数据: 
  false：过滤的是高于阈值的数据 
  true：过滤的是低于阈值的数据 
threshold用于过滤的阈值 
can_equal是否包含等于阈值的数据
```

### 示例参数

```json
{
  "#threshold":"阈值，对数据进行筛选的标准",
  "threshold":0.0,
  "#can_equal":"true：包含阈值，false：不包含阈值",
  "can_equal":true,
  "#invert":"true：过滤掉高于阈值的数据，false：过滤掉低于阈值的数据",
  "invert":false
}
```

## IdentifyMapper

### 描述

```text
不对数据进行任何处理，直接返回原始数据。
```

### 示例参数

无参数。

## LastMapper

### 描述

```text
对序列中的所有数据条目进行操作，取出最后一个数据条目作为新的序列中的数据条目列表中的元素。
取出的数据条目的发生时间为该序列的起始时间与结束时间的中间值，数据点主键的值为该序列的数据点主键的值。
该映射器是聚合映射器。
```

### 示例参数

无参数。

## LowPassCounterMapper

### 描述

```text
用于计算低于阈值的数据的个数: 
invert 用于控制计算的是低于阈值的数据还是高于阈值的数据: 
  false：过滤的是高于阈值的数据 
  true：过滤的是低于阈值的数据 
threshold用于过滤的阈值 
can_equal是否包含等于阈值的数据
```

### 示例参数

```json
{
  "#threshold":"阈值，对数据进行筛选的标准",
  "threshold":0.0,
  "#can_equal":"true：包含阈值，false：不包含阈值",
  "can_equal":true,
  "#invert":"true：过滤掉低于阈值的数据，false：过滤掉高于阈值的数据",
  "invert":false
}
```

## LowPassExistenceMapper

### 描述

```text
用于判断是否存在低于阈值的数据: 
invert 用于控制计算的是低于阈值的数据还是高于阈值的数据: 
  false：过滤的是低于阈值的数据 
  true：过滤的是高于阈值的数据 
threshold用于过滤的阈值 
can_equal是否包含等于阈值的数据
```

### 示例参数

```json
{
  "#threshold":"阈值，对数据进行筛选的标准",
  "threshold":0.0,
  "#can_equal":"true：包含阈值，false：不包含阈值",
  "can_equal":true,
  "#invert":"true：过滤掉低于阈值的数据，false：过滤掉高于阈值的数据",
  "invert":false
}
```

## LowPassMapper

### 描述

```text
用于保留高于阈值的数据: 
invert 用于控制计算的是低于阈值的数据还是高于阈值的数据: 
  false：过滤的是高于阈值的数据 
  true：过滤的是低于阈值的数据 
threshold用于过滤的阈值 
can_equal是否包含等于阈值的数据
```

### 示例参数

```json
{
  "#threshold":"阈值，对数据进行筛选的标准",
  "threshold":0.0,
  "#can_equal":"true：包含阈值，false：不包含阈值",
  "can_equal":true,
  "#invert":"true：过滤掉低于阈值的数据，false：过滤掉高于阈值的数据",
  "invert":false
}
```

## MergeMapper

### 描述

```text
将 Sequence.getPointKey() 相同的序列进行合并。
合并后的序列起始时间与结束时间分别为合并序列中最早的起始时间与最晚的结束时间。
```

### 示例参数

无参数。

## SortMapper

### 描述

```text
对序列列表按照 Sequence.getPointKey() 进行排序，同时对序列中的所有数据条目进行排序。
排序方式由参数决定。
```

### 示例参数

```json
{
  "#sequence_point_key_order":"0: 升序, 1: 降序",
  "sequence_point_key_order":0,
  "#item_happened_date_order":"0: 升序, 1: 降序",
  "item_happened_date_order":0
}
```

## TimeWeightedAgvMapper

### 描述

```text
对序列中的所有数据条目进行加权平均值计算，得到的值作为新的序列中的数据条目。新的数据条目的发生时间为序列开始时间和结束时间的中间值，并且数据条目的数据点主键会被替换为序列的数据点主键。
如果序列中没有数据条目，则平均值为 0。
如果序列的数据点主键为 null，则抛出异常。
如果序列中有任何一个数据条目的值是 null，或其类型不是 Number，则抛出异常。
该映射器将数据转换为 double 类型，因此，该映射器适用于精度要求不高且数据不越界的情况。
该映射器是聚合映射器。
```

### 示例参数

无参数。

## ToBooleanMapper

### 描述

```text
保留数据表中所有序列的开始时间和结束时间。对每个序列的数据条目做映射处理: 
是否为严格模式： 
  是：只有严格匹配真值或者假值的数据才会被转换为布尔类型的数据，如果序列中存在其它值的则抛出 IllegalArgumentException 异常 
    字符串真值：true 
    字符串假值:false 
    数字真值：1.0 
    数字假值：0.0 
  否：数据匹配真值时，会被转换为布尔类型的真值，其余任何值都会被转换为布尔类型的假值 
```

### 示例参数

```json
{
  "#strict":"true：启用严格模式，不符合直接抛出异常; false：不启用严格模式，不符合转为false",
  "strict":false,
  "#string_ignore_case":"true：忽略字符串大小写，false：不忽略大小写",
  "string_ignore_case":true,
  "#specification":"    字符串真值：true \n    字符串假值:false \n    数字真值：1.0 \n    数字假值：0.0 \n"
}
```

## ToDoubleMapper

### 描述

```text
保留数据表中所有序列的开始时间和结束时间。对每个序列的数据条目做映射处理: 
1. 如果数据条目的值的类型是 Number，那么获得其双精度浮点数值。 
2. 如果数据条目的值的类型是 String，那么尝试将其转换为双精度浮点数。 
3. 如果数据条目的值的类型是布尔值，那么根据配置对布尔值进行映射。 
4. 对于其它的情况，可以进行策略配置，映射为默认值、或 null、或忽略该数据条目。
```

### 示例参数

```json
{
  "boolean_true_value":1.0,
  "boolean_false_value":0.0,
  "#other_type_strategy":"0: 默认值, 1: null, 2: 忽略该数据条目。",
  "other_type_strategy":0,
  "other_type_default_value":0.0
}
```

## TrimMapper

### 描述

```text
映射器工作时会寻找序列中发生时间最早和最晚的数据，然后将这两个数据的发生时间作为序列的起始时间和结束时间,若使用 only_trim_start 配置项可以只裁剪序列的起始时间。
```

### 示例参数

```json
{
  "#only_trim_start":"当 onlyTrimStart 为 true 时只剪裁序列的起始时间，false 裁剪序列的起始时间和结束时间",
  "only_trim_start":false
}
```

## WindowMapper

### 描述

```text
根据窗口大小、锚点时间，针对每个序列进行开窗操作，将每个序列分为若干个长度为窗口大小的窗口，每个窗口的起始时间与锚点时间的距离都是窗口步长的整数倍
```

### 示例参数

```json
{
  "duration":600000,
  "anchor_timestamp":0,
  "#remove_edges":"每个序列开窗后是否去除第一个和最后一个窗口。在部分情况下，第一个和最后一个窗口的数据可能不完整，可能会导致后续的聚合操作出现不准确的结果。因此，可以通过设置该参数为 true 来去除第一个和最后一个窗口。",
  "remove_edges":false,
  "#extend_item":"是否扩展每个窗口的数据条目。如果该参数为 true，则会在每个窗口（第一个窗口除外）的起始时间处插入一个数据条目，其值为上一个窗口的最后一个数据条目的值；同时会在每个窗口（最后一个窗口除外）的结束时间处插入一个数据条目，其值为下一个窗口的第一个数据条目的值。将该参数设置为 true 可以提高部分聚合映射器的准确性，如加权平均值映射器。",
  "extend_item":false
}
```
