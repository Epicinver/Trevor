package repository

import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import entity.User
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
import java.util.*

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
class UserRepository : Repository <User> {

//    val dao = Injekt.get<Dao<User, Long>>()
    val dao : Dao<User, Long> = DaoManager.createDao(JdbcConnectionSource("jdbc:sqlite:test.s3db"), User::class.java)

    override fun create(user: User) { dao.create(user) }

    override fun delete(chatId: Number) { dao.deleteById(chatId.toLong()) }

    override fun getById(chatId: Number): User? = dao.queryForId(chatId.toLong())

    override fun getAll(): ArrayList<User> = dao.queryForAll() as ArrayList<User>

    override fun update(chatId: Number, column : String, value : Any ) {
        dao.updateBuilder().apply {
            where().eq("chatId", chatId)
            updateColumnValue(column, value)
            update()
        }
    }

}