package com.ltei.ljuutils

import java.io.File

fun main() {

    val implString = """
        class |T|CircularQueue(private val buffer: |T|Array) : AbstractCircularQueue<|T|>(buffer.size) {

            constructor(bufferSize: Int) : this(|T|Array(bufferSize))

            override fun getBufferValue(bufferIndex: Int): |T|? = buffer[bufferIndex]

            override fun setBufferValue(bufferIndex: Int, value: |T|) {
                buffer[bufferIndex] = value
            }

            override fun removeBufferValue(bufferIndex: Int) {}
        }


    """.trimIndent()

    val path = "ljuutils/src/main/java/com/ltei/ljuutils/collections/CircularQueueImpls.kt"

    val file = File(path)
    if (!file.exists()) file.createNewFile()
    file.writeText("package com.ltei.ljuutils.collections\n\n")
    file.appendText(implString.replace("|T|", "Int"))
    file.appendText(implString.replace("|T|", "Short"))
    file.appendText(implString.replace("|T|", "Byte"))
    file.appendText(implString.replace("|T|", "Float"))
    file.appendText(implString.replace("|T|", "Double"))

}