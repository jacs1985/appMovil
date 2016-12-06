package info.androidhive.firebase.model;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;

public class Menu implements Serializable{

    private int id;
    private String name;
    private String description;
    private ArrayList<Combo> combos = new ArrayList<>();
    private ArrayList<SuperCombo> superCombos = new ArrayList<>();
    private Time startTime;
    private Time endTime;

    public Menu(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public ArrayList<Combo> getCombos() {
        return combos;
    }

    public ArrayList<SuperCombo> getSuperCombos() {
        return superCombos;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


}
