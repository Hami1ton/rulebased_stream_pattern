package org.example.rulebased.streaming.flinkdroolspattern;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;


public class FireDetector extends KeyedProcessFunction<Integer, SensorData, FireAlarm> {

    @Override
    public void processElement(
            SensorData sensorData,
            Context context,
            Collector<FireAlarm> collector) throws Exception {

        if(sensorData.getTemperature() > 1000) {
            FireAlarm alarm = new FireAlarm(
                sensorData.getDate()
                , sensorData.getTemperature()
            );
            collector.collect(alarm);
        }
    }
    
}
