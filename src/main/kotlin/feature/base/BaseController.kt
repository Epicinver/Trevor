package feature.base

import bot.SmlSalaryBot
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 25/11/2016.
 */
abstract class BaseController {
    protected val bot = Injekt.get<SmlSalaryBot>()
}