package org.example.rulebased.streaming.flinkdroolsdynamic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import org.example.rulebased.streaming.flinkdroolsdynamic.dto.FireAlarm;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.RuleString;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.SensorData;
import org.junit.jupiter.api.Test;


public class RuleTest {

    @Test
    public void test_火災判定ルール() throws ParseException {
        // set up rule
        RuleString ruleString = new RuleString(20);
        var kieBase = KieBaseCreater.createFromRuleString(ruleString);
        var session = kieBase.newKieSession();

        // insert data
        var sensorData = new SensorData(1, Date.from(Instant.now()), 90);
        session.insert(sensorData);
        int count = session.fireAllRules();
        System.out.println(count);

        // query result
        FireAlarm fireAlarm = null;
        var queryResult = session.getQueryResults("FindAlarm", sensorData.getId());
        if (queryResult.size() == 1) {
            fireAlarm = (FireAlarm) queryResult.toList().get(0).get("$f");
            System.out.println(fireAlarm);
        }
        session.dispose();
        assertEquals(1, fireAlarm.getId());
        assertEquals(90, fireAlarm.getTemperature());
    }
}
