package utils

import java.io.FileInputStream
import java.util.*

/**
 * Created by sergeyopivalov on 25/11/2016.
 */
object PropertiesLoader {
    fun getProperty(property: String): String {
        val properties = Properties()
        FileInputStream("config.properties").use {
            properties.load(it)
        }
        return properties.getProperty(property)
    }
}