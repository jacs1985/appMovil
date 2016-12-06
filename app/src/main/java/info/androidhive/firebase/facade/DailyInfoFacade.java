package info.androidhive.firebase.facade;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;
import info.androidhive.firebase.R;
import info.androidhive.firebase.model.Combo;
import info.androidhive.firebase.model.Menu;

public class DailyInfoFacade extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Combo> combosList = new ArrayList<>();
    private DailyGains internalDailyGains;
    private DailyStatistics internalDailyStatistics;
    private Button totalSellsButton;
    private Button sellStatisticsButton;
    private TextView sellInfo;
    private TextView sellStatistics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facade);

        totalSellsButton = (Button)findViewById(R.id.total_sells_button);
        totalSellsButton.setOnClickListener(this);

        sellStatisticsButton = (Button)findViewById(R.id.sell_statistics_button);
        sellStatisticsButton.setOnClickListener(this);

        sellInfo = (TextView)findViewById(R.id.sell_info);
        sellStatistics = (TextView)findViewById(R.id.sell_statistics);

        Bundle i = getIntent().getExtras();
        Menu menu = (Menu) i.getSerializable("obj");
        combosList = menu.getCombos();

        internalDailyGains = new DailyGains(combosList);
        internalDailyStatistics = new DailyStatistics(combosList);
    }

    private int getGains(){
        return internalDailyGains.getTotalGains();
    }

    private String getStatistics(){
        return internalDailyStatistics.geyEarlyMorningPercentages()+internalDailyStatistics.geyMorningPercentages()
                +internalDailyStatistics.geyLunchPercentages();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.total_sells_button:
                sellInfo.setText("Ganancias diarias: $"+getGains());
                break;

            case R.id.sell_statistics_button:
                sellStatistics.setText("Estadísticas de las ventas: \n"+getStatistics());
        }
    }
}
