package info.androidhive.firebase;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Calendar;
import info.androidhive.firebase.R;
import info.androidhive.firebase.model.Food;
import info.androidhive.firebase.strategys.EarlyMorningStrategy;
import info.androidhive.firebase.strategys.LunchStrategy;
import info.androidhive.firebase.strategys.MorningStrategy;

public class PizzaCustom extends Activity implements View.OnClickListener  {

    private Button meat;
    private Button chicken;
    private Button tomato;
    private Button choricillo;
    private Button olive;
    private Button mushroom;
    private Button costButton;
    private TextView pizzaDetails;
    private TextView totalCostText;

    private EarlyMorningStrategy earlyMorning = new EarlyMorningStrategy();
    private LunchStrategy lunch = new LunchStrategy();
    private MorningStrategy morning = new MorningStrategy();

    private ArrayList<Food> ingredients = new ArrayList<>();
    private ArrayList<Food> productsList = new ArrayList<>();

    private Food pizza = new Food("Pizza", 2000, "pizza");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pizza_custom);

        Bundle bundleObject = getIntent().getExtras();
        productsList = (ArrayList<Food>) bundleObject.getSerializable("products");

        meat = (Button)findViewById(R.id.meat_button);
        meat.setOnClickListener(this);

        chicken = (Button)findViewById(R.id.chicken_button);
        chicken.setOnClickListener(this);

        tomato = (Button)findViewById(R.id.tomato_button);
        tomato.setOnClickListener(this);

        mushroom = (Button)findViewById(R.id.mushroom_button);
        mushroom.setOnClickListener(this);

        olive = (Button)findViewById(R.id.olive_button);
        olive.setOnClickListener(this);

        choricillo = (Button)findViewById(R.id.choricillo_button);
        choricillo.setOnClickListener(this);

        costButton = (Button)findViewById(R.id.cost_button);
        costButton.setOnClickListener(this);

        pizzaDetails = (TextView)findViewById(R.id.pizza_details);

        totalCostText = (TextView)findViewById(R.id.total_cost);

        for (int i =0; i<productsList.size(); i++) {
            if (productsList.get(i).getName().equalsIgnoreCase("Queso")
                    || productsList.get(i).getName().equalsIgnoreCase("Jamón")) {
                ingredients.add(productsList.get(i));
            }
        }

    }

    private void addToDetails(String newIngredient){
        if(pizzaDetails.getText().toString().contains(newIngredient)){
            String stringToReplace = ", "+newIngredient;
            pizzaDetails.setText(pizzaDetails.getText().toString().replace(stringToReplace,""));
            for (int i =0; i<ingredients.size(); i++){
                if(ingredients.get(i).getName().equalsIgnoreCase(newIngredient)){
                    ingredients.remove(i);
                }
            }
        }else{
            pizzaDetails.setText(pizzaDetails.getText().toString()+", "+newIngredient);
            for (int i =0; i<productsList.size(); i++){
                if(productsList.get(i).getName().equalsIgnoreCase(newIngredient)){
                    ingredients.add(productsList.get(i));
                }
            }
        }
    }

    private void calculateTotal(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        if(hour>=20 ||  hour<=9){
            totalCostText.setText("$"+earlyMorning.totalCost(ingredients)+pizza.getCost());
        }else if(hour>11 && hour<16){
            totalCostText.setText("$"+lunch.totalCost(ingredients)+pizza.getCost());
        }else{
            totalCostText.setText("$"+morning.totalCost(ingredients)+pizza.getCost());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.meat_button:
                addToDetails("Lomo");
                break;

            case R.id.mushroom_button:
                addToDetails("Champiñones");
                break;

            case R.id.chicken_button:
                addToDetails("Pollo");
                break;

            case R.id.tomato_button:
                addToDetails("Tomate");
                break;

            case R.id.olive_button:
                addToDetails("Aceitunas");
                break;

            case R.id.choricillo_button:
                addToDetails("Choricillo");
                break;

            case R.id.cost_button:
                calculateTotal();
                break;

        }

    }
}
