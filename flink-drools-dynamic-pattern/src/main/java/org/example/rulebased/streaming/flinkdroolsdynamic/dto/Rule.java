package org.example.rulebased.streaming.flinkdroolsdynamic.dto;


public class Rule {

    private String ruleStr;

    public Rule(int temperature) {
        this.ruleStr = getRuleStr(temperature);
    }

    private String getRuleStr(int temperature) {
        String rule = "" +
                   "package org.example.rulebased.streaming.flinkdroolsdynamic; \n" +
                   "rule \"fire detect\" \n" +
                   "    when \n" +
                   "        $s: SensorData(temperature > " + temperature + " ) \n" +
                   "    then \n" +
                   "        FireAlarm alarm = new FireAlarm($s.getId(), $s.getDate(), $s.getTemperature()); \n" +
                   "        insert(alarm); \n" +
                   "end \n";
        return rule;
    }

    public String toString() {
        return this.ruleStr;
    }
}
