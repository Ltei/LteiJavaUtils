package com.ltei.ljuutils.datamanager

open class DefaultListPropertyManager<T : Any>(
    val itemManager: IdentifiablePropertyManager<T> = DefaultIdentifiablePropertyManager()
) : PropertyManager<List<T>>() {
    override fun isEmptyImpl(data: List<T>): Boolean {
        if (data.isNullOrEmpty()) return true
        return data.all { itemManager.isEmpty(it) }
    }

    override fun normalizeImpl(data: List<T>): List<T>? {
        if (isEmpty(data)) return null
        val result = mutableListOf<T>()
        for (item in data.mapNotNull { itemManager.normalize(it) }.filter { !itemManager.isEmpty(it) }) {
            val lastItemIndex = result.indexOfFirst { itemManager.areSameData(item, it) }
            if (lastItemIndex >= 0) {
                val lastItem = result.removeAt(lastItemIndex)
                result.add(itemManager.merge(lastItem, item)!!)
            } else {
                result.add(item)
            }
        }
        return if (isEmpty(result)) null else result
    }

    override fun mergeImpl(previous: List<T>, new: List<T>): List<T>? = normalize(previous + new)
}