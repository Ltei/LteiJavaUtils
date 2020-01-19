package com.ltei.ljubase

object ResourcesUtils {
    fun getFile(path: String) = javaClass.classLoader.getResource(path).file
    fun getStream(path: String) = javaClass.classLoader.getResourceAsStream(path)
}