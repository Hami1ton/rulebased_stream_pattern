package org.example.rulebased.streaming.study.flink.fireDetect;

import java.util.Date;


public class Alarm {
    
    // yyyy-MM-ddTHH:mm:ss.SSS
    public Date alarmDate;

    public int temperature;

    public Alarm() {}

    public Alarm(Date alarmDate, int temperature) {
        this.alarmDate = alarmDate;
        this.temperature = temperature;
    }

    public String toString() {
        return "Alarm(alarmDate=" + this.alarmDate + ",temperature=" + this.temperature + ")";
    }

}
