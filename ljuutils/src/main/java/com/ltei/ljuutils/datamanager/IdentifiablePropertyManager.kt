package com.ltei.ljuutils.datamanager

abstract class IdentifiablePropertyManager<T> : PropertyManager<T>() {
    fun areSameData(a: T?, b: T?): Boolean {
        if (a == null || b == null) return false
        return areSameDataImpl(a, b)
    }

    fun areSameDataOrEmpty(a: T?, b: T?): Boolean {
        if (isEmpty(a) || isEmpty(b)) return true
        return areSameDataImpl(a!!, b!!)
    }

    protected abstract fun areSameDataImpl(a: T, b: T): Boolean

    class FromPropertyManager<T>(
        private val propertyManager: PropertyManager<T>,
        private val areSameData: (a: T, b: T) -> Boolean
    ) : IdentifiablePropertyManager<T>() {
        override fun areSameDataImpl(a: T, b: T): Boolean = areSameData.invoke(a, b)

        override fun isEmptyImpl(data: T): Boolean {
            return propertyManager.isEmpty(data)/*.also {
                logger.debug("isEmptyImpl($data) = $it")
            }*/
        }

        override fun normalizeImpl(data: T): T? {
            return propertyManager.normalize(data)/*.also {
                logger.debug("normalizeImpl($data) = $it")
            }*/
        }

        override fun mergeImpl(previous: T, new: T): T? {
            return propertyManager.merge(previous, new)/*.also {
                logger.debug("mergeImpl($previous, $new) = $it")
            }*/
        }

        /*companion object {
            private val logger = Logger(FromPropertyManager::class.java)
        }*/
    }
}