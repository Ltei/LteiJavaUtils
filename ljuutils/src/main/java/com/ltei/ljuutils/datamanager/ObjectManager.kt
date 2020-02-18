package com.ltei.ljuutils.datamanager

interface ObjectManager<T> {
    fun isEmpty(obj: T): Boolean
    fun clear(obj: T)
    fun normalize(obj: T)
    fun updateWith(objToUpdate: T, newObj: T?)

    fun toPropertyManager(
        areSameData: (a: T, b: T) -> Boolean
    ): PropertyManager<T> = PropertyManagerImpl(this, areSameData)

    private class PropertyManagerImpl<T>(
        private val objectManager: ObjectManager<T>,
        private val areSameData: (a: T, b: T) -> Boolean
    ) : PropertyManager<T>() {
        override fun isEmptyImpl(data: T): Boolean = objectManager.isEmpty(data)

        override fun normalizeImpl(data: T): T? {
            objectManager.normalize(data)
            return (if (objectManager.isEmpty(data)) null else data)
        }

        override fun mergeImpl(previous: T, new: T): T? {
            objectManager.updateWith(previous, new)
            return (if (isEmpty(previous)) null else previous)
        }

        override fun areSameDataImpl(a: T, b: T): Boolean = areSameData.invoke(a, b)
    }
}