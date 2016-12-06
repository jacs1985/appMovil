package info.androidhive.firebase.state;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import info.androidhive.firebase.R;

public class FailedConnectionState implements ConnectionStateInterface, View.OnClickListener {

    private Context context;
    private Button tryAgainButton;

    public FailedConnectionState(Context context){
        this.context = context;
    }

    @Override
    public View getView() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.user_connection_error, null);
        tryAgainButton = (Button)view.findViewById(R.id.try_again_button);
        tryAgainButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.e("Lorem","Ipsum");
    }
}
