package info.androidhive.firebase.model;

import java.io.Serializable;

public class Meat extends Food implements Serializable {

    private String origin;
    private double fatIndex;

    public Meat(String name, int cost, int weight, String type, String origin, double fatIndex){
        super(name, cost, weight, type);
        this.origin = origin;
        this.fatIndex = fatIndex;
    }

    public String getOrigin() {
        return origin;
    }

    public double getfatIndex() {
        return fatIndex;
    }



}
