package com.ltei.ljubase.interfaces

import java.util.concurrent.atomic.AtomicInteger

interface LoadListener {
    fun onStartLoad()
    fun onStopLoad()

    abstract class AtomicCounter : LoadListener {

        var loadCount = AtomicInteger(0)

        override fun onStartLoad() {
            val count = loadCount.incrementAndGet()
            onLoadCountChanged(count)
        }

        override fun onStopLoad() {
            val count = loadCount.decrementAndGet()
            if (count < 0) throw IllegalStateException()
            onLoadCountChanged(count)
        }

        abstract fun onLoadCountChanged(loadCount: Int)
    }
}