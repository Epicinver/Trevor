package feature.birthdays

import entity.User
import feature.base.BaseController
import res.BirthdayStrings
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sergeyopivalov on 24/11/2016.
 */
object BirthdayController : BaseController<BirthdayService>(BirthdayService::class) {

    private var birthdayUsers: ArrayList<User>? = null
    private var birthdayAtWeekendUsers: ArrayList<User>? = null

    fun notifyUsersAboutBirthdays() {
        birthdayAtWeekendUsers?.apply {
            var birthdayUser: User? = null
            forEach { birthdayUser = it }
            flatMap { service.getUsersForNotify(birthdayUser!!) }
            forEach { bot.performSendMessage(it.chatId,
                        "${BirthdayStrings.notificationWeekend} ${birthdayUser!!.smlName}") }
            clear()
        }

        birthdayUsers?.apply {
            var birthdayUser: User? = null
            forEach { birthdayUser = it }
            flatMap { service.getUsersForNotify(birthdayUser!!) }
            forEach { bot.performSendMessage(it.chatId,
                        "${BirthdayStrings.notificationWeekend} ${birthdayUser!!.smlName}") }
            clear()
        }

    }

    fun checkBirthdays(weekend: Boolean = false) {
        if (weekend) birthdayAtWeekendUsers = getBirthdayUsers() else birthdayUsers = getBirthdayUsers()
    }

    private fun getBirthdayUsers(): ArrayList<User>? = service.getUsersWasBornToday(getCurrentDate())

    private fun getCurrentDate(): String = SimpleDateFormat("dd.MM.yyyy").let { it.format(Date()) }
}