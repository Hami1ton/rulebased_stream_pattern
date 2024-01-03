package org.example.rulebased.streaming.study.flink.fireDetected;

import java.util.Date;

public class SensorData {

    public int id;

    // yyyy-MM-ddTHH:mm:ss.SSS
    public Date date;

    public int temperature;
   
    public SensorData() {}

    public SensorData(int id, Date date, int temperature) {
        this.id = id;
        this.date = date;
        this.temperature = temperature;
    }

    public int getId() {
        return this.id;
    }

    public Date getDate() {
        return this.date;
    }


    public int getTemperature() {
        return this.temperature;
    }

    public String toString() {
        return "SensorData(id=" + this.id + ",temperature=" + this.temperature + ")";
    }

}
