package org.example.rulebased.streaming.study.flink.fireDetect;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;


public class FireDetector extends KeyedProcessFunction<Integer, SensorData, Alarm> {

    @Override
    public void processElement(
            SensorData sensorData,
            Context context,
            Collector<Alarm> collector) throws Exception {

        if(sensorData.getTemperature() > 1000) {
            Alarm alarm = new Alarm(
                sensorData.getDate()
                , sensorData.getTemperature()
            );
            collector.collect(alarm);
        }
    }

}
