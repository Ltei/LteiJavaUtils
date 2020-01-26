package com.ltei.ljuutils.datamanager

abstract class PropertyManager<T> {
    fun isEmpty(data: T?): Boolean {
        return data?.let { isEmptyImpl(it) } ?: true
    }

    fun normalize(data: T?): T? {
        return if (isEmpty(data)) null else data
    }

    fun merge(previous: T?, new: T?): T? {
        if (isEmpty(previous)) return new
        if (isEmpty(new)) return previous
        return normalize(mergeImpl(previous!!, new!!))
    }

    fun toIdentifiable(areSameData: (a: T, b: T) -> Boolean) =
        IdentifiablePropertyManager.FromPropertyManager(this, areSameData)

    protected abstract fun isEmptyImpl(data: T): Boolean
    protected abstract fun normalizeImpl(data: T): T?
    protected abstract fun mergeImpl(previous: T, new: T): T?

    class FromObjectManager<T>(private val objectManager: ObjectManager<T>) : PropertyManager<T>() {
        override fun isEmptyImpl(data: T): Boolean = objectManager.isEmpty(data)/*.also {
            logger.debug("isEmptyImpl($data) = $it")
        }*/

        override fun normalizeImpl(data: T): T? {
            objectManager.normalize(data)
            return (if (objectManager.isEmpty(data)) null else data)/*.also {
                logger.debug("normalizeImpl($data) = $it")
            }*/
        }

        override fun mergeImpl(previous: T, new: T): T? {
            objectManager.updateWith(previous, new)
            return (if (isEmpty(previous)) null else previous)/*.also {
                logger.debug("mergeImpl($previous, $new) = $it")
            }*/
        }

        /*companion object {
            private val logger = Logger(FromObjectManager::class.java)
        }*/
    }
}