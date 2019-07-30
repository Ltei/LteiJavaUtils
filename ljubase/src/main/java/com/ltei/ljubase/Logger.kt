package com.ltei.ljubase

class Logger(val clazz: Class<*>, var level: Int = LEVEL_DEBUG) {

    val name = clazz.simpleName!!

    constructor(o: Any, level: Int = LEVEL_DEBUG): this(o::class.java, level)

    fun debug(message: String = "") {
        if (level <= LEVEL_DEBUG && BASE_LEVEL <= LEVEL_DEBUG) {
            println("[$TAG][$name][${logParametrizedName(clazz)}][DEBUG] $message")
        }
    }

    fun info(message: String = "") {
        if (level <= LEVEL_INFO && BASE_LEVEL <= LEVEL_INFO) {
            println("[$TAG][$name][${logParametrizedName(clazz)}][INFO] $message")
        }
    }

    fun warn(message: String = "") {
        if (level <= LEVEL_WARN && BASE_LEVEL <= LEVEL_WARN) {
            println("[$TAG][$name][${logParametrizedName(clazz)}][WARN] $message")
        }
    }

    fun err(message: String = "") {
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

        const val LEVEL_DEBUG = 1
        const val LEVEL_INFO = 2
        const val LEVEL_WARN = 3
        const val LEVEL_ERR = 4

        const val STACKTRACE_DELTA = 3 // 4
        const val LOG_CLASS_INDEX = 3

        const val BASE_LEVEL = LEVEL_DEBUG

        val DEFAULT = Logger(this::class.java)
    }

}