package com.ltei.ljubase.interfaces

interface LListener<T: LListenable<T>> {
    fun onObservableChanged(observable: T)
}