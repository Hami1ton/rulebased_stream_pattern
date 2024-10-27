package org.example.rulebased.streaming.flinkdroolsdynamic;

import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.util.Collector;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.FireAlarm;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.SensorData;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.apache.flink.configuration.Configuration;


public class FireDetector extends KeyedProcessFunction<Integer, SensorData, FireAlarm> {

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
            SensorData sensorData,
            Context context,
            Collector<FireAlarm> collector) throws Exception {

        // execute rule
        session.insert(sensorData);
        session.fireAllRules();

        // query result
        var queryResult = session.getQueryResults("FindAlarm", sensorData.id);
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
