package utils

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object Validator {

    fun validateBirthday(birthday: String): Boolean {
        val regex = Regex("^(0[1-9]|[12][0-9]|3[01])[. /.](0[1-9]|1[012])[. /.](19|20)\\d\\d$")
        return regex.containsMatchIn(birthday)

    }

    fun validateReserveTime(time: String): Boolean {
        val regex = Regex("^([0-1][0-9]|[2][0-3]):([0-5][0-9])$")
        return regex.containsMatchIn(time)
    }

    fun validateReserveDuration(duration: String): Boolean {
        return if (duration.toInt() >= 10 && duration.toInt() <= 120) true else false
    }
}