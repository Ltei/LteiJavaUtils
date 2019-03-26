package com.ltei.ljugson

import com.google.gson.JsonElement
import com.google.gson.JsonObject

class JsonBuilder {

    private val json = JsonObject()

    fun addAny(name: String, value: Any): JsonBuilder {
        return addJson(name, GsonUtils.toGson(value))
    }

    fun addJson(name: String, value: JsonElement): JsonBuilder {
        json.add(name, value)
        return this
    }

    fun build(): JsonObject {
        return json
    }

}