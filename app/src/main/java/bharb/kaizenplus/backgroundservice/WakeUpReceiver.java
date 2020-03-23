package bharb.kaizenplus.backgroundservice;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.legacy.content.WakefulBroadcastReceiver;

import java.util.Calendar;

import static android.content.Context.POWER_SERVICE;

public class WakeUpReceiver extends WakefulBroadcastReceiver {

    public static final int WAKE_TYPE_UPLOAD = 2;
    private static AlarmManager alarm;
    private static PendingIntent pIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        int wakeType = intent.getExtras().getInt("wakeType");
        switch (wakeType) {
            case WAKE_TYPE_UPLOAD:
                startFallDetectionService(context);
                break;
            default:
                break;
        }
    }

    public static void startFallDetectionService(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent newUpload = new Intent(context, FallDetectionService.class);
            String packageName = context.getPackageName();
            PowerManager pm = (PowerManager) context.getSystemService(POWER_SERVICE);
            if (!pm.isIgnoringBatteryOptimizations(packageName)) {
                newUpload.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                newUpload.setData(Uri.parse("package:" + packageName));
                startWakefulService(context, newUpload);
            }
        }
    }

    @SuppressLint("NewApi")
    public static void setAlarm(Context context, int wakeType, Calendar startTime) {
        alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, WakeUpReceiver.class);
        intent.putExtra("wakeType", wakeType);
        pIntent = PendingIntent.getBroadcast(context, wakeType, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.KITKAT) {
            alarm.setExact(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis() + 5000, pIntent);
        } else {
            alarm.set(AlarmManager.RTC_WAKEUP, startTime.getTimeInMillis() + 5000, pIntent);
        }
    }
}
