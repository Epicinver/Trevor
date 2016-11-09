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
                "SML_NAME TEXT NOT NULL," +
                "BIRTHDAY TEXT," +
                "ROLE TEXT NOT NULL);"

        private val DATABASE_NAME = "jdbc:sqlite:test.s3db"

        fun createDb() {
                Class.forName("org.sqlite.JDBC")

                DriverManager.getConnection(DATABASE_NAME).let { connection ->
                    connection.createStatement(). let { statement ->
                        statement.execute((SQL_CREATE_TABLE))
                        statement.close()
                    }
                    connection.close()
                }
        }

        fun openDb(): Connection = DriverManager.getConnection(DATABASE_NAME)

        fun closeDb(connection : Connection) {
            connection.close()
        }

    }
}