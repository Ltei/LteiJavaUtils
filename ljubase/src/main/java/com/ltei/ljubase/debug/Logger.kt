package com.ltei.ljubase.debug

class Logger(
    val clazz: Class<*>,
    var level: Int = BASE_LEVEL,
    val name: String = clazz.simpleName!!
) {

    fun info(message: Any = "") {
        if (level <= LEVEL_INFO && BASE_LEVEL <= LEVEL_INFO) {
            println("[$TAG][$name][${logParametrizedName(clazz)}][INFO] $message")
        }
    }

    fun debug(message: Any = "") {
        if (level <= LEVEL_DEBUG && BASE_LEVEL <= LEVEL_DEBUG) {
            println("[$TAG][$name][${logParametrizedName(clazz)}][DEBUG] $message")
        }
    }

    fun warn(message: Any = "") {
        if (level <= LEVEL_WARN && BASE_LEVEL <= LEVEL_WARN) {
            println("[$TAG][$name][${logParametrizedName(clazz)}][WARN] $message")
        }
    }

    fun err(message: Any = "") {
        if (level <= LEVEL_ERR && BASE_LEVEL <= LEVEL_ERR) {
            println("[$TAG][$name][${logParametrizedName(clazz)}][ERR] $message")
        }
    }

    private fun logParametrizedName(c: Class<*>): String {
        val stackTrace = Thread.currentThread().stackTrace.drop(STACKTRACE_DELTA)

        val lineNumber = stackTrace.first().lineNumber.toString()
        val methodName: String = stackTrace.find {
            !it.methodName.contains("$") && it.methodName != "invoke"
        }?.methodName ?: stackTrace.first().methodName

        return "[${logClassName(c)}:$methodName:$lineNumber]"
    }

    private fun logClassName(c: Class<*>): String {
        val canonicalName = c.canonicalName
        return if (canonicalName == null) {
            c.name
        } else {
            val split = canonicalName.split(".")
            if (split.size < LOG_CLASS_INDEX) {
                canonicalName
            } else {
                split.subList(LOG_CLASS_INDEX, split.size).fold(StringBuilder()) { acc, item ->
                    acc.append(item).append(".")
                    acc
                }.removeSuffix(".").toString()
            }
        }
    }

    companion object {
        const val TAG = "XAV"

        const val LEVEL_INFO = 1
        const val LEVEL_DEBUG = 2
        const val LEVEL_WARN = 3
        const val LEVEL_ERR = 4

        const val STACKTRACE_DELTA_JAVA = 3
        const val STACKTRACE_DELTA_ANDROID = 4

        var STACKTRACE_DELTA = STACKTRACE_DELTA_JAVA
        const val LOG_CLASS_INDEX = 3

        var BASE_LEVEL = LEVEL_DEBUG

        val DEFAULT = Logger(this::class.java)
    }

}