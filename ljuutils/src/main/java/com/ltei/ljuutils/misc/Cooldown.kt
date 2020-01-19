package com.ltei.ljuutils.misc

class Cooldown(val cooldown: Long) {

    private var lastActionTime: Long = 0

    fun tryActivate(action: () -> Unit) {
        val now = System.currentTimeMillis()
        if (now - lastActionTime > cooldown) {
            action()
            lastActionTime = now
        }
    }

}