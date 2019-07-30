package com.ltei.ljugson

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement


inline fun <reified T> Gson.fromJson(json: JsonElement): T = fromJson(json, T::class.java)
inline fun <reified T> Gson.fromJsonArray(json: JsonArray): List<T> = json.map { fromJson<T>(it) }

fun JsonElement.asBooleanOrNull() = tryCatch { asBoolean }
fun JsonElement.asIntOrNull() = tryCatch { asInt }
fun JsonElement.asFloatOrNull() = tryCatch { asFloat }
fun JsonElement.asDoubleOrNull() = tryCatch { asDouble }
fun JsonElement.asStringOrNull() = tryCatch { asString }

private inline fun <T> tryCatch(block: () -> T): T? = try {
    block()
} catch (t: Throwable) {
    null
}