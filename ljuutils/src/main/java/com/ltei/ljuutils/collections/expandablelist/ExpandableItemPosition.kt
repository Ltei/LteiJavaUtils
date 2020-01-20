package com.ltei.ljuutils.collections.expandablelist

data class ExpandableItemPosition(val group: Int, val child: Int?) {
    override fun toString(): String = "(group=$group, child=$child)"
}