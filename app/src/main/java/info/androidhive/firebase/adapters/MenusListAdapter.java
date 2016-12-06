package info.androidhive.firebase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import info.androidhive.firebase.R;
import info.androidhive.firebase.model.Combo;

public class MenusListAdapter extends ArrayAdapter<Combo> implements ListAdapter {
    private Context context;
    private ArrayList<Combo> combos;

    public MenusListAdapter(Context context, ArrayList<Combo> combos) {
        super(context, 0, combos);
        this.context = context;
        this.combos = combos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_menus_list, parent, false);
        Combo combo = getItem(position);
        TextView name = (TextView) row.findViewById(R.id.combo_text);
        name.setText(combo.getComboComponents()+" = "+combo.calculateCost());
        return row;
    }

}
