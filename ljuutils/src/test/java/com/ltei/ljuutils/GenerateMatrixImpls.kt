package com.ltei.ljuutils

import java.io.File

fun main() {

    val implString = """
        class |T|Matrix private constructor(
            override val rows: Int,
            override val cols: Int,
            val array: |T|Array
        ) : IArrayMatrix<|T|> {

            constructor(rows: Int, cols: Int) : this(rows, cols, |T|Array(rows * cols))
            constructor(rows: Int, cols: Int, init: |T|) : this(rows, cols, |T|Array(rows * cols) { init })
            constructor(rows: Int, cols: Int, init: (row: Int, col: Int) -> |T|) :
                    this(rows, cols, |T|Array(rows * cols) {
                        init(IArrayMatrix.getRowFromArrayIndex(rows, it), IArrayMatrix.getColFromArrayIndex(rows, it))
                    })

            override fun get1d(index: Int) = array[index]
            override fun set1d(index: Int, value: |T|) {
                array[index] = value
            }

        }


    """.trimIndent()

    val path = "ljuutils/src/main/java/com/ltei/ljuutils/matrix/MatrixImpls.kt"

    val file = File(path)
    if (!file.exists()) file.createNewFile()
    file.writeText("package com.ltei.ljuutils.matrix\n\n")
    file.appendText(implString.replace("|T|", "Int"))
    file.appendText(implString.replace("|T|", "Short"))
    file.appendText(implString.replace("|T|", "Byte"))
    file.appendText(implString.replace("|T|", "Float"))
    file.appendText(implString.replace("|T|", "Double"))

}