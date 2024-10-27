package org.example.rulebased.streaming.flinkdroolsdynamic.util;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.RuleString;


public class RuleDataSplitter implements FlatMapFunction<String, RuleString>{

    @Override
    public void flatMap(String sentence, Collector<RuleString> out) throws Exception {
        String[] words = sentence.split(",");
        
        out.collect(new RuleString(Integer.parseInt(words[1])));
    }
}
