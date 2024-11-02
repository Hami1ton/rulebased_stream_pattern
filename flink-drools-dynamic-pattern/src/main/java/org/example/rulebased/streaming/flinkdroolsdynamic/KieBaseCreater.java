package org.example.rulebased.streaming.flinkdroolsdynamic;

import org.example.rulebased.streaming.flinkdroolsdynamic.dto.RuleString;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieRepository;
import org.kie.api.runtime.KieContainer;


public class KieBaseCreater {

    public static KieBase createFromRuleString(RuleString ruleString) {
        KieServices ks = KieServices.Factory.get();
        KieRepository kr = ks.getRepository();
        KieFileSystem kfs = ks.newKieFileSystem();

        kfs.write("src/main/resources/org/example/rulebased/streaming/flinkdroolsdynamic/FireDetectorRule.drl", ruleString.getValue());
        KieBuilder kb = ks.newKieBuilder(kfs);
        kb.buildAll();
        KieContainer kContainer = ks.newKieContainer(kr.getDefaultReleaseId());
        return kContainer.getKieBase();
    }

}
