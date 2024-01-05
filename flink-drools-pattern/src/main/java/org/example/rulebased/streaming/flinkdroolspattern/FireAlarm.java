package org.example.rulebased.streaming.flinkdroolspattern;

import java.util.Date;
import org.kie.api.definition.type.Duration;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;


@Role(Role.Type.EVENT)
@Timestamp("alarmDate")
@Duration( "alarmDate" )
@Expires( "5s" )
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
