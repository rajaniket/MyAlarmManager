package my.alarm

import android.content.Context
import android.text.TextUtils
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent


class MyAlarmManager {

    companion object{

        fun createAlarm(context: Context, listener: MyAlarmListener?){
            listener?.let {

                if (listener.listOfTimesInSec().isEmpty()){
                    Toast.makeText(context,"Time must not be empty",Toast.LENGTH_LONG).show()
                    return
                }

                // setting calendar start date - it may be today or any future date
                val now = Calendar.getInstance() // - initially today
                if (!TextUtils.isEmpty(listener.startDate())){
                    val date = listener.startDate()!!
                    val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    try {
                        now.time = df.parse(date)!!  // - set to future date
                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }

                }

                AlarmUtils.setAlarm(now,listener.listOfTimesInSec(),context,listener.getId(),listener.listOfDays(),listener.getTitle(),listener.getDescription())

            }
        }

        fun cancelAlarm(context: Context, id : Int){
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, AlarmReceiver::class.java)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
            val pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0)
            alarmManager.cancel(pendingIntent)
        }

    }
}