package com.ltei.ljugson

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class JsonArrayPersister<T>(
        private val gson: Gson,
        val name: String,
        val folder: File,
        val itemsPerFile: Int,
        itemClass: Class<*>
) {

    private val typeToken = TypeToken.get(itemClass).type
    private val listTypeToken = TypeToken.getParameterized(List::class.java, itemClass).type

    private val fileNameRegex = Regex("^${name}_[0-9]+.json\$")


    init {
        if (!folder.exists()) folder.mkdir()
    }


    private fun getStashFolder() = File(folder, "stash")
    private fun getFile(index: Int) = File(folder, "${name}_$index.json")
    private fun listSavedFiles(): List<File> = folder.listFiles { file -> fileNameRegex.matches(file.name) }.toList()

    fun load(): MutableList<T> {
        val result = mutableListOf<T>()
        for (file in listSavedFiles()) {
            val list = gson.fromJson(file.reader(), listTypeToken) as List<T>
            result.addAll(list)
        }
        return result
    }

    fun save(list: List<T>) {
        for (file in listSavedFiles()) {
            file.copyTo(File(getStashFolder(), file.name), overwrite = true)
            file.delete()
        }

        var index = 0
        var file = getFile(index)
        while (true) {
            val idx0 = index * itemsPerFile
            if (list.size < idx0 + itemsPerFile) {
                val subList = list.subList(idx0, list.lastIndex)
                file.writeText(gson.toJson(subList))
                break
            } else {
                val subList = list.subList(idx0, idx0 + itemsPerFile)
                file.writeText(gson.toJson(subList))
                index += 1
                file = getFile(index)
            }
        }
    }

    companion object {
        inline fun <reified T> create(
                gson: Gson,
                name: String,
                folder: File,
                itemsPerFile: Int
        ) = JsonArrayPersister<T>(gson, name, folder, itemsPerFile, T::class.java)
    }

}