package info.androidhive.firebase.facade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import info.androidhive.firebase.model.Combo;

public class DailyStatistics {
    private ArrayList<Combo> combosList;
    private int earlyMorningCounter = 0;
    private int morningCounter = 0;
    private int lunchCounter = 0;

    public DailyStatistics(ArrayList<Combo> combos){
        this.combosList = combos;
        setCombosDate();
    }

    private void setCombosDate(){
        for (int i=0; i<combosList.size(); i++){
            Calendar calendar = null;
            if(i<(combosList.size()/3)){
                calendar = new GregorianCalendar(2016,2,2,5,7);
                earlyMorningCounter++;
            }else if(i<(combosList.size()*2/3)){
                calendar = new GregorianCalendar(2016,2,2,11,7);
                morningCounter++;
            }else{
                calendar = new GregorianCalendar(2016,2,2,16,7);
                lunchCounter++;
            }
            combosList.get(i).setComboDate(calendar);
        }
    }

    public String geyEarlyMorningPercentages(){
        return "Hoy en la madrugada se registraron el "+getPercentage(earlyMorningCounter)+"% de las ventas.\n";
    }

    public String geyMorningPercentages(){
        return "Hoy en la mañana se registraron el "+getPercentage(morningCounter)+"% de las ventas.\n";
    }

    public String geyLunchPercentages(){
        return "Hoy en la tarde se registraron el "+getPercentage(lunchCounter)+"% de las ventas.";
    }

    private int getPercentage(int i){
        int percentage = 0;
        if(combosList.size()!=0){
            percentage = (i*100)/combosList.size();
        }
        return percentage;
    }


}
