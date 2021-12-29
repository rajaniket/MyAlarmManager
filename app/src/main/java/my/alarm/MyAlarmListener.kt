package my.alarm

interface MyAlarmListener {
    fun listOfDays() : ArrayList<Int>
    fun listOfTimesInSec() : ArrayList<Long>
    fun startDate() : String?
    fun getId() : Int
    fun getTitle() : String
    fun getDescription() : String
}