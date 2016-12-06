package info.androidhive.firebase.model;


import java.io.Serializable;

public class Vegetal extends Food implements Serializable{

    private String season;

    public Vegetal(String name, int cost, int weight, String type, String season){
        super(name, cost, weight, type);
        this.season = season;
    }
    public String getSeason(){
        return season;
    }

}
