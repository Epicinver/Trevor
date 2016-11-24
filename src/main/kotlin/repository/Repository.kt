package repository

import java.util.*


/**
 * Created by sergeyopivalov on 15.11.16.
 */
interface Repository <T> {

    fun create(entity : T)

    fun delete(id : Long)

    fun getById(id: Long) : T?

    fun getAll() : ArrayList<T>

    fun update(id : Long,
               key : String,
               value: String,
               closeConnection: Boolean)
}