package com.ltei.ljuutils.collections.expandablelist

interface ExpandableGroup {
    var isExpanded: Boolean
    val childrenCount: Int
    fun getChild(childPosition: Int): Any

    class Simple<T : Any>(
        val children: List<T>,
        override var isExpanded: Boolean = false
    ) : ExpandableGroup {
        override val childrenCount: Int get() = children.size
        override fun getChild(childPosition: Int): Any = children[childPosition]
    }
}