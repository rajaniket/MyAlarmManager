package my.alarm

import android.app.AlarmManager
import android.app.AlarmManager.AlarmClockInfo
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.gson.Gson
import my.alarm.nativeuse.WakeUpActivity
import java.util.*
import kotlin.collections.ArrayList

class AlarmUtils {
    companion object {

        fun setAlarm(now : Calendar, listOfTimesInSec : ArrayList<Long>, context : Context, id : Int, listOfDays : ArrayList<Int>, title : String, desc : String){
            val currentHour: Int = now.get(Calendar.HOUR_OF_DAY)
            val currentMinutes: Int = now.get(Calendar.MINUTE)
            val currentSeconds: Int = now.get(Calendar.SECOND)
            val currentTimeInSec = ((((currentHour * 60) + currentMinutes) * 60) + currentSeconds).toLong()
            val timesList : List<Long> = listOfTimesInSec.sortedBy { it }
            val arraytwo = timesList.filterIndexed { index, item -> item > currentTimeInSec }

            if (arraytwo.isEmpty()) return
            val closestTime = closest(currentTimeInSec,arraytwo)
            val hours = closestTime / 3600
            val minutes = (closestTime % 3600) / 60
            val seconds = closestTime % 60

            // setting time to get the timeInMillis for alarmManager
            val tmp = Calendar.getInstance()
            tmp.add(Calendar.HOUR_OF_DAY, (hours - currentHour).toInt())
            tmp.add(Calendar.MINUTE, (minutes - currentMinutes).toInt())
            tmp.add(Calendar.SECOND, (seconds - currentSeconds).toInt())

            val timeInMillis = tmp.timeInMillis

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, AlarmReceiver::class.java)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);

            val pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0)

            // saving data to shared pref
            val alarmSharedPref = AlarmSharedPref(context)
            val alarmDataModel = AlarmDataModel(listOfDays, listOfTimesInSec, "",id,title, desc)
            alarmSharedPref.saveAlarmData("" + (timeInMillis/100000).toInt(), Gson().toJson(alarmDataModel))

            val timeInMillis1 = System.currentTimeMillis() + 10000
            Log.e("data","time : $timeInMillis1")

            val broadcast = PendingIntent.getBroadcast(context, 1, intent, 0)
            val broadcast2 = PendingIntent.getBroadcast(context, 0, Intent(context, WakeUpActivity::class.java), 0)

            with(alarmManager) {
                setAlarmClock(AlarmClockInfo(timeInMillis, broadcast2), broadcast)
//                setExact(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent)
            }
        }

    }

}