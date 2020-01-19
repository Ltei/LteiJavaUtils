package com.ltei.ljubase.interfaces

interface IObjectBinder<T> {
    val boundObject: T?
    fun bind(obj: T)
}