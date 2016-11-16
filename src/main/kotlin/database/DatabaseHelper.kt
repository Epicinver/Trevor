package database

import java.sql.Connection
import java.sql.DriverManager


/**
 * Created by sergeyopivalov on 09/11/2016.
 */
object DatabaseHelper {

    val COLUMN_USERNAME = "USERNAME"
    val COLUMN_CHAT_ID = "CHAT_ID"
    val COLUMN_SML_NAME = "SML_NAME"
    val COLUMN_BIRTHDAY = "BIRTHDAY"
    val COLUMN_ROLE = "ROLE"

    private val DATABASE_NAME = "jdbc:sqlite:test.s3db"
    private var connection: Connection? = null

    private val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users" +
            " ($COLUMN_USERNAME TEXT PRIMARY KEY NOT NULL," +
            "$COLUMN_CHAT_ID INTEGER NOT NULL," +
            "$COLUMN_SML_NAME TEXT," +
            "$COLUMN_BIRTHDAY TEXT," +
            "$COLUMN_ROLE TEXT);"

    fun executeTransaction(transaction : String, closeDb : Boolean = false) {
        with(getConnection()) {
            with(createStatement()) {
                executeUpdate(transaction)
                commit()
            }
            if (closeDb) closeConnection()
        }
    }

    fun createDb() {
        Class.forName("org.sqlite.JDBC")
            connection = getConnection()
            connection?.createStatement()?.execute(SQL_CREATE_TABLE)
        }


    //todo catch sqlexception
    fun getConnection(): Connection {
        return if (connection == null) {
          with(DriverManager.getConnection(DATABASE_NAME)) {
              autoCommit = false
              this
          }
        } else {
            connection!!
        }

    }

    fun closeConnection() {
        connection?.close()
    }
}


