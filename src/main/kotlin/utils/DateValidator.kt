package utils

/**
 * Created by sergeyopivalov on 16.11.16.
 */
object DateValidator {

    fun validateBirthday(birtday : String) : Boolean {
        val regex = Regex("^(0[1-9]|[12][0-9]|3[01])[. /.](0[1-9]|1[012])[. /.](19|20)\\d\\d$")
        return regex.containsMatchIn(birtday)

    }
}