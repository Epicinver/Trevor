package database

import java.sql.Connection
import java.sql.DriverManager


/**
 * Created by sergeyopivalov on 09/11/2016.
 */
class DatabaseHelper {

    companion object {
        private val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users" +
                " (USERNAME TEXT PRIMARY KEY NOT NULL," +
                "CHAT_ID INTEGER NOT NULL," +
                "SML_NAME TEXT," +
                "BIRTHDAY TEXT," +
                "ROLE TEXT);"

        private val DATABASE_NAME = "jdbc:sqlite:test.s3db"

        fun createDb() {
            Class.forName("org.sqlite.JDBC")

            with(DriverManager.getConnection(DATABASE_NAME)) {
                with(createStatement()) {
                    execute((SQL_CREATE_TABLE))
                    close()
                }
                close()
            }
        }

        fun openDb(): Connection {
            val connection = DriverManager.getConnection(DATABASE_NAME)
            connection.autoCommit = false
            return connection

        }

    }
}