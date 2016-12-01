package utils

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object RegexValidator {

    fun validateBirthday(birthday: String): Boolean {
        val regex = Regex("^(0[1-9]|[12][0-9]|3[01])[. /.](0[1-9]|1[012])[. /.](19|20)\\d\\d$")
        return regex.containsMatchIn(birthday)

    }

    fun validateReserveDate(time: String): Boolean {
        val regex = Regex("^([1-9]|([012][0-9])|(3[01]))\\.([0]{0,1}[1-9]|1[012])\\.\\d\\d\\d\\d [012]{0,1}[0-9]:[0-6][0-9]$")
        return regex.containsMatchIn(time)
    }

    fun validateReserveDuration(duration: String): Boolean {
        return if (duration.toInt() >= 1 && duration.toInt() <= 120) true else false
    }
}