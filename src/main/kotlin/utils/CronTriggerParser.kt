package utils

import java.util.*

/**
 * Created by sergeyopivalov on 01/12/2016.
 */
object CronTriggerParser {

    fun parse(timestamp: Long): String {
        val calendar = Calendar.getInstance().apply {
            time = Date(timestamp)
        }
        return "0 ${calendar.get(Calendar.MINUTE)} " +
                "${calendar.get(Calendar.HOUR_OF_DAY)} " +
                "${calendar.get(Calendar.DAY_OF_MONTH)} " +
                "${calendar.get(Calendar.MONTH) + 1} ? " +
                "${calendar.get(Calendar.YEAR)}"
    }
}