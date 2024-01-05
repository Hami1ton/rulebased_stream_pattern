package org.example.rulebased.streaming.flinkdroolspattern;

import java.time.Instant;
import java.util.Date;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class FireDetector extends KeyedProcessFunction<Integer, Tuple2<Integer, Integer>, FireAlarm> {

    @Override
    public void processElement(
            Tuple2<Integer, Integer> sensorData,
            Context context,
            Collector<FireAlarm> collector) throws Exception {

        // execute rule
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase("fireDetect");
        KieSession session = kieBase.newKieSession();
        session.insert(new SensorData(sensorData.f0, Date.from(Instant.now()), sensorData.f1));
        session.fireAllRules();

        // query result
        FireAlarm fireAlarm = null;
        var queryResult = session.getQueryResults("FindAlarm");
        if (queryResult.size() > 0) {
            fireAlarm = (FireAlarm) queryResult.toList().get(0).get("$f");
            // System.out.println(fireAlarm);
            collector.collect(fireAlarm);
        }
    }
    
}
