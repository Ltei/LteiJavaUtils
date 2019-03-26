package com.ltei.ljubase

open class LException : IllegalStateException {

    constructor() : super(LLog.TAG)
    constructor(cause: Throwable) : super(LLog.TAG, cause)
    constructor(message: String) : super("${LLog.TAG} $message")
    constructor(message: String, cause: Throwable) : super("${LLog.TAG} $message", cause)

}