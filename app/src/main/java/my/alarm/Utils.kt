package my.alarm

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