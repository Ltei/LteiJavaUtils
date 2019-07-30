package com.ltei.ljubase

open class DebugException : IllegalStateException {

    constructor(o: Any) : this(o.javaClass)
    constructor(o: Any, cause: Throwable) : this(o.javaClass, cause)
    constructor(o: Any, message: String) : this(o.javaClass, message)
    constructor(o: Any, message: String, cause: Throwable) : this(o.javaClass, message, cause)

    constructor(clazz: Class<*>) : this("[${clazz.canonicalName}]")
    constructor(clazz: Class<*>, cause: Throwable) : this("[${clazz.canonicalName}]", cause)
    constructor(clazz: Class<*>, message: String) : this("[${clazz.canonicalName}] $message")
    constructor(clazz: Class<*>, message: String, cause: Throwable) : this("[${clazz.canonicalName}] $message", cause)

    constructor() : super(TAG)
    constructor(cause: Throwable) : super(TAG, cause)
    constructor(message: String) : super("$TAG $message")
    constructor(message: String, cause: Throwable) : super("$TAG $message", cause)

    companion object {
        private const val TAG = "[DEBUG_EXCEPTION]"
    }

}