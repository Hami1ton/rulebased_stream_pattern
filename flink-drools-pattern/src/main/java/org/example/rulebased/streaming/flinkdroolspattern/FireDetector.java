package org.example.rulebased.streaming.flinkdroolspattern;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;

public class FireDetector extends KeyedProcessFunction<Integer, Tuple2<Integer, Integer>, FireAlarm> {

    @Override
    public void processElement(
            Tuple2<Integer, Integer> sensorData,
            Context context,
            Collector<FireAlarm> collector) throws Exception {

        // init rule unit
        FireDetectRuleUnit drinkRuleUnit = new FireDetectRuleUnit();
        RuleUnitInstance<FireDetectRuleUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(drinkRuleUnit);
        drinkRuleUnit.getSensorDatas().add(new SensorData(3, DateUtils.parseDate("2024-01-04 23:20:04.000", "yyyy-MM-dd HH:mm:ss.SSS"), 1100));
        // drinkRuleUnit.getSensorDatas().add(sensorData);


        // execute rule 
        instance.fire();

        if(drinkRuleUnit.getAlarmList().size() > 0) {
            collector.collect(drinkRuleUnit.getAlarmList().get(0));
        }

        instance.close();
    }
    
}
