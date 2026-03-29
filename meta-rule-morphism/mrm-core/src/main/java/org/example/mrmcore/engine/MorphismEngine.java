package org.example.mrmcore.engine;

import org.apache.commons.beanutils.PropertyUtils;
import org.example.mrmcore.model.MorphismInstruction;
import org.example.mrmcore.strategy.MorphismStrategy;

import java.util.List;
import java.util.Map;

public class MorphismEngine {

    private final Map<String, MorphismStrategy> strategies;

    public MorphismEngine(Map<String, MorphismStrategy> strategies) {
        this.strategies = strategies;
    }

    public void execute(Object source, Object target, List<MorphismInstruction> instructions) {
        for (MorphismInstruction inst : instructions) {
            apply(source, target, inst);
        }
    }

    private void apply(Object source, Object target, MorphismInstruction inst) {
        try {
            // 1. 指示書に記載された sourceField から値を取得
            Object value = getValue(source, inst.sourceField());

            // 2. 戦略適用
            MorphismStrategy strategy = strategies.getOrDefault(inst.strategyId(), (v, o) -> v);
            Object transformed = strategy.apply(value, inst.options());

            // 3. 指示書に記載された targetField へ値を書き込み
            setValue(target, inst.targetField(), transformed);
            
        } catch (Exception e) {
            throw new RuntimeException(String.format("Morphism failed: [%s] -> [%s]", 
                inst.sourceField(), inst.targetField()), e);
        }
    }

    private Object getValue(Object obj, String field) throws Exception {
        if (obj == null || field == null) return null;

        return (obj instanceof Map) 
            ? ((Map<?, ?>) obj).get(field) 
            : PropertyUtils.getNestedProperty(obj, field);
    }

    @SuppressWarnings("unchecked")
    private void setValue(Object obj, String field, Object value) throws Exception {
        if (obj == null || field == null) return;

        if (obj instanceof Map) {
            ((Map<String, Object>) obj).put(field, value);
        } else {
            PropertyUtils.setNestedProperty(obj, field, value);
        }
    }
}