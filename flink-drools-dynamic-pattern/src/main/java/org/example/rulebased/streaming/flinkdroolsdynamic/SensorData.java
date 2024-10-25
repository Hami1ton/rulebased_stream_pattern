package org.example.rulebased.streaming.flinkdroolsdynamic;

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

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTemperature() {
        return this.temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String toString() {
        return "SensorData(id=" + this.id + ",date=" + this.date +",temperature=" + this.temperature + ")";
    }
}
