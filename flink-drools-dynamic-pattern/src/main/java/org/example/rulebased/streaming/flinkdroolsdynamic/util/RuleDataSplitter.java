package org.example.rulebased.streaming.flinkdroolsdynamic.util;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.example.rulebased.streaming.flinkdroolsdynamic.dto.Rule;


public class RuleDataSplitter implements FlatMapFunction<String, Rule>{

    @Override
    public void flatMap(String sentence, Collector<Rule> out) throws Exception {
        String[] words = sentence.split(",");
        
        out.collect(new Rule(Integer.parseInt(words[1])));
    }
}
