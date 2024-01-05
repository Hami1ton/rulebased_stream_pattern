package org.example.rulebased.streaming.flinkdroolspattern;

import java.util.Date;


public class FireAlarm {

    // yyyy-MM-ddTHH:mm:ss.SSS
    public Date alarmDate;

    public int temperature;

    public FireAlarm() {}

    public FireAlarm(Date alarmDate, int temperature) {
        this.alarmDate = alarmDate;
        this.temperature = temperature;
    }

    public int getTemperature() {
        return temperature;
    }

    public String toString() {
        return "FireAlarm(alarmDate=" + this.alarmDate + ",temperature=" + this.temperature + ")";
    }
    
}
