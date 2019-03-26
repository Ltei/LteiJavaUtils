package com.ltei.ljugson

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

object GsonUtils {

    var defaultGson = Gson()

    fun toGson(o: Any, gson: Gson = defaultGson): JsonElement {
        return gson.toJsonTree(o)
    }

    inline fun <reified T> fromGson(json: JsonElement, gson: Gson = defaultGson): T? {
        return gson.fromJson(json, T::class.java)
    }

    inline fun <reified T> fromGson(json: String, gson: Gson = defaultGson): T? {
        return gson.fromJson(json, T::class.java)
    }

    fun fromGson(json: JsonObject, c: Class<*>, gson: Gson = defaultGson): Any {
        return gson.fromJson(json, c)
    }


    fun asStringOrNull(json: JsonElement): String? {
        return try {
            json.asString
        } catch (t: Throwable) {
            null
        }
    }

    fun asIntOrNull(json: JsonElement): Int? {
        return try {
            json.asInt
        } catch (t: Throwable) {
            null
        }
    }

}