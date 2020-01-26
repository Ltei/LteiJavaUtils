package com.ltei.ljuutils.datamanager

interface ObjectManager<T> {
    fun isEmpty(obj: T): Boolean
    fun clear(obj: T)
    fun normalize(obj: T)
    fun updateWith(objToUpdate: T, newObj: T?)

    fun toPropertyManager() = PropertyManager.FromObjectManager(this)
}