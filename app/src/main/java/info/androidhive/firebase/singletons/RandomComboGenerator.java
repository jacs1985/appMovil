package info.androidhive.firebase.singletons;

import java.util.ArrayList;
import java.util.Random;

import info.androidhive.firebase.R;
import info.androidhive.firebase.model.Combo;
import info.androidhive.firebase.model.Dessert;
import info.androidhive.firebase.model.Drink;
import info.androidhive.firebase.model.Food;
import info.androidhive.firebase.model.Meat;
import info.androidhive.firebase.model.Bread;
import info.androidhive.firebase.model.Menu;
import info.androidhive.firebase.model.SuperCombo;
import info.androidhive.firebase.model.Vegetal;

public class RandomComboGenerator {

    private static RandomComboGenerator randomCombo = null;
    private static ArrayList<Food> comboComponents = new ArrayList<>();
    private static ArrayList<Integer> exclude = new ArrayList<>();
    private static int combosCounter = 1;

    public static RandomComboGenerator getInstance(){
        if(randomCombo==null){
            randomCombo = new RandomComboGenerator();
        }
        return randomCombo;
    }

    public void fillMenu(ArrayList<Vegetal> vegetals, ArrayList<Meat> meats, ArrayList<Bread> breads,
                         ArrayList<Drink> drinks, ArrayList<Dessert> desserts, Menu menu){
        for (int i=0; i<3; i++){
            menu.getCombos().add(generateCombo(vegetals, meats, breads));
        }
    }


    public static Combo generateCombo(ArrayList<Vegetal> vegetals, ArrayList<Meat> meats, ArrayList<Bread> breads){
        comboComponents.clear();
        exclude.clear();

        Vegetal [] comboVegetals = new Vegetal[2];
        comboVegetals[0] = vegetals.get(generateRandomInteger(vegetals.size()-1,1));
        comboVegetals[1] = vegetals.get(generateRandomInteger(vegetals.size()-1,1));


        Combo combo = new Combo(combosCounter, comboVegetals,
                                meats.get(generateRandomInteger(meats.size()-1,0)),
                                breads.get(generateRandomInteger(breads.size()-1,0))
                                );

        combosCounter++;

        Random random = new Random();
        if(random.nextInt()%2==0){
            combo.setImageId(R.drawable.cajita_feliz);
        }else if(random.nextInt()%3==0){
            combo.setImageId(R.drawable.cafe_combo);
        }else {
            combo.setImageId(R.drawable.burger_king_combo);
        }

        return combo;
    }

    public static SuperCombo generateSuperCombo(ArrayList<Vegetal> vegetals, ArrayList<Meat> meats,
                                            ArrayList<Bread> breads, ArrayList<Drink> drinks,
                                            ArrayList<Dessert> desserts){
        comboComponents.clear();
        exclude.clear();

        Vegetal [] comboVegetals = new Vegetal[2];

        comboVegetals[0] = vegetals.get(generateRandomInteger(vegetals.size()-1,1));
        comboVegetals[1] = vegetals.get(generateRandomInteger(vegetals.size()-1,1));

        SuperCombo superCombo = new SuperCombo(combosCounter, comboVegetals,
                                               meats.get(generateRandomInteger(meats.size()-1,0)),
                                               breads.get(generateRandomInteger(breads.size()-1,0)),
                                               desserts.get(generateRandomInteger(desserts.size()-1,0)),
                                               drinks.get(generateRandomInteger(drinks.size()-1,0)));

        combosCounter++;

        return superCombo;
    }

    private static int generateRandomInteger(int end, int mode){
        Random rand = new Random();
        int range = end - 0;
        int random = rand.nextInt(range);
        if(mode==1){
            if(exclude.size()>0){
                while(exclude.contains(random)) {
                        random = rand.nextInt(range) + 1;
                }
            }
            exclude.add(random);
        }
        return random;
    }

}
