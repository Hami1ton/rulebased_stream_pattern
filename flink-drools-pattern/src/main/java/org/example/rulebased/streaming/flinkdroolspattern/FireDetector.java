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
import org.apache.flink.configuration.Configuration;


public class FireDetector extends KeyedProcessFunction<Integer, Tuple2<Integer, Integer>, FireAlarm> {

    KieSession session;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        // rule resource
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kContainer = kieServices.getKieClasspathContainer();
        KieBase kieBase = kContainer.getKieBase("fireDetect");
        this.session = kieBase.newKieSession();
        System.out.println("Rule Init Completed!");
    }

    @Override
    public void processElement(
            Tuple2<Integer, Integer> sensorData,
            Context context,
            Collector<FireAlarm> collector) throws Exception {

        // execute rule
        session.insert(new SensorData(sensorData.f0, Date.from(Instant.now()), sensorData.f1));
        session.fireAllRules();

        // query result
        var queryResult = session.getQueryResults("FindAlarm", sensorData.f0);
        if (queryResult.size() == 1) {
            FireAlarm fireAlarm = (FireAlarm) queryResult.toList().get(0).get("$f");
            collector.collect(fireAlarm);
        }

        // debug sensor data
        var sensorDataQueryResult = session.getQueryResults("FindSensorData");
        if (sensorDataQueryResult.size() > 0) {
            var debugSensorDatas = sensorDataQueryResult.toList();
            System.out.println(debugSensorDatas);
        }
    }
    
}
