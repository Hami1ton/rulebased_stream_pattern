package org.example.rulebased.streaming.flinkdroolspattern;

import java.util.Date;
import org.kie.api.definition.type.Duration;
import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;

@Role(Role.Type.EVENT)
@Timestamp("date")
@Duration( "date" )
@Expires( "5s" )
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
