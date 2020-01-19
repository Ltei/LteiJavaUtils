package com.ltei.ljubase.interfaces

import java.io.File
import java.io.InputStream

interface IStreamProvider {
    fun tryOpenStream(): InputStream?
    fun openStream(): InputStream = tryOpenStream()!!
}

class FileStreamProvider(val file: File) : IStreamProvider {
    override fun tryOpenStream(): InputStream? = file.inputStream()
    override fun openStream(): InputStream = file.inputStream()
}

fun File.toStreamProvider() = FileStreamProvider(this)