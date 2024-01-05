package org.example.rulebased.streaming.flinkdroolspattern;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;


public class FireDetectJob {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // source
        DataStream<Tuple2<Integer, Integer>> dataStream = env
                .socketTextStream("localhost", 9999)
                .flatMap(new Splitter());

        // operatore
        DataStream<FireAlarm> alarms = dataStream
            .keyBy(value -> value.f0)
            .process(new FireDetector())
            .name("fire-detector");

        // sink
        alarms.print();

        env.execute();
    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<Integer, Integer>> {
        @Override
        public void flatMap(String sentence, Collector<Tuple2<Integer, Integer>> out) throws Exception {
            String[] words = sentence.split(",");
            out.collect(new Tuple2<Integer, Integer>(Integer.parseInt(words[0]), Integer.parseInt(words[1])));
        }
    }
}
