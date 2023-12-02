package org.example.rulebased.streaming.flinkdroolspattern;

import java.util.ArrayList;
import java.util.List;

import org.drools.ruleunits.api.DataSource;
import org.drools.ruleunits.api.DataStore;
import org.drools.ruleunits.api.RuleUnitData;

public class DrinkRuleUnit implements RuleUnitData {
    
    private DataStore<Person> persons;

    private List<Drink> drinkList = new ArrayList<>();

    public DrinkRuleUnit() {
        this(DataSource.createStore());
    }

    public DrinkRuleUnit(DataStore<Person> persons) {
        this.persons = persons;
    }

    public void setPersons(DataStore<Person> persons) {
        this.persons = persons;
    }

    public DataStore<Person> getPersons() {
        return persons;
    }

    public List<Drink> getDrinkList() {
        return drinkList;
    }
   
}
