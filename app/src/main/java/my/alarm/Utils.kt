package my.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import my.alarm.nativeuse.WakeUpActivity
import java.util.*

fun Context.showNotificationWithFullScreenIntent(
    channelId: String = CHANNEL_ID,
    title: String,
    description: String
) {
    val builder = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(android.R.drawable.arrow_up_float)
        .setContentTitle(title)
        .setContentText(description)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setDefaults(Notification.DEFAULT_ALL)
        .setCategory(NotificationCompat.CATEGORY_ALARM)
        .setFullScreenIntent(getFullScreenIntent(), true)

    val DEFAULT_VIBRATE_PATTERN = longArrayOf(0, 250, 250, 250)
    builder.setVibrate(DEFAULT_VIBRATE_PATTERN)
    builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)


    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    with(notificationManager) {
        buildChannel()

        val notification = builder.build()

        notify(0, notification)
    }
}

private fun NotificationManager.buildChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "Example Notification Channel"
        val descriptionText = "This is used to demonstrate the Full Screen Intent"
        val importance = NotificationManager.IMPORTANCE_MAX
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
            enableLights(true);
            setLightColor(Color.CYAN);
            enableVibration(true);
            setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        }

        createNotificationChannel(channel)
    }
}

private fun Context.getFullScreenIntent(): PendingIntent {
    val destination = WakeUpActivity::class.java
    val intent = Intent(this, destination)

    // flags and request code are 0 for the purpose of demonstration
    return PendingIntent.getActivity(this, 0, intent, 0)
}

fun getDay( day : String): Int {
    when(day){
        "Monday" -> return Days.MONDAY
        "Tuesday" -> return Days.TUESDAY
        "Wednesday" -> return Days.WEDNESDAY
        "Thursday" -> return Days.THURSDAY
        "Friday" -> return Days.FRIDAY
        "Saturday" -> return Days.SATURDAY
        "Sunday" -> return Days.SUNDAY
    }
    return Days.MONDAY
}

fun closest(of: Long, `in`: List<Long>): Long {
    var min = Long.MAX_VALUE
    var closest = of
    for (v in `in`) {
        val diff = Math.abs(v - of)
        if (diff < min) {
            min = diff
            closest = v
        }
    }
    return closest
}

private const val CHANNEL_ID = "channelId"