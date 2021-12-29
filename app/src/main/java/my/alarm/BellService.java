package my.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import my.alarm.nativeuse.WakeUpActivity;

public class BellService extends Service {

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        startForeground(1, ServiceUtilsKt.showNotificationWithFullScreenIntent(this, "channelId","",""));
        Intent intent2 = new Intent(this, WakeUpActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent2);
        return START_STICKY;
    }


    public IBinder onBind(Intent intent) {
        return null;
    }


}
