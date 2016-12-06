package info.androidhive.firebase.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import info.androidhive.firebase.dao.DataAccessObject;
import info.androidhive.firebase.R;
import info.androidhive.firebase.model.Employee;

public class EmployeesListAdapter extends ArrayAdapter<Employee> implements ListAdapter {

    private Context context;
    private ArrayList<Employee> employees;

    public EmployeesListAdapter(Context context, ArrayList<Employee> employeesArray){
        super(context, 0, employeesArray);
        this.context = context;
        this.employees = employeesArray;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = LayoutInflater.from(context).inflate(R.layout.row_employee, parent, false);

        Employee employee = getItem(position);

        TextView name = (TextView) row.findViewById(R.id.employee_name);
        TextView function = (TextView) row.findViewById(R.id.employee_function);
        TextView presence = (TextView) row.findViewById(R.id.employee_presence);

        name.setText(employee.getName());
        function.setText(employee.getFunction());
        try {
            presence.setText(DataAccessObject.getDao(context).getPresenceById(employee.getId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return row;
    }
}
