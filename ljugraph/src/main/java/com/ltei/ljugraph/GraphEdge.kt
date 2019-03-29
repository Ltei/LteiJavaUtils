package com.ltei.ljugraph

/**
 * An edge of a Graph
 * @see Graph
 */
class GraphEdge<NodeInfo, EdgeInfo> internal constructor(
        /**
         * The first node linked to this edge
         */
        val nodeFrom: GraphNode<NodeInfo, EdgeInfo>,

        /**
         * The second node linked to this edge
         */
        val nodeTo: GraphNode<NodeInfo, EdgeInfo>,

        /**
         * The edge's attributes
         */
        val info: EdgeInfo
) {

    /**
     * Returns true if the node is one of this edge's extremities
     * @param node: The node to check
     * @return true if the node is one of this edge's extremities
     */
    fun contains(node: GraphNode<NodeInfo, *>) = (node == nodeFrom || node == nodeTo)

    /**
     * Returns the other extremity of this edge
     * @param extremity: One of the extremity of this edge
     * @return The other extremity
     * @throws IllegalStateException if this edge doesn't contains [extremity]
     */
    fun getOther(extremity: GraphNode<NodeInfo, *>) = when (extremity) {
        nodeFrom -> nodeTo
        nodeTo -> nodeFrom
        else -> throw IllegalStateException()
    }

    override fun toString() = "{idFrom=${nodeFrom.id}, idTo=${nodeTo.id}, info=$info}}"
}