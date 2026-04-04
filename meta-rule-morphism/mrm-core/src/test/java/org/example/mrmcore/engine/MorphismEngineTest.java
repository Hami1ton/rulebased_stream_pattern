package org.example.mrmcore.engine;

import org.example.mrmcore.model.MorphismInstruction;
import org.example.mrmcore.strategy.MorphismStrategy;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class MorphismEngineTest {

    @Test
    void test_MapからJavaBeanへのマッピング_集信() {
        Map<String, Object> source = Map.of("item_nm", "ABC123");
        TestDataDTO target = new TestDataDTO();

        MorphismInstruction instruction = new MorphismInstruction("item_nm", "productId", "DIRECT", Map.of());
        MorphismEngine engine = new MorphismEngine(Map.of("DIRECT", (MorphismStrategy) (value, opts) -> value));

        engine.execute(source, target, List.of(instruction));

        assertEquals("ABC123", target.getProductId());
    }

    @Test
    void test_JavaBeanからMapへのマッピング_配信() {
        TestDataDTO source = new TestDataDTO();
        source.setProductId("XYZ987");
        Map<String, Object> target = new HashMap<>();

        MorphismInstruction instruction = new MorphismInstruction("productId", "ext_code", "DIRECT", Map.of());
        MorphismEngine engine = new MorphismEngine(Map.of("DIRECT", (MorphismStrategy) (value, opts) -> value));

        engine.execute(source, target, List.of(instruction));

        assertEquals("XYZ987", target.get("ext_code"));
    }

    @Test
    void test_オプション付きの戦略の適用() {
        MorphismStrategy suffixStrategy = (value, opts) -> value.toString() + opts.get("ext");

        Map<String, MorphismStrategy> strategies = Map.of("SUFFIX", suffixStrategy);
        MorphismEngine engine = new MorphismEngine(strategies);

        Map<String, Object> source = Map.of("item_nm", "ABC");
        TestDataDTO target = new TestDataDTO();
        MorphismInstruction instruction = new MorphismInstruction("item_nm", "productId", "SUFFIX", Map.of("ext", "-TEST"));

        engine.execute(source, target, List.of(instruction));

        assertEquals("ABC-TEST", target.getProductId());
    }

    @Test
    void test_フィールドが見つからない場合にRuntimeExceptionがスローされること() {
        Map<String, Object> source = Map.of("item_nm", "ABC");
        TestDataDTO target = new TestDataDTO();

        // source に存在する field から読み出して、存在しない target フィールドに書き込みを試み
        MorphismInstruction instruction = new MorphismInstruction("item_nm", "invalid_field", "DIRECT", Map.of());
        MorphismEngine engine = new MorphismEngine(Map.of("DIRECT", (MorphismStrategy) (value, opts) -> value));

        assertThrows(RuntimeException.class, () -> engine.execute(source, target, List.of(instruction)));
    }

    @Test
    void test_戦略IDが未登録の場合のフォールバック() {
        Map<String, Object> source = Map.of("item_nm", "FALLBACK");
        TestDataDTO target = new TestDataDTO();

        MorphismInstruction instruction = new MorphismInstruction("item_nm", "productId", "UNKNOWN", Map.of());
        MorphismEngine engine = new MorphismEngine(Map.of());

        engine.execute(source, target, List.of(instruction));

        assertEquals("FALLBACK", target.getProductId());
    }
}
