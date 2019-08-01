package com.ltei.ljubase

import com.ltei.ljubase.interfaces.ILoadListener
import java.util.concurrent.atomic.AtomicBoolean

class LoadingFlag : ILoadListener {

    private val mIsLoading = AtomicBoolean(false)
    val isLoading get() = mIsLoading.get()

    override fun onStartLoad() {
        if (!mIsLoading.compareAndSet(false, true))
            throw IllegalStateException()
    }

    override fun onStopLoad() {
        if (!mIsLoading.compareAndSet(true, false))
            throw IllegalStateException()
    }
}