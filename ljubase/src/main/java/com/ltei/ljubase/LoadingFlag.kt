package com.ltei.ljubase

import com.ltei.ljubase.interfaces.ILoadListener
import java.util.concurrent.atomic.AtomicBoolean

class LoadingFlag : ILoadListener {
    private val mIsLoading = AtomicBoolean(false)
    val isLoading get() = mIsLoading.get()
    override fun onStartLoad() = check(mIsLoading.compareAndSet(false, true))
    override fun onStopLoad() = check(mIsLoading.compareAndSet(true, false))
}