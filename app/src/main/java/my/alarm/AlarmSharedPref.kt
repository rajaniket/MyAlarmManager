package my.alarm

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

class AlarmSharedPref(context: Context) {

    private val mPref: SharedPreferences = context.getSharedPreferences("ALARM_SHARED_PREF", Context.MODE_PRIVATE)
    private var mEditor: Editor = mPref.edit()

    fun saveAlarmData(key: String, value: String) {
        mEditor = mPref.edit()
        mEditor.putString(key, value)
        mEditor.apply()
    }

    fun getAlarmData(key : String) : String {
        return mPref.getString(key, "")!!
    }


}