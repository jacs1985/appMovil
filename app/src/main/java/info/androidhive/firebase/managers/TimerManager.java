package info.androidhive.firebase.managers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import java.util.Calendar;

public class TimerManager extends BroadcastReceiver {

    private static TimerManager timerManager;
    private AlarmManager alarm;
    private PendingIntent pendingIntent;
    private Calendar calendar = Calendar.getInstance();
    private Boolean dayState;

    public static TimerManager getInstance(){
        if(timerManager==null){
            timerManager = new TimerManager();
        }
        return timerManager;
    }

    public void launchClock(Context context, String packageName, int seconds){
        Intent intent = new Intent(context, TimerManager.class);
        intent.setAction(packageName);
        pendingIntent = PendingIntent.getBroadcast(context,0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pendingIntent);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000*seconds, pendingIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int a = calendar.get(Calendar.AM_PM);
        String amPmString = "";
        if(a == Calendar.AM){
            amPmString = "AM";
        } else {
            amPmString = "PM";
        }

        if(calendar.get(Calendar.HOUR) == 8 && amPmString.equals("PM")){
            dayState = true;
        }else{
            dayState = false;
        }
        Log.e("time",""+Calendar.getInstance().get(Calendar.HOUR)+":"+Calendar.getInstance().get(Calendar.MINUTE)+amPmString);

        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 10);
    }

    public boolean isNight(){
        return dayState;
    }

    public void stopClock(){
        alarm.cancel(pendingIntent);
    }

}
