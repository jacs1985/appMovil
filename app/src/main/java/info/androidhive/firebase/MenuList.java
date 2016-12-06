package info.androidhive.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import info.androidhive.firebase.adapters.MenusListAdapter;
import info.androidhive.firebase.interfaces.CostCalculation;
import info.androidhive.firebase.interfaces.OrderBy;
import info.androidhive.firebase.model.Combo;
import info.androidhive.firebase.model.Menu;

public class MenuList extends AppCompatActivity implements AdapterView.OnItemClickListener, OrderBy, CostCalculation, View.OnClickListener  {

    private Context context;
    private ListView menusList;
    private ArrayList<Combo> combos = new ArrayList<>();
    private Menu menu;
    private ImageView comboImagePreview;
    private TextView comboListTitle;
    private Button testButton;
    private Button testButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle i = getIntent().getExtras();
        menu = (Menu) i.getSerializable("obj");
        combos = menu.getCombos();

        setContentView(R.layout.menu_list);
        comboListTitle = (TextView)findViewById(R.id.combo_list_title);
        comboListTitle.setText(comboListTitle.getText().toString()+" "+menu.getName());

        testButton = (Button)findViewById(R.id.test_button);
        testButton.setOnClickListener(this);

        testButton = (Button)findViewById(R.id.test_button2);
        testButton.setOnClickListener(this);

        menusList = (ListView)findViewById(R.id.menus_list);
        menusList.setAdapter(new MenusListAdapter(getBaseContext(),combos));
        menusList.setOnItemClickListener(this);

        comboImagePreview = (ImageView)findViewById(R.id.combo_image_preview);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        comboImagePreview.setImageResource(combos.get(position).getImageId());
    }

    @Override
    public void orderByCost() {
        Collections.sort(combos, new IntegerComparator());
        ((BaseAdapter) menusList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void customOrder() {
        Collections.sort(combos, new IdComparator());
        ((BaseAdapter) menusList.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public int calculateCost() {
        int total = 0;
        for (int i =0; i<combos.size(); i++){
            total = combos.get(i).calculateCost() + total;
        }
        return total;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_button:
                orderByCost();
                break;

            case R.id.test_button2:
                customOrder();
                break;

        }
    }

    public class IntegerComparator implements Comparator<Combo> {
        public int compare(Combo c1, Combo c2) {
            return ((Integer)c1.calculateCost()).compareTo(c2.calculateCost());
        }
    }

    public class IdComparator implements Comparator<Combo> {
        public int compare(Combo c1, Combo c2) {
            return ((Integer)c1.getComboId()).compareTo(c2.getComboId());
        }
    }

}
