package com.ltei.ljuutils.misc

import java.io.InputStream
import java.net.URL

class JavaResource(val name: String) : InputStreamProvider {
    override fun tryOpenStream(): InputStream? = javaClass.getResourceAsStream(name)
    fun openURL(): URL? = javaClass.getResource(name)
}