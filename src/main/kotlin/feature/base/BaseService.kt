package feature.base

import entity.User
import repository.Repository
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

/**
 * Created by sergeyopivalov on 25/11/2016.
 */
abstract class BaseService {
    protected val userRepository = Injekt.get<Repository<User>>()
}