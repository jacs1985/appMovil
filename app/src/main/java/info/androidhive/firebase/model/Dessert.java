package info.androidhive.firebase.model;

import java.io.Serializable;

public class Dessert extends Food implements Serializable {

    private String arrivalDate;
    private int duration;

    public Dessert(String name, int cost, int weight, String type, String arrivalDate, int duration) {
        super(name, cost, weight, type);
        this.arrivalDate = arrivalDate;
        this.duration = duration;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public int getDuration() {
        return duration;
    }

}
