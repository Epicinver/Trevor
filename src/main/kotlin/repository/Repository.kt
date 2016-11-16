package repository


/**
 * Created by sergeyopivalov on 15.11.16.
 */
interface Repository <T> {

    fun create(entity : T)

    fun delete(id : Long)

    fun getById(id: Long) : T?

    fun update(id : Long,
               key : String,
               value: String,
               closeConnection: Boolean)
}