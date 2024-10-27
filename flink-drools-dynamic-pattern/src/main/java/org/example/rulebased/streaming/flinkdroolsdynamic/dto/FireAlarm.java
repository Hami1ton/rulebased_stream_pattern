package org.example.rulebased.streaming.flinkdroolsdynamic.dto;

import java.util.Date;


public class FireAlarm {

    public int id;

    // yyyy-MM-ddTHH:mm:ss.SSS
    public Date alarmDate;

    public int temperature;

    public FireAlarm() {}

    public FireAlarm(int id, Date alarmDate, int temperature) {
        this.id = id;
        this.alarmDate = alarmDate;
        this.temperature = temperature;
    }

    public int getId() {
        return id;
    }

    public int getTemperature() {
        return temperature;
    }

    public String toString() {
        return "FireAlarm(id=" + this.id +",alarmDate=" + this.alarmDate + ",temperature=" + this.temperature + ")";
    }
    
}
