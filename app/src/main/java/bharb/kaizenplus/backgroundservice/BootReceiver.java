package bharb.kaizenplus.backgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class BootReceiver extends BroadcastReceiver {

    WakeUpReceiver alarm = new WakeUpReceiver();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            WakeUpReceiver.setAlarm(context, WakeUpReceiver.WAKE_TYPE_UPLOAD, Calendar.getInstance());
        }
    }
}
