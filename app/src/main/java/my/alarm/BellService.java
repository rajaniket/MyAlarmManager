package my.alarm;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Vibrator;
import android.util.Log;

import my.alarm.nativeuse.WakeUpActivity;

public class BellService extends Service {
    private final long[] ALARM_VIBRATION_PATTERN = {1000, 200, 700, 200, 400, 200};
    private boolean mAlarmIgnore = false;
    private boolean[] mDay = new boolean[7];
    private boolean mEnable = false;
    private int mId = 0;
    private int mTime = -1;
    private boolean[] mTmpSettingDay = new boolean[7];

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        Log.d("kairylab", "BellService::onDestroy()");
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append("BellService::onStartCommand(");
        sb.append(intent == null);
        sb.append(",");
        sb.append(i);
        sb.append(",");
        sb.append(i2);
        sb.append(");");
        Log.d("kairylab", sb.toString());
        super.onStartCommand(intent, i, i2);
//        if (intent == null) {
//            stopSelf();
//            return 2;
//        }
//        this.mId = intent.getIntExtra("id", 0);

//        BellService.this.showNotificationWithFullScreenIntent(title = alarmDataModel.title, description = alarmDataModel.desc);
//        startForeground(1, AlarmCommonDefs.getAlarmingNotification(this, this.mId));
        Intent intent2 = new Intent(this, WakeUpActivity.class);
//            intent2.setFlags(402653184);
//            intent2.putExtra("id", this.mId);
        startActivity(intent2);
//            initializePlayer(this.mId);
//            initializeVibrator(this.mId);
//            AlarmCommonDefs.cancelSnoozeNotification(this, this.mDb, this.mId);
        return START_STICKY;
    }


    public IBinder onBind(Intent intent) {
        Log.d("kairylab", "BellService::onBind()");
        return null;
    }


}
