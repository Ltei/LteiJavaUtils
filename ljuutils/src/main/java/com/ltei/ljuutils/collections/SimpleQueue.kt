package com.ltei.ljuutils.collections

class SimpleQueue<E> private constructor(val list: ArrayList<E>) : Collection<E> by list {

    constructor(initialCapacity: Int = 1) : this(ArrayList<E>(initialCapacity))

    fun isNotEmpty(): Boolean {
        return list.isNotEmpty()
    }

    fun push(elem: E) {
        list.add(elem)
    }

    fun pop(): E {
        return list.removeAt(0)
    }

    fun tryPop(): E? {
        return if (list.isEmpty()) {
            null
        } else {
            pop()
        }
    }
}