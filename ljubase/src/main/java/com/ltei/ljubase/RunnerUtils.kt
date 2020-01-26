package com.ltei.ljubase

import java.lang.Exception

object RunnerUtils {
    inline fun <T> tryOrNull(block: () -> T) = try { block() } catch (e: Exception) { null }
}