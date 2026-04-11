package org.example.mrmoutbound.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.example.mrmcore.engine.MorphismEngine;
import org.example.mrmcore.strategy.MorphismStrategy;
import org.example.mrmoutbound.fetcher.TradeFetcher;
import org.example.mrmoutbound.rule.RuleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OutboundServiceTest {

    private TradeFetcher fetcher;
    private RuleService ruleService;
    private MorphismEngine engine;
    private OutboundService outboundService;

    @BeforeEach
    void setUp() {
        fetcher = new TradeFetcher();
        ruleService = new RuleService();
        engine = new MorphismEngine(Map.of("DIRECT", (MorphismStrategy) (value, opts) -> value));
        outboundService = new OutboundService(fetcher, ruleService, engine);
    }

    @Test
    void test_正常なデータが変換されCSVに出力されること() throws IOException {
        String systemId = "SYS_B";
        Path outputPath = Files.createTempFile("mrm_out_test", ".csv");

        // csv出力処理を実行
        outboundService.export(systemId, outputPath);

        // BufferedReader を使って中身を検証
        try (BufferedReader br = Files.newBufferedReader(outputPath)) {
            String header = br.readLine();
            String dataLine1 = br.readLine();
            String dataLine2 = br.readLine();

            assertEquals("QTY,PRODUCT_CODE", header, "ヘッダーの内容が正しいこと");
            assertEquals("100,8001", dataLine1, "データ行の内容が正しいこと");
            assertEquals("50,8002", dataLine2, "データ行の内容が正しいこと");
        } finally {
            // テスト終了時に即座に削除
            Files.deleteIfExists(outputPath);
        }
    }
}