package org.example.mrminbound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.example.mrmcore.engine.MorphismEngine;
import org.example.mrmcore.strategy.MorphismStrategy;
import org.example.mrminbound.model.Trade;
import org.example.mrminbound.reader.CsvReader;
import org.example.mrminbound.rule.RuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InboundServiceTest {

    private CsvReader csvReader;
    
    private RuleService ruleService;
    
    private MorphismEngine engine;

    private InboundService inboundService;

    @BeforeEach
    void setUp() {
        csvReader = new CsvReader();
        ruleService = new RuleService();
        engine = new MorphismEngine(Map.of("DIRECT", (MorphismStrategy) (value, opts) -> value));

        // テスト対象の Service に注入
        inboundService = new InboundService(csvReader, ruleService, engine);
    }

    @Test
    void test_CSVファイル単一行が正しく変換されること() {
        // 実際にプロジェクト内の test/resources 等に置いた CSV パスを指定
        Path path = Paths.get("mrm-inbound/src/test/resources/test_data_1.csv");
        String systemId = "SYS_A";

        List<Trade> result = inboundService.processFile(systemId, path);

        assertFalse(result.isEmpty());
        Trade firstTrade = result.get(0);
        
        // DRL で定義した通りの値が入っているか、実値でチェックする
        assertEquals("8001", firstTrade.getSymbol()); 
    }

    @Test
    void test_CSVファイル複数行が正しく変換されること() {
        // [正常系] 複数行のデータを含むCSVを読み込み、リストのサイズが一致することを確認
        Path path = Paths.get("mrm-inbound/src/test/resources/test_data_multi.csv");
        String systemId = "SYS_A";

        List<Trade> result = inboundService.processFile(systemId, path);

        assertEquals(3, result.size());
        assertEquals("8001", result.get(0).getSymbol());
        assertEquals("8002", result.get(1).getSymbol());
        assertEquals("8003", result.get(2).getSymbol());
    }

    @Test
    void test_ルールエンジンから指示が返らない場合でも空のTradeが生成されること() {
        // [正常系] RuleService が空の指示リストを返した場合の挙動を確認
        Path path = Paths.get("mrm-inbound/src/test/resources/test_data_1.csv");
        String systemId = "SYS_B"; // SYS_B はルールにマッチしない

        List<Trade> result = inboundService.processFile(systemId, path);

        assertEquals(1, result.size());
        Trade trade = result.get(0);
        assertNull(trade.getSymbol()); // 指示がないので symbol は null
    }

    @Test
    void test_CSV読み込みで例外が発生した際に適切にラップされた例外が投げられること() {
        // [異常系] CsvReader が IOException を投げた場合の Service の挙動を確認
        Path path = Paths.get("nonexistent.csv");

        assertThrows(RuntimeException.class, () -> inboundService.processFile("SYS_A", path));
    }

    @Test
    void test_CSVファイルがヘッダーのみの場合に空のリストが返されること() {
        // [境界値] ヘッダーのみのファイルを読み込んだ場合を確認
        Path path = Paths.get("mrm-inbound/src/test/resources/test_data_header_only.csv");
        String systemId = "SYS_A";

        List<Trade> result = inboundService.processFile(systemId, path);

        assertTrue(result.isEmpty());
    }

}