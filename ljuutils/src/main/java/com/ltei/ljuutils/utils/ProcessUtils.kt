package com.ltei.ljuutils.utils

fun Process.closeStreamsAndWait(): Int {
    outputStream.close()
    inputStream.close()
    errorStream.close()
    return waitFor()
}