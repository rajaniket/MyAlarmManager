package my.alarm.nativeuse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import my.alarm.Days
import my.alarm.MyAlarmListener
import my.alarm.MyAlarmManager
import my.alarm.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        MyAlarmManager.createAlarm(this, object : MyAlarmListener {
            override fun listOfDays(): ArrayList<Int> {
                return arrayListOf(Days.FRIDAY, Days.SATURDAY)
            }

            override fun listOfTimesInSec(): ArrayList<Long> {
                return arrayListOf(2 * 60 * 60, 16 * 60 * 60, ((20 * 60) + 44) * 60)
            }

            override fun startDate(): String? = null

            override fun getId(): Int = 0

            override fun getTitle(): String = "Title"

            override fun getDescription(): String = "desc"

        })

    }
}