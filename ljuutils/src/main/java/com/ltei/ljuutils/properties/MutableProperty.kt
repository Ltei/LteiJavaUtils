package com.ltei.ljuutils.properties

class MutableProperty<T>(value: T) : Property<T> {

    private val listeners = mutableListOf<(T) -> Unit>()

    override var value: T = value
        set(value) {
            field = value
            for (listener in listeners) {
                listener.invoke(value)
            }
        }

    override fun listen(listener: (T) -> Unit) {
        listeners.add(listener)
    }

}