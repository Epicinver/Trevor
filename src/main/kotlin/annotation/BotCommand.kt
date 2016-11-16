package annotation

/**
 * Created by sergeyopivalov on 08/11/2016.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BotCommand (val command : String)
