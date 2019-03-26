package com.ltei.ljubase.interfaces

interface ObjectBinder<T> {
    fun bind(obj: T)
    fun getBoundObject(): T?
}