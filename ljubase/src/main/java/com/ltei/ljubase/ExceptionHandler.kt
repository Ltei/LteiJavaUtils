package com.ltei.ljubase

object ExceptionHandler {

    private val additionalHandlers = mutableListOf<(Throwable) -> Unit>()

    fun addAdditionalHandler(handler: (Throwable) -> Unit) = additionalHandlers.add(handler)
    fun removeAdditionalHandler(handler: (Throwable) -> Unit) = additionalHandlers.remove(handler)

    fun handle(t: Throwable) {
        LLog.debug(javaClass, "Handling throwable $t")
        t.printStackTrace()
        for (handler in additionalHandlers) {
            handler.invoke(t)
        }
    }

    inline fun <V> accept(result: Result<V, Throwable>, block: (V) -> Unit) {
        result.runOnOk(block).runOnErr(::handle)
    }

}