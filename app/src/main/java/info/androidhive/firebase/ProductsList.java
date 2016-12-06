package info.androidhive.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import info.androidhive.firebase.adapters.FoodListAdapter;
import info.androidhive.firebase.interfaces.OrderBy;
import info.androidhive.firebase.R;
import info.androidhive.firebase.model.Food;


public class ProductsList extends AppCompatActivity implements AdapterView.OnItemClickListener, OrderBy, View.OnClickListener {

    private ListView menusList;
    private ArrayList<Food> foods = new ArrayList<>();
    private Button orderByAlf;
    private Button orderByCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            Bundle bundleObject = getIntent().getExtras();
            foods = (ArrayList<Food>) bundleObject.getSerializable("products");

            setContentView(R.layout.products_list);

            orderByAlf = (Button)findViewById(R.id.order_by_alf);
            orderByAlf.setOnClickListener(this);

            orderByCost = (Button)findViewById(R.id.order_by_cost);
            orderByCost.setOnClickListener(this);

            menusList = (ListView)findViewById(R.id.menus_list);
            menusList.setAdapter(new FoodListAdapter(getBaseContext(),foods));
            menusList.setOnItemClickListener(this);
    }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.order_by_cost:
                    orderByCost();
                    break;

                case R.id.order_by_alf:
                    customOrder();
                    break;

            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void orderByCost() {
            Collections.sort(foods, new IntegerComparator());
            ((BaseAdapter) menusList.getAdapter()).notifyDataSetChanged();
        }

        @Override
        public void customOrder() {
            Collections.sort(foods, new NameComparator());
            ((BaseAdapter) menusList.getAdapter()).notifyDataSetChanged();
        }

        public class IntegerComparator implements Comparator<Food> {
            public int compare(Food c1, Food c2) {
                return ((Integer)c1.getCost()).compareTo(c2.getCost());
            }
        }

        public class NameComparator implements Comparator<Food> {
            public int compare(Food c1, Food c2) {
                return c1.getName().compareToIgnoreCase(c2.getName());
            }
        }


    }
