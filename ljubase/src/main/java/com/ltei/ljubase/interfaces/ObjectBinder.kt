package com.ltei.ljubase.interfaces

interface ObjectBinder<T> {
    val boundObject: T?
    fun bind(obj: T)

    abstract class Simple<T>: ObjectBinder<T> {
        override val boundObject: T? = null
    }
}