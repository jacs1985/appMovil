package info.androidhive.firebase.facade;

import java.util.ArrayList;
import info.androidhive.firebase.model.Combo;

public class DailyGains {

    private ArrayList<Combo> combosList;

    public DailyGains(ArrayList<Combo> combos){
        this.combosList = combos;
    }

    public int getTotalGains(){
        int total = 0;
        for (int i =0; i<combosList.size(); i++ ){
            total = total + combosList.get(i).calculateCost();
        }
        return total;
    }

    public String getDailySellsCounter(){
        return "Hoy se realizaron "+combosList.size()+" ventas";
    }

}
