package com.example.techjini.loginapplicationsimple.utility

import com.google.gson.Gson
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type
import java.util.*

object JsonToObject {

    fun getResponse(filename: String, classOfT: Class<*>): Any? {
        val response = getResponseAsString(filename)
        var `object`: Any? = null
        if (response.isNotEmpty()) {
            `object` = Gson().fromJson<Class<*>>(response, classOfT)
        }
        return `object`
    }

    fun getResponseAsList(filename: String, type: Type): ArrayList<Any>? {
        val response = getResponseAsString(filename)
        return Gson().fromJson<ArrayList<Any>>(response, type)
    }

    private fun getResponseAsString(filename: String): String {
        val classLoader = JsonToObject::class.java.classLoader
        val filePath = "%s.json"
        // To load text file
        val input: InputStream
        try {
            input = classLoader.getResourceAsStream(String.format(filePath, filename))

            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()

            // byte buffer into a string

            return String(buffer)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ""
    }
}
