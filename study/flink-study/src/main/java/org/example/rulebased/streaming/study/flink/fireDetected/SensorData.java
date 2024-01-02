package org.example.rulebased.streaming.study.flink.fireDetected;


public class SensorData {

    public int temperature;
   
    public SensorData() {}

    public SensorData(int temperature) {
        this.temperature = temperature;
    }

    public String toString() {
        return "SensorData(temperature=" + this.temperature + ")";
    }

}
