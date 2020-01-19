package com.ltei.ljubase

abstract class KillableThread : Thread() {
    protected var isKilled = false
    abstract override fun run()
    fun kill() {
        isKilled = true
    }
}