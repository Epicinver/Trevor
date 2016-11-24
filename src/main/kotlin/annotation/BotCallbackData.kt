package annotation

/**
 * Created by sergeyopivalov on 16.11.16.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BotCallbackData(val callbackData : String)
