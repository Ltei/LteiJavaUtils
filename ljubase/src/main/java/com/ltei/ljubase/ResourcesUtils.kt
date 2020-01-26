package com.ltei.ljubase

import java.io.InputStream

object ResourcesUtils {
    fun getFile(path: String): String? = javaClass.classLoader.getResource(path).file
    fun getStream(path: String): InputStream? = javaClass.classLoader.getResourceAsStream(path)
}