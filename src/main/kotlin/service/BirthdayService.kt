package service

import entity.User
import java.util.*

/**
 * Created by sergeyopivalov on 24/11/2016.
 */
class BirthdayService : BaseService(){

    fun getUsersWasBornToday(today: String): ArrayList<User>? =
            userRepository.getAll().filter { it.birthday == today } as ArrayList<User>

    fun getUsersForNotify(birthdayUser: User): ArrayList<User> =
            userRepository.getAll().filter { it.username != birthdayUser.username } as ArrayList<User>
}