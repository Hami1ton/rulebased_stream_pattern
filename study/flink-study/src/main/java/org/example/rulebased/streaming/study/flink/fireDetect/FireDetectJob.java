package org.example.rulebased.streaming.study.flink.fireDetect;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;


public class FireDetectJob {

    public static void executeJob() throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // source
        DataStream<SensorData> flintstones = env.fromElements(
            new SensorData(1, DateUtils.parseDate("2024-01-04 23:20:02.000", "yyyy-MM-dd HH:mm:ss.SSS"), 30)
            , new SensorData(2, DateUtils.parseDate("2024-01-04 23:20:03.000", "yyyy-MM-dd HH:mm:ss.SSS"), 1000)
            , new SensorData(3, DateUtils.parseDate("2024-01-04 23:20:04.000", "yyyy-MM-dd HH:mm:ss.SSS"), 1100)
        );

        // operatore
        DataStream<Alarm> alarms = flintstones
            .keyBy(SensorData::getId)
            .process(new FireDetector())
            .name("fire-detector");

        // sink
        alarms.print();

        env.execute();
    }
}
