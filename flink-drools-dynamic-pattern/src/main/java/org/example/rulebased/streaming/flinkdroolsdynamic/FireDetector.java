package org.example.rulebased.streaming.flinkdroolsdynamic;

import org.apache.flink.api.common.state.BroadcastState;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.co.KeyedBroadcastProcessFunction;
import org.apache.flink.util.Collector;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.FireAlarm;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.RuleString;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.SensorData;
import org.kie.api.KieBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FireDetector extends KeyedBroadcastProcessFunction<Long, SensorData, RuleString, FireAlarm> {

    // KieBase Descriptor
    MapStateDescriptor<Void, KieBase> kieBaseDesc;

    Logger logger = LoggerFactory.getLogger(FireDetector.class);

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        // init Descriptor
        this.kieBaseDesc = new MapStateDescriptor<>("kieBaseDesc", Types.VOID, Types.GENERIC(KieBase.class));
    }

    @Override
    public void processElement(
            SensorData sensorData,
            ReadOnlyContext ctx,
            Collector<FireAlarm> collector) throws Exception {
        // get kiebase from broadcast state
        KieBase kieBase = ctx.getBroadcastState(this.kieBaseDesc).get(null);
        if (kieBase == null) {
            logger.info("KieBase do not exist.");
            logger.info("Stopped evaluating sensor data.");
            return;
        }

        // execute rule
        var session = kieBase.newKieSession();
        session.insert(sensorData);
        session.fireAllRules();
        logger.info("Rule was fired.");

        var queryResult = session.getQueryResults("FindAlarm", sensorData.id);
        if (queryResult.size() == 1) {
            logger.info("FireAlarm was created.");
            logger.info("sensor data:" + sensorData.toString());
            FireAlarm fireAlarm = (FireAlarm) queryResult.toList().get(0).get("$f");
            collector.collect(fireAlarm);
        }
        session.dispose();

        logger.info("Query was executed.");
    }

    @Override
    public void processBroadcastElement(RuleString ruleString, Context ctx, Collector<FireAlarm> collector) throws Exception {

        BroadcastState<Void, KieBase> state = ctx.getBroadcastState(this.kieBaseDesc);
        state.put(null, KieBaseCreater.createFromRuleString(ruleString));
    }
    
}
