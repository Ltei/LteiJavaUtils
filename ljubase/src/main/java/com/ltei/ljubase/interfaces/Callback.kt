package com.ltei.ljubase.interfaces

interface Callback<V, E> {
    fun onSuccess(value: V)
    fun onError(err: E)
}