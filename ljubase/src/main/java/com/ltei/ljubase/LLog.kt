package com.ltei.ljubase

object LLog {

    const val LOG_LEVEL_INFO = 0
    const val LOG_LEVEL_DEBUG = 1
    const val LOG_LEVEL_WARN = 2
    const val LOG_LEVEL_ERROR = 3

    private const val XAVIER_TAG: String = "XAV"
    var DISABLE_LOGS = false

    const val TAG: String = XAVIER_TAG
    const val LOG_LEVEL = LOG_LEVEL_INFO

    var LOG_CLASS_INDEX = 3

    @JvmOverloads
    fun info(c: Class<*>, message: String = "") {
        if (!DISABLE_LOGS && LOG_LEVEL <= LOG_LEVEL_INFO) {
            println("[${TAG}_INFO]${logParametrizedName(c)} : $message")
        }
    }

    @JvmOverloads
    fun debug(c: Class<*>, message: String = "") {
        if (!DISABLE_LOGS && LOG_LEVEL <= LOG_LEVEL_DEBUG) {
            println("[${TAG}_DEBUG]${logParametrizedName(c)} : $message")
        }
    }

    @JvmOverloads
    fun warn(c: Class<*>, message: String = "") {
        if (!DISABLE_LOGS && LOG_LEVEL <= LOG_LEVEL_WARN) {
            println("[${TAG}_WARN]${logParametrizedName(c)} : $message")
        }
    }

    @JvmOverloads
    fun error(c: Class<*>, message: String = "") {
        if (!DISABLE_LOGS && LOG_LEVEL <= LOG_LEVEL_ERROR) {
            println("[${TAG}_ERROR]${logParametrizedName(c)} : $message")
        }
    }

    private fun logParametrizedName(c: Class<*>): String {
        val stackTrace = Thread.currentThread().stackTrace.drop(4)

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

}