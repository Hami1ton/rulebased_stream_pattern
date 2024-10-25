package org.example.rulebased.streaming.flinkdroolsdynamic;

import java.time.Instant;
import java.util.Date;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;


public class FireDetectJob {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Data Sources
        DataStream<String> dataStream = env.socketTextStream("localhost", 9999);

        // DataStream Transformations(Operators)
        DataStream<FireAlarm> alarms = dataStream
            .flatMap(new Splitter())
            .keyBy(value -> value.id)
            .process(new FireDetector())
            .name("fire-detector");

        // Data Sinks
        alarms.print();

        env.execute();
    }

    public static class Splitter implements FlatMapFunction<String, SensorData> {
        @Override
        public void flatMap(String sentence, Collector<SensorData> out) throws Exception {
            String[] words = sentence.split(",");
            
            out.collect(new SensorData(Integer.parseInt(words[0]), Date.from(Instant.now()), Integer.parseInt(words[1])));
        }
    }
}
