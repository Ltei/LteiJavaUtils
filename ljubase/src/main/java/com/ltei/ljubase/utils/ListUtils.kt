package com.ltei.ljubase.utils

object ListUtils {

    fun <T> format(
        list: List<T>,
        separator: String = ",",
        transform: ((T) -> Any?)? = null
    ): String = list.withIndex().fold(StringBuilder()) { acc, (idx, it) ->
        if (transform == null) acc.append(it) else acc.append(transform(it))
        if (idx != list.lastIndex) acc.append(separator)
        acc
    }.toString()

    fun <T> concat(vararg lists: List<T>?): List<T> {
        val result = mutableListOf<T>()
        for (list in lists)
            if (list != null)
                result.addAll(list)
        return result
    }

}

inline fun <T> List<T>.contains(block: (T) -> Boolean) = find(block) != null

fun List<String>?.hasNonBlankString(): Boolean = this?.fold(false) { acc, it -> acc || it.isNotBlank() } ?: false

inline fun <T, U: Any> List<T>.mapCatching(block: (T) -> U) = mapNotNull {
    try {
        block(it)
    } catch (e: Exception) {
        null
    }
}

fun <T> List<T>.toNullIfEmpty() = if (isEmpty()) null else this

inline fun <T, U> List<T>.distinctBy(block: (T) -> U, merge: (T, T) -> T): List<T> {
    val result = mutableListOf<T>()
    for (item in this) {
        val storedItem = result.find { block(it) == block(item) }
        result.add(if (storedItem == null) item else merge(storedItem, item))
    }
    return result
}

fun <T> List<T>?.mergeWith(other: List<T>?): List<T> {
    if (other == null) return this?.toList() ?: listOf()
    if (this == null) return other.toList()
    return (this + other).distinct()
}

inline fun <T, U> List<T>?.mergeWith(other: List<T>?, distinctTransform: (T) -> U): List<T> {
    if (other == null) return this?.toList() ?: listOf()
    if (this == null) return other.toList()
    return (this + other).distinctBy(distinctTransform)
}

inline fun <T, U> List<T>?.mergeWith(other: List<T>?, distinctTransform: (T) -> U, mergeItem: (T, T) -> T): List<T> {
    if (other == null) return this?.toList() ?: listOf()
    if (this == null) return other.toList()
    return (this + other).distinctBy(distinctTransform, mergeItem)
}