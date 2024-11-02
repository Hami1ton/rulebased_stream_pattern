package org.example.rulebased.streaming.flinkdroolsdynamic.dto;


public class RuleString {

    private String value;

    public RuleString(int temperature) {
        this.value = createRuleStr(temperature);
    }

    private String createRuleStr(int temperature) {
        StringBuilder sb = new StringBuilder();
        sb.append("package org.example.rulebased.streaming.flinkdroolsdynamic; \n");
        sb.append("import org.example.rulebased.streaming.flinkdroolsdynamic.dto.SensorData; \n");
        sb.append("import org.example.rulebased.streaming.flinkdroolsdynamic.dto.FireAlarm; \n");
        sb.append("rule \"fire detect\" \n");
        sb.append("    when \n");
        sb.append("        $s: SensorData(temperature > " + temperature + " ) \n");
        sb.append("    then \n");
        sb.append("        FireAlarm alarm = new FireAlarm($s.getId(), $s.getDate(), $s.getTemperature()); \n");
        sb.append("        insert(alarm); \n");
        sb.append("end \n");

        sb.append("query FindAlarm(String sensorDataId) \n");
        sb.append("    $f: FireAlarm(id == sensorDataId) \n");
        sb.append("end \n");

        return sb.toString();
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }
}
