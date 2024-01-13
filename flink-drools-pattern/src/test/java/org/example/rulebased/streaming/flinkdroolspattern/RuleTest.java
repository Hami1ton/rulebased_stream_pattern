package org.example.rulebased.streaming.flinkdroolspattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;


public class RuleTest {

    @Test
    public void test_火災判定ルール() throws ParseException {
        KieServices kieServices = KieServices.Factory.get();

        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase("fireDetect");
        KieSession session = kieBase.newKieSession();

        // execute rule
        var sensorData = new SensorData(1, Date.from(Instant.now()), 90);
        session.insert(sensorData);
        session.fireAllRules();

        // query result
        FireAlarm fireAlarm = null;
        var queryResult = session.getQueryResults("FindAlarm", sensorData.getId());
        if (queryResult.size() == 1) {
            fireAlarm = (FireAlarm) queryResult.toList().get(0).get("$f");
            System.out.println(fireAlarm);
        }
        assertEquals(1, fireAlarm.getId());
        assertEquals(90, fireAlarm.getTemperature());
    }

}
