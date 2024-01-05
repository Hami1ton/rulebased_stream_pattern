package org.example.rulebased.streaming.flinkdroolspattern;

import java.util.ArrayList;
import java.util.List;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.RuleUnitData;


public class FireDetectRuleUnit implements RuleUnitData {

    private DataStore<SensorData> sensorDatas;

    private List<FireAlarm> alarmList = new ArrayList<>();

    public FireDetectRuleUnit() {
        this(DataSource.createStore());
    }

    public FireDetectRuleUnit(DataStore<SensorData> sensorDatas) {
        this.sensorDatas = sensorDatas;
    }

    public void setSensorDatas(DataStore<SensorData> sensorDatas) {
        this.sensorDatas = sensorDatas;
    }

    public DataStore<SensorData> getSensorDatas() {
        return sensorDatas;
    }

    public List<FireAlarm> getAlarmList() {
        return alarmList;
    }   
}