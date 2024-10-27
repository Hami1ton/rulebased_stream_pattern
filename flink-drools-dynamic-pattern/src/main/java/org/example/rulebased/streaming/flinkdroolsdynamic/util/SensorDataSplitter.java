package org.example.rulebased.streaming.flinkdroolsdynamic.util;

import java.time.Instant;
import java.util.Date;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.SensorData;


public class SensorDataSplitter implements FlatMapFunction<String, SensorData>{

        @Override
        public void flatMap(String sentence, Collector<SensorData> out) throws Exception {
            String[] words = sentence.split(",");
            
            out.collect(new SensorData(Integer.parseInt(words[0]), Date.from(Instant.now()), Integer.parseInt(words[1])));
        }
}
