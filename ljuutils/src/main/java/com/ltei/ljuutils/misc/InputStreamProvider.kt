package com.ltei.ljuutils.misc

import java.io.File
import java.io.InputStream

interface IInputStreamProvider {
    fun tryOpenStream(): InputStream?
    fun openStream(): InputStream = tryOpenStream()!!
}

class FileInputStreamProvider(val file: File) : IInputStreamProvider {
    override fun tryOpenStream(): InputStream? = file.inputStream()
    override fun openStream(): InputStream = file.inputStream()
}

fun File.toStreamProvider() = FileInputStreamProvider(this)