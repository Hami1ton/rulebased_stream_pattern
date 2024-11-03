package org.example.rulebased.streaming.flinkdroolsdynamic;

import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.FireAlarm;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.RuleString;
import org.example.rulebased.streaming.flinkdroolsdynamic.util.RuleDataSplitter;
import org.example.rulebased.streaming.flinkdroolsdynamic.util.SensorDataSplitter;


public class FireDetectJob {

    public void run() throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // Define parallelism 2
        env.setParallelism(2);

        MapStateDescriptor<Void, RuleString> bcStateDescriptor = 
         new MapStateDescriptor<>("kieBaseDesc", Types.VOID, Types.POJO(RuleString.class));

        // Rule Stream
        BroadcastStream<RuleString> ruleStream = createRuleStream(env).broadcast(bcStateDescriptor);
        
        // FireAlarm Stream
        DataStream<FireAlarm> alarms = createAlarmStream(env, ruleStream);

        // Data Sinks
        alarms.print();

        env.execute();
    }

    private DataStream<RuleString> createRuleStream(StreamExecutionEnvironment env) {
        // Data Sources
        DataStream<String> ruleDatas = env.socketTextStream("localhost", 9998);

        var rules = ruleDatas
            .flatMap(new RuleDataSplitter())
            .name("rule-updater");
        return rules;
        
    }

    private DataStream<FireAlarm> createAlarmStream(StreamExecutionEnvironment env, BroadcastStream<RuleString> ruleStream) {
        // Data Sources
        DataStream<String> sensorDatas = env.socketTextStream("localhost", 9999);

        // DataStream Transformations(Operators)
        var alarms = sensorDatas
            .flatMap(new SensorDataSplitter())
            .keyBy(value -> value.id)
            .connect(ruleStream)
            .process(new FireDetector())
            .name("fire-detector");

        return alarms;

    }

}
