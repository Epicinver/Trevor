package database

/**
 * Created by sergeyopivalov on 10/11/2016.
 */
abstract class DatabaseRepository {

     fun executeTransaction(transaction : String) {
        with(DatabaseHelper.openDb()) {
            with(createStatement()) {
                executeUpdate(transaction)
                close()
            }
            commit()
            close()
        }
    }

}