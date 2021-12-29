package my.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.text.format.DateFormat
import com.google.gson.Gson
import java.util.*
import android.os.Build

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        val alarmSharedPref = AlarmSharedPref(context!!)
        val id = Calendar.getInstance().timeInMillis / 100000
        val nextAlarmData = alarmSharedPref.getAlarmData("" + id.toInt())

        if (TextUtils.isEmpty(nextAlarmData)) return

        val alarmDataModel = Gson().fromJson(nextAlarmData,AlarmDataModel::class.java)

        val intent2 = Intent(context.applicationContext, BellService::class.java)
        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        if (Build.VERSION.SDK_INT >= 26) {
            context.applicationContext!!.startForegroundService(intent2)
        } else {
            context.applicationContext.startService(intent2)
        }

        // first check for repeated time
        if (!alarmDataModel.listOfTimeInSec.isNullOrEmpty()){
            val now = Calendar.getInstance()
            AlarmUtils.setAlarm(now,alarmDataModel.listOfTimeInSec,context,id.toInt(),alarmDataModel.listOfDays,alarmDataModel.title, alarmDataModel.desc)
            return
        }

        val now = Calendar.getInstance()
        val startDate = now.time
        val dayOfTheWeek = DateFormat.format("EEEE", startDate) as String
        val today = getDay(dayOfTheWeek)
        val weekDays = arrayListOf(Days.MONDAY, Days.TUESDAY, Days.WEDNESDAY, Days.THURSDAY, Days.FRIDAY, Days.SATURDAY, Days.SUNDAY)
        val dayValue = weekDays[today]

        if (alarmDataModel.listOfDays.isNullOrEmpty()) return
        val sortedList = alarmDataModel.listOfDays.sortedBy { it }.filterIndexed { index, i -> i != dayValue }

        var i = sortedList[0]
        val list : java.util.ArrayList<Int> = java.util.ArrayList()
        for (j in 0..5){
            if (i == 6){
                i=0
                list.add(i)
                continue
            }
            i++
            list.add(i)
        }

        var nextAlarmDay = dayValue
        for (k in list){
            if (alarmDataModel.listOfDays.contains(k)){
                nextAlarmDay = k
                break
            }
        }
        val tmp = Calendar.getInstance()
        tmp.add(Calendar.DAY_OF_WEEK, list.indexOf(nextAlarmDay))

        AlarmUtils.setAlarm(tmp,alarmDataModel.listOfTimeInSec,context,id.toInt(),alarmDataModel.listOfDays, alarmDataModel.title, alarmDataModel.desc)


    }


}