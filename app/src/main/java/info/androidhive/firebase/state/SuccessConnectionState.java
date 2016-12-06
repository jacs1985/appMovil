package info.androidhive.firebase.state;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import info.androidhive.firebase.R;

public class SuccessConnectionState implements ConnectionStateInterface {

    private Context context;

    public SuccessConnectionState(Context context){
        this.context = context;
    }

    @Override
    public View getView() {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.user_connection_success, null);
        TextView name = (TextView) view.findViewById(R.id.name);
        ImageView profileImage = (ImageView) view.findViewById(R.id.profile_image);
        //Carga del historial de la persona...
        return view;
    }
}
