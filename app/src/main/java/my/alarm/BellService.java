package my.alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import my.alarm.nativeuse.WakeUpActivity;

public class BellService extends Service {

    private int id;

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
        ServiceUtilsKt.cancelNotification(this,id);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        id = Integer.parseInt(intent.getStringExtra("id"));
        Log.e("data","service Id : " + id);
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("desc");
        startForeground(id, ServiceUtilsKt.showNotificationWithFullScreenIntent(this, "channelId",(title== null) ? "" : title,(description== null) ? "" : description));
        Intent intent2 = new Intent(this, WakeUpActivity.class);
        intent2.putExtra("id","" + id);
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent2);
        return START_STICKY;
    }


    public IBinder onBind(Intent intent) {
        return null;
    }


}
