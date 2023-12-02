package org.example.rulebased.streaming.flinkdroolspattern;


public class Drink {

    private String name;

    private int charge;

    public Drink(String name, int charge) {
        this.name = name;
        this.charge = charge;
    }

    public String getName() {
        return name;
    }

    public int getCharge(){
        return charge;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCharge(int charge){
        this.charge = charge;
    }

    @Override
    public String toString() {
        return "name: " + name + ", " + "charge: " + charge;
    }

}