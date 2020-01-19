package com.ltei.ljugson

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.File

class JsonObjectPersitance(
        val name: String,
        val folder: File,
        val itemsPerFile: Int
) {

    val fileNameRegex = Regex("^${name}_[0-9]+.json\$")
    val jsonParser = JsonParser()


    init {
        if (!folder.exists()) folder.mkdir()
    }


    private fun getStashFolder() = File(folder, "stash")
    private fun getFile(index: Int) = File(folder, "${name}_$index.json")
    private fun listSavedFiles(): List<File> = folder.listFiles { file -> fileNameRegex.matches(file.name) }.toList()

    fun save(obj: JsonObject) {
        for (file in listSavedFiles()) {
            file.copyTo(File(getStashFolder(), file.name), overwrite = true)
            file.delete()
        }

        splitJsonObject(obj, itemsPerFile).mapIndexed { index, jsonObject ->
            getFile(index).writeText(jsonObject.toString())
        }
    }

    fun load(): JsonObject {
        return mergeJsonObjects(listSavedFiles().map { jsonParser.parse(it.reader()).asJsonObject })
    }

    inline fun load(block: (key: String, value: JsonElement) -> Boolean): JsonObject {
        return mergeJsonObjects(folder.listFiles { file -> fileNameRegex.matches(file.name) }.map {
            val obj = jsonParser.parse(it.reader()).asJsonObject
            for (entry in obj.entrySet().toList())
                if (!block(entry.key, entry.value))
                    obj.remove(entry.key)
            obj
        })
    }

    companion object {
        fun mergeJsonObjects(objs: List<JsonObject>): JsonObject {
            val result = JsonObject()
            for (obj in objs)
                for (key in obj.keySet())
                    result.add(key, obj.get(key))
            return result
        }

        fun splitJsonObject(obj: JsonObject, attrPerObj: Int): List<JsonObject> {
            val keys = obj.keySet().toList()
            var index = 0
            val result = mutableListOf<JsonObject>()
            while (true) {
                val resultObj = JsonObject()
                result.add(resultObj)
                val idx0 = index * attrPerObj
                if (keys.size < idx0 + attrPerObj) {
                    val subList = keys.subList(idx0, keys.lastIndex)
                    for (key in subList)
                        resultObj.add(key, obj.get(key))
                    break
                } else {
                    val subList = keys.subList(idx0, idx0 + attrPerObj)
                    for (key in subList)
                        resultObj.add(key, obj.get(key))
                    index += 1
                }
            }
            return result
        }
    }

}