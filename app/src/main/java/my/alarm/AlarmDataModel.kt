package my.alarm

data class AlarmDataModel(
    val listOfDays : ArrayList<Int>,
    val listOfTimeInSec : ArrayList<Long>,
    val startDate : String,
    val id : Int,
    val title : String,
    val desc : String
)
