package featurecontroller

import entity.User
import res.BirthdayStrings
import service.BirthdayService
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sergeyopivalov on 24/11/2016.
 */
object BirthdayController : BaseController(){

    val service = Injekt.get<BirthdayService>()

    private var birthdayUsers: ArrayList<User>? = null
    private var birthdayAtWeekendUsers: ArrayList<User>? = null


    //TODO переделать на flatmap, без вложенности
    fun notifyUsersAboutBirthdays() {
        birthdayAtWeekendUsers?.apply {
            forEach { user ->
                service.getUsersForNotify(user)
                        .forEach { bot.performSendMessage(it.chatId, "${BirthdayStrings.notificationWeekend} ${user.smlName}") }
            }
            clear()
        }

        birthdayUsers?.apply {
            forEach { user ->
                service.getUsersForNotify(user)
                        .forEach { bot.performSendMessage(it.chatId, "${BirthdayStrings.notification} ${user.smlName}") }
            }
            clear()
        }
    }

    fun checkBirthdays(weekend: Boolean = false) {
        if (weekend) birthdayAtWeekendUsers = getBirthdayUsers() else birthdayUsers = getBirthdayUsers()
    }

    private fun getBirthdayUsers(): ArrayList<User>? = service.getUsersWasBornToday(getCurrentDate())

    private fun getCurrentDate(): String = SimpleDateFormat("dd.MM.yyyy").let {
        it.format(Date())
    }
}