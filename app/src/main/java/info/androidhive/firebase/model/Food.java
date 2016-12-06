package info.androidhive.firebase.model;

import java.io.Serializable;

public class Food implements Serializable {

    private String name;
    private int cost;
    private int weight;
    private String type;

    public Food(String name, int cost, String type){
        this.name = name;
        this.cost = cost;
        this.type = type;
    }

    public Food(String name, int cost, int weight, String type) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getWeight() {
        return weight;
    }

    public String getType() {
        return type;
    }

}
