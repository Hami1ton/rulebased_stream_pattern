package org.example.rulebased.streaming.flinkdroolsdynamic;

import java.time.Instant;
import java.util.Date;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.FireAlarm;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.SensorData;


public class FireDetectJob {

    public void run() throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        var fireDetecter = new FireDetector();

        // Data Sources
        DataStream<String> sensorDatas = env.socketTextStream("localhost", 9999);

        // DataStream Transformations(Operators)
        DataStream<FireAlarm> alarms = createAlarmStream(fireDetecter, sensorDatas);

        // Data Sinks
        alarms.print();

        env.execute();
    }

    private void createRuleStream() {

    }

    private DataStream<FireAlarm> createAlarmStream(FireDetector detector, DataStream<String> sensorDatas) {
        DataStream<FireAlarm> alarms = sensorDatas
            .flatMap(new Splitter())
            .keyBy(value -> value.id)
            .process(detector)
            .name("fire-detector");

        return alarms;

    }

    public static class Splitter implements FlatMapFunction<String, SensorData> {
        @Override
        public void flatMap(String sentence, Collector<SensorData> out) throws Exception {
            String[] words = sentence.split(",");
            
            out.collect(new SensorData(Integer.parseInt(words[0]), Date.from(Instant.now()), Integer.parseInt(words[1])));
        }
    }

}
