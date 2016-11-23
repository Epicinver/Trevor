package entity

/**
 * Created by sergeyopivalov on 16.11.16.
 */
class User(var username: String,
           val chatId: Long,
           var smlName: String? = null,
           var birthday: String? = null,
           var role: String) {
    //todo попробовать без этого метода
    override fun equals(other: Any?): Boolean {
        return if (other == null) false else super.equals(other)
    }
}
