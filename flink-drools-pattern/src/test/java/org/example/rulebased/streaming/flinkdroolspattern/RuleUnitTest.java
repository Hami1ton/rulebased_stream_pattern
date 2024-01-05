package org.example.rulebased.streaming.flinkdroolspattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
import org.drools.ruleunits.api.RuleUnitInstance;
import org.drools.ruleunits.api.RuleUnitProvider;
import org.junit.jupiter.api.Test;

public class RuleUnitTest {
    
    @Test
    public void test_火災判定ルール() throws ParseException {
        FireDetectRuleUnit drinkRuleUnit = new FireDetectRuleUnit();
        RuleUnitInstance<FireDetectRuleUnit> instance = RuleUnitProvider.get().createRuleUnitInstance(drinkRuleUnit);

        var sensorData = new SensorData(1, DateUtils.parseDate("2024-01-04 23:20:02.000", "yyyy-MM-dd HH:mm:ss.SSS"), 1300);
        drinkRuleUnit.getSensorDatas().add(sensorData);

        // execute rule 
        instance.fire();

        // assert
        var alarm = drinkRuleUnit.getAlarmList().get(0);
        assertEquals(1300, alarm.getTemperature());

        instance.close();
    }
}
