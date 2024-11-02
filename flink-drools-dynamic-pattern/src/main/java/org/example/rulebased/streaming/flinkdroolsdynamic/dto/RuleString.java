package org.example.rulebased.streaming.flinkdroolsdynamic.dto;


public class RuleString {

    private String value;

    public RuleString(int temperature) {
        this.value = createRuleStr(temperature);
    }

    private String createRuleStr(int temperature) {
        String rule = "" +
                   "package org.example.rulebased.streaming.flinkdroolsdynamic; \n" +
                   "import org.example.rulebased.streaming.flinkdroolsdynamic.dto.SensorData; \n" +
                   "import org.example.rulebased.streaming.flinkdroolsdynamic.dto.FireAlarm; \n" +
                   "rule \"fire detect\" \n" +
                   "    when \n" +
                   "        $s: SensorData(temperature > " + temperature + " ) \n" +
                   "    then \n" +
                   "        FireAlarm alarm = new FireAlarm($s.getId(), $s.getDate(), $s.getTemperature()); \n" +
                   "        insert(alarm); \n" +
                   "end \n" + 

                   "query FindAlarm(String sensorDataId) \n" + 
                   "    $f: FireAlarm(id == sensorDataId) \n" + 
                   "end \n";
        return rule;
    }

    public String getValue() {
        return this.value;
    }

    public String toString() {
        return this.value;
    }
}
