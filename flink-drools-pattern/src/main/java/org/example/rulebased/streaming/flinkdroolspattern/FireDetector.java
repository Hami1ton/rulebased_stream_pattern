package org.example.rulebased.streaming.flinkdroolspattern;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;

public class FireDetector extends KeyedProcessFunction<Integer, SensorData, FireAlarm> {

    @Override
    public void processElement(
            SensorData sensorData,
            Context context,
            Collector<FireAlarm> collector) throws Exception {

        // init rule unit
        FireDetectRuleUnit drinkRuleUnit = new FireDetectRuleUnit();
        RuleUnitInstance<FireDetectRuleUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(drinkRuleUnit);
        drinkRuleUnit.getSensorDatas().add(sensorData);

        // execute rule 
        instance.fire();

        if(drinkRuleUnit.getAlarmList().size() > 0) {
            collector.collect(drinkRuleUnit.getAlarmList().get(0));
        }

        instance.close();
    }
    
}
