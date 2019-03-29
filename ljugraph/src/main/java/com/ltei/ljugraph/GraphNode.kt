package com.ltei.ljugraph

/**
 * A node of a com.ltei.ljugraph
 * @see Graph
 */
class GraphNode<NodeInfo, EdgeInfo> internal constructor(var id: Int, var info: NodeInfo) {

    /**
     * The node's linked edges
     */
    val edges = ArrayList<GraphEdge<NodeInfo, EdgeInfo>>()

    /**
     * Returns whether this is linked with [node] with an edge
     * @param node: The node
     * @returns True if [node] is linked with [this] with an edge
     */
    fun isLinkedWith(node: GraphNode<NodeInfo, EdgeInfo>): Boolean {
        for (edge in edges)
            if (edge.contains(node))
                return true
        return false
    }

    /**
     * Returns the degree of this node in the com.ltei.ljugraph
     * @param orientedGraph: If true, will only count the incident edges
     * @return The degree of this node in the com.ltei.ljugraph
     */
    fun computeDegree(orientedGraph: Boolean = false): Int {
        return if (orientedGraph) {
            edges.fold(0) { acc, it ->
                if (it.nodeTo.id == id) {
                    acc + 1
                } else {
                    acc
                }
            }
        } else {
            edges.size
        }
    }

    override fun toString() = "{id=$id, info=$info}"
}