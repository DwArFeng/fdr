package com.dwarfeng.fdr.impl.handler.bridge.mock;

import com.alibaba.fastjson.JSON;
import com.dwarfeng.subgrade.stack.bean.key.LongIdKey;
import com.dwarfeng.subgrade.stack.exception.HandlerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 模拟桥接数据值生成器。
 *
 * @author DwArFeng
 * @since 2.0.0
 */
@Component
public class MockBridgeDataValueGenerator {

    private final MockBridgeRandomGenerator randomGenerator;

    @Value("${bridge.mock.data_config}")
    private String dataConfig;

    private Map<LongIdKey, MockBridgeDataConfigItem> dataConfigItemMap = null;
    private Map<String, Method> methodMap = null;

    public MockBridgeDataValueGenerator(MockBridgeRandomGenerator randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    @PostConstruct
    public void init() {
        // 将 dataConfig 转换为 MockSourceDataConfigItem 的列表。
        List<MockBridgeDataConfigItem> dataConfigItems = JSON.parseArray(dataConfig, MockBridgeDataConfigItem.class);
        dataConfigItemMap = dataConfigItems.stream().collect(
                Collectors.toMap((item) -> new LongIdKey(item.getPointId()), Function.identity())
        );
        // 构造 methodMap。
        methodMap = new HashMap<>();
        // 扫描 MockBridgeRandomGenerator 的所有带有 RequiredPointType 注解的方法，将其放入 methodMap。
        for (Method method : MockBridgeRandomGenerator.class.getMethods()) {
            if (!method.isAnnotationPresent(RequiredPointType.class)) {
                continue;
            }
            RequiredPointType requiredPointType = method.getAnnotation(RequiredPointType.class);
            methodMap.put(requiredPointType.value(), method);
        }
    }

    public Object nextValue(LongIdKey pointKey) throws Exception {
        if (!dataConfigItemMap.containsKey(pointKey)) {
            throw new HandlerException("未知的点位: " + pointKey);
        }
        MockBridgeDataConfigItem dataConfigItem = dataConfigItemMap.get(pointKey);
        Method method = methodMap.get(dataConfigItem.getPointType());
        if (method == null) {
            throw new HandlerException("未知的点位类型: " + dataConfigItem.getPointType());
        }
        return method.invoke(randomGenerator);
    }
}
