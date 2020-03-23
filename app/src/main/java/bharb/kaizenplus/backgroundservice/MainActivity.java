package bharb.kaizenplus.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar timeNow = Calendar.getInstance();
        WakeUpReceiver.setAlarm(this, WakeUpReceiver.WAKE_TYPE_UPLOAD, timeNow);
        getPackageManager().setComponentEnabledSetting(new ComponentName(this, BootReceiver.class), PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }
}
