package com.ltei.ljuutils.collections.expandablelist

import com.ltei.ljubase.debug.Logger

object ExpandableGroupImpls {
    val logger = Logger(ExpandableGroupImpls::class.java)
}

fun <T : ExpandableGroup> List<T>.getItem(position: ExpandableItemPosition): Any =
    if (position.child == null) {
        this[position.group]
    } else this[position.group].getChild(position.child)

fun <T : ExpandableGroup> List<T>.getItem(flatPosition: Int): Any? {
    val position = getPosition(flatPosition) ?: return null
    return getItem(position)
}

fun <T : ExpandableGroup> List<T>.getPosition(flatPosition: Int): ExpandableItemPosition? {
    var count = 0
    for ((groupPosition, group) in this.withIndex()) {
        if (flatPosition == count) return ExpandableItemPosition(groupPosition, null)
        count++
        if (group.isExpanded) {
            if (flatPosition in count until (count + group.childrenCount))
                return ExpandableItemPosition(groupPosition, flatPosition - count)
            count += group.childrenCount
        }
    }
    ExpandableGroupImpls.logger.debug("Invalid flat position ($flatPosition)")
    return null
}

fun <T : ExpandableGroup> List<T>.getFlatPosition(position: ExpandableItemPosition): Int? {
    fun getGroupFlatPosition(groupPosition: Int): Int {
        var count = 0
        for (groupIdx in 0 until groupPosition) {
            if (this[groupIdx].isExpanded)
                count += this[groupIdx].childrenCount
            count++
        }
        return count
    }

    return if (position.child == null) {
        getGroupFlatPosition(position.group)
    } else {
        if (this[position.group].isExpanded) {
            val groupFlatPosition = getGroupFlatPosition(position.group)
            return groupFlatPosition + position.child + 1
        } else {
            ExpandableGroupImpls.logger.debug("Invalid position ($position)")
            null
        }
    }
}

fun <T : ExpandableGroup> List<T>.getFlatSize(): Int {
    var count = 0
    for (group in this) {
        count++
        if (group.isExpanded) count += group.childrenCount
    }
    return count
}

inline fun <T : ExpandableGroup> List<T>.getFirstMatchingGroupPosition(block: (T) -> Boolean): Int? {
    for ((groupIdx, group) in withIndex()) {
        if (block(group)) return groupIdx
    }
    return null
}

/**
 * Returns the flat position of the first child item matching [block]
 * @param block: The condition to match
 * @return The flat position of the first child item matching [block], or null
 */
inline fun <T : ExpandableGroup> List<T>.getFirstMatchingChildFlatPosition(block: (Any) -> Boolean): Int? {
    for ((groupIdx, group) in withIndex()) {
        for (childIdx in 0 until group.childrenCount) {
            val child = group.getChild(childIdx)
            if (block(child)) {
                return getFlatPosition(ExpandableItemPosition(groupIdx, childIdx))
            }
        }
    }
    return null
}