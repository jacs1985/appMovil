package info.androidhive.firebase.interfaces;

import java.util.ArrayList;
import info.androidhive.firebase.model.Food;

public interface TotalCostStrategy {
    public int totalCost(ArrayList<Food> ingredients);
}
