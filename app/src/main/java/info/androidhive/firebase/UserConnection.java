package info.androidhive.firebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import info.androidhive.firebase.R;
import info.androidhive.firebase.state.FailedConnectionState;
import info.androidhive.firebase.state.SuccessConnectionState;

public class UserConnection extends AppCompatActivity {

    private boolean connectionResult = false;
    private LinearLayout connectionInfoContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection_state);

        connectionInfoContainer = (LinearLayout)findViewById(R.id.connection_info_container);

        SuccessConnectionState successConnectionState = new SuccessConnectionState(getApplicationContext());
        FailedConnectionState failedConnectionState = new FailedConnectionState(getApplicationContext());

        try{
            connectionResult = getIntent().getExtras().getBoolean("connectionBoolean");
        }catch (NullPointerException nPE){
            nPE.getMessage();
        }

        if(connectionResult){
            connectionInfoContainer.addView(successConnectionState.getView());
        }else {
            connectionInfoContainer.addView(failedConnectionState.getView());
        }
    }

}
