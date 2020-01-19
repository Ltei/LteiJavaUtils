package com.ltei.ljuutils.utils

object SystemUtils {
    fun setProxy(host: String, port: String) {
        val properties = System.getProperties()
        properties["http.proxyHost"] = host
        properties["http.proxyPort"] = port
        properties["https.proxyHost"] = host
        properties["https.proxyPort"] = port
    }
}