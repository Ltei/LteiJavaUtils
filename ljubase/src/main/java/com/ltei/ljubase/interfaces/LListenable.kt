package com.ltei.ljubase.interfaces

interface LListenable<This: LListenable<This>> {

    fun addListener(listener: LListener<This>)

    fun addListener(listener: LListener<This>, notify: Boolean = false) {
        addListener(listener)
        @Suppress("UNCHECKED_CAST")
        if (notify) listener.onObservableChanged(this as This)
    }

    fun removeListener(listener: LListener<This>)

    fun notifyListeners()

    abstract class Simple<T: LListenable<T>> : LListenable<T> {

        private val listeners = ArrayList<LListener<T>>()

        override fun addListener(listener: LListener<T>) {
            listeners.add(listener)
        }

        override fun removeListener(listener: LListener<T>) {
            listeners.remove(listener)
        }

        override fun notifyListeners() {
            listeners.forEach {
                @Suppress("UNCHECKED_CAST")
                it.onObservableChanged(this as T)
            }
        }

    }

}