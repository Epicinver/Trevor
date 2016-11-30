package repository

import java.util.*


/**
 * Created by sergeyopivalov on 15.11.16.
 */
interface Repository<T> {

    fun create(entity: T)

    fun delete(id: Number)

    fun getById(id: Number): T?

    fun getAll(): ArrayList<T>

    fun update(id: Number, column: String, value: Any)
}