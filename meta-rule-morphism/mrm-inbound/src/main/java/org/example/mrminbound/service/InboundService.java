package org.example.mrminbound.service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.example.mrmcore.engine.MorphismEngine;
import org.example.mrmcore.model.MorphismInstruction;
import org.example.mrminbound.model.Trade;
import org.example.mrminbound.reader.CsvReader;
import org.example.mrminbound.rule.RuleService;


public class InboundService {

    private final CsvReader csvReader;
    
    private final RuleService ruleService;

    private final MorphismEngine engine;

    public InboundService(CsvReader csvReader, RuleService ruleService, MorphismEngine engine) {
        this.csvReader = csvReader;
        this.ruleService = ruleService;
        this.engine = engine;
    }

    public List<Trade> processFile(String systemId, Path path) {
        // 1. CSV 読み込み
        List<Map<String, Object>> rows = csvReader.read(path);

        List<Trade> trades = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            // 2. 指示書の作成 (どの項目をどこに、どう入れるか)
            List<MorphismInstruction> insts = ruleService.evaluate(systemId, row.keySet());

            // 3. 変換実行 (Map -> Trade)
            Trade trade = new Trade();
            engine.execute(row, trade, insts);
            trades.add(trade);
        }

        return trades;
    }
}
