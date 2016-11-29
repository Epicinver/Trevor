package repository

import com.j256.ormlite.dao.Dao
import entity.User
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
class UserRepository : Repository <User> {

    val dao = Injekt.get<Dao<User, Long>>()

    override fun create(user: User) { dao.create(user) }

    override fun delete(chatId: Number) { dao.deleteById(chatId.toLong()) }

    override fun getById(chatId: Number): User? = dao.queryForId(chatId.toLong())

    override fun getAll(): ArrayList<User> = dao.queryForAll() as ArrayList<User>

    override fun update(user: User) { dao.update(user) }

}