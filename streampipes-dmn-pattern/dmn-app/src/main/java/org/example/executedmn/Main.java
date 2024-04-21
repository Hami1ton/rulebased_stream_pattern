package org.example.executedmn;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        // set up
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        DMNRuntime dmnRuntime = KieRuntimeFactory.of(kieContainer.getKieBase()).get(DMNRuntime.class);

        // namespace, modelNameを指定 (DMNファイルDocumentationから)
        String namespace = "https://kiegroup.org/dmn/_4735995C-BC84-43CC-83AE-63C1BB354B5C";
        String modelName = "Drink";
        DMNModel dmnModel = dmnRuntime.getModel(namespace, modelName);
        DMNContext dmnContext = dmnRuntime.newContext();

        // insert datas
        Map<String, Object> personMap = new HashMap<>();
        personMap.put("name", "太郎");
        personMap.put("age", 34);
        dmnContext.set("Person", personMap);

        // execute
        final DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);

        System.out.println(dmnResult.toString());
        
    }
}
