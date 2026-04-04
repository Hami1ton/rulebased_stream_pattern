package org.example.mrminbound.reader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvReader {

    public List<Map<String, Object>> read(Path path) {
        
        List<Map<String, Object>> results = new ArrayList<>();

        // Builderパターンでフォーマットを定義
        CSVFormat format = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .get();

        // format.parse(reader) を使用してパーサーを取得する
        try (Reader reader = Files.newBufferedReader(path);
             CSVParser parser = format.parse(reader)) {

            for (CSVRecord record : parser) {
                // record.toMap() でヘッダー名をキーにした Map を生成
                results.add(new HashMap<>(record.toMap()));
            }

        } catch (IOException e) {
            throw new RuntimeException("CSVの解析に失敗しました: " + path, e);
        }

        return results;
    }
}