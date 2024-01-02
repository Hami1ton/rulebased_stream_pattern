package org.example.rulebased.streaming.study.flink.fireDetected;

import java.util.Date;


public class Alarm {
    
    public Date alarmDate;

    public int temperature;

    public Alarm() {}

    public Alarm(Date alarmDate, int temperature) {
        this.alarmDate = alarmDate;
        this.temperature = temperature;
    }

}
