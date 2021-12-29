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

private const val CHANNEL_ID = "channelId"

fun showNotificationWithFullScreenIntent(
    context: Context,
    channelId: String,
    title: String,
    description: String
) : Notification {
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.arrow_up_float)
        .setContentTitle(title)
        .setContentText(description)
        .setPriority(NotificationCompat.PRIORITY_MAX)
        .setDefaults(Notification.DEFAULT_ALL)
        .setCategory(NotificationCompat.CATEGORY_ALARM)
        .setFullScreenIntent(getFullScreenIntent(context), true)

    val DEFAULT_VIBRATE_PATTERN = longArrayOf(0, 250, 250, 250)
    builder.setVibrate(DEFAULT_VIBRATE_PATTERN)
    builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI)


    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    with(notificationManager) {
        buildChannel()

        return builder.build()
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

private fun getFullScreenIntent(context: Context): PendingIntent {
    val destination = WakeUpActivity::class.java
    val intent = Intent(context, destination)

    // flags and request code are 0 for the purpose of demonstration
    return PendingIntent.getActivity(context, 0, intent, 0)
}