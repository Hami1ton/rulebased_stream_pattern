package org.example.mrminbound.rule;

import org.example.mrmcore.model.MappingContext;
import org.example.mrmcore.model.MorphismInstruction;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RuleService {

    private final KieContainer kieContainer;

    public RuleService() {
        KieServices ks = KieServices.Factory.get();
        this.kieContainer = ks.getKieClasspathContainer();
    }

    /**
     * システムIDと入力項目のリストを元に、マッピング指示書を生成する
     */
    public List<MorphismInstruction> evaluate(String systemId, Collection<String> sourceFields) {
        KieSession kSession = kieContainer.newKieSession();
        List<MorphismInstruction> instructions = new ArrayList<>();

        try {
            // DRL側の global java.util.List instructions に紐付け
            kSession.setGlobal("instructions", instructions);

            // 各項目を Fact (MappingContext) として投入
            for (String field : sourceFields) {
                kSession.insert(new MappingContext(systemId, "INBOUND", field));
            }

            kSession.fireAllRules();
            return instructions;

        } finally {
            kSession.dispose(); // セッションの確実な破棄
        }
    }
}