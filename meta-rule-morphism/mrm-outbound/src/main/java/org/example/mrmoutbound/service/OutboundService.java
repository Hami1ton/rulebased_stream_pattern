package org.example.mrmoutbound.service;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.example.mrmcore.engine.MorphismEngine;
import org.example.mrmcore.model.MorphismInstruction;
import org.example.mrmoutbound.fetcher.TradeFetcher;
import org.example.mrmoutbound.model.Trade;
import org.example.mrmoutbound.rule.RuleService;

public class OutboundService {
    private final TradeFetcher fetcher;
    private final RuleService ruleService;
    private final MorphismEngine engine;

    public OutboundService(TradeFetcher fetcher, RuleService ruleService, MorphismEngine engine) {
        this.fetcher = fetcher;
        this.ruleService = ruleService;
        this.engine = engine;
    }

    public void export(String systemId, Path outputPath) {
        List<Trade> trades = fetcher.fetchAll();
        List<Map<String, Object>> outRows = new ArrayList<>();

        Set<String> allFields = Arrays.stream(Trade.class.getDeclaredFields())
            .map(Field::getName)
            .collect(Collectors.toSet());

        // 1. Trade -> Map への逆変換
        for (Trade trade : trades) {
            Map<String, Object> row = new HashMap<>();
            // 出力項目を決めるためにルールを評価 (Tradeのフィールド一覧を渡す)
            List<MorphismInstruction> insts = ruleService.evaluate(systemId, allFields);
            
            // エンジン実行 (Trade から Map へ)
            engine.execute(trade, row, insts);
            outRows.add(row);
        }

        // 2. CSV 出力
        writeCsv(outRows, outputPath);
    }

    private void writeCsv(List<Map<String, Object>> rows, Path path) {
        if (rows.isEmpty()) return;
        
        // Mapのキーをヘッダーにする
        String[] headers = rows.get(0).keySet().toArray(new String[0]);
        CSVFormat format = CSVFormat.DEFAULT.builder().setHeader(headers).get();

        try (FileWriter out = new FileWriter(path.toFile());
            CSVPrinter printer = new CSVPrinter(out, format)) {
            for (Map<String, Object> row : rows) {
                printer.printRecord(row.values());
            }

        } catch (IOException e) {
            throw new RuntimeException("CSV出力に失敗しました", e);
        }
    }
}