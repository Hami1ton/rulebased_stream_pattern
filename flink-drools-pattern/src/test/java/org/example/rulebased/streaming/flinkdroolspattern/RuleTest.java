package org.example.rulebased.streaming.flinkdroolspattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;
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

        session.insert(new SensorData(1, DateUtils.parseDate("2024-01-04 23:20:02.000", "yyyy-MM-dd HH:mm:ss.SSS"), 1300));
        session.fireAllRules();

        // query result
        FireAlarm fireAlarm = null;
        var queryResult = session.getQueryResults("FindAlarm");
        if (queryResult.size() == 1) {
            fireAlarm = (FireAlarm) queryResult.toList().get(0).get("$f");
            System.out.println(fireAlarm);
        }
        assertEquals(1300, fireAlarm.getTemperature());
    }

}
