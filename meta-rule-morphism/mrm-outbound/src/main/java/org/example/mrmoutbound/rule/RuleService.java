package org.example.mrmoutbound.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.example.mrmcore.model.MappingContext;
import org.example.mrmcore.model.MorphismInstruction;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class RuleService {
    private final KieContainer kieContainer;

    public RuleService() {
        // KieServices を通じてクラスパス上の DRL をロード
        KieServices ks = KieServices.Factory.get();
        this.kieContainer = ks.getKieClasspathContainer();
    }

    public List<MorphismInstruction> evaluate(String systemId, Set<String> fields) {
        List<MorphismInstruction> instructions = new ArrayList<>();
        KieSession kSession = kieContainer.newKieSession();

        try {
            // DRL 内の global java.util.List instructions; に紐付け
            kSession.setGlobal("instructions", instructions);

            // フィールドごとに MappingContext を作成し、ルールを評価
            for (String field : fields) {
                kSession.insert(new MappingContext(systemId, "Outbound", field));
            }

            kSession.fireAllRules();
        } finally {
            kSession.dispose();
        }

        return instructions;
    }
}