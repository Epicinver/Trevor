package entity

/**
 * Created by sergeyopivalov on 16.11.16.
 */
class User(var username: String,
           val chatId: Long,
           var smlName: String? = null,
           var birthday: String? = null)
