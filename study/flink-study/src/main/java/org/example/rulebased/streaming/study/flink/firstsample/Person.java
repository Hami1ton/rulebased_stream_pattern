package org.example.rulebased.streaming.study.flink.firstsample;

public class Person {

    public String name;

    public int age;

    public String country;

    public Person() {}

    public Person(String name, int age, String country) {
        this.name = name;
        this.age = age;
        this.country = country;
    }

    public String toString() {
        return "Person(name=" + this.name + ",age" + this.age + ",country=" + this.country + ")";
    }
     
}
