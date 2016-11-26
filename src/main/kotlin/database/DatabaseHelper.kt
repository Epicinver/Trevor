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
    val COLUMN_ROOM_ID = "ROOM_ID"
    val COLUMN_DESCRIPTION = "DESCRIPTION"
    val COLUMN_TIME = "TIME"
    val COLUMN_DURATION = "DURATION"

    private val DATABASE_NAME = "jdbc:sqlite:test.s3db"
    private var connection: Connection? = null

    private val SQL_CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users" +
            " ($COLUMN_USERNAME TEXT PRIMARY KEY NOT NULL," +
            "$COLUMN_CHAT_ID INTEGER NOT NULL," +
            "$COLUMN_SML_NAME TEXT," +
            "$COLUMN_BIRTHDAY TEXT," +
            "$COLUMN_ROLE TEXT);"

    private val SQL_CREATE_RESERVATIONS_TABLE = "CREATE TABLE IF NOT EXISTS reservations" +
            " ($COLUMN_CHAT_ID INTEGER PRIMARY KEY NOT NULL," +
            "$COLUMN_ROOM_ID INTEGER NOT NULL," +
            "$COLUMN_TIME TEXT," +
            "$COLUMN_DURATION TEXT);"

    private val SQL_CREATE_ROOMS_TABLE = "CREATE TABLE IF NOT EXISTS rooms" +
            " ($COLUMN_ROOM_ID INTEGER PRIMARY KEY NOT NULL AUTOINCREMENT," +
            "$COLUMN_DESCRIPTION TEXT NOT NULL);"

    fun executeTransaction(transaction: String, closeDb: Boolean = false) {
        with(getConnection()) {
            with(createStatement()) {
                execute(transaction)
                commit()
            }
            if (closeDb) closeConnection()
        }
    }

    fun createDb() {
        Class.forName("org.sqlite.JDBC")
        executeTransaction(SQL_CREATE_USERS_TABLE)
        executeTransaction(SQL_CREATE_RESERVATIONS_TABLE)
        executeTransaction(SQL_CREATE_ROOMS_TABLE)
        closeConnection()
    }


    //todo catch sqlexception
    fun getConnection(): Connection {
        if (connection == null) {
            connection = with(DriverManager.getConnection(DATABASE_NAME)) {
                autoCommit = false
                this
            }
        }
        return connection!!
    }

    fun closeConnection() {
        connection?.close()
        connection = null
    }
}


