package com.ltei.ljugraph

import java.io.File
import java.util.*
import kotlin.math.min

/**
 * A class representing a Graph
 */
class Graph<NodeInfo, EdgeInfo> {

    /**
     * The private mutable node list
     */
    private val mNodes = ArrayList<GraphNode<NodeInfo, EdgeInfo>>()

    /**
     * The public immutable node list
     */
    val nodes: List<GraphNode<NodeInfo, EdgeInfo>> get() = mNodes

    /**
     * The private mutable edge list
     */
    private val mEdges = ArrayList<GraphEdge<NodeInfo, EdgeInfo>>()

    /**
     * The public immutable edge list
     */
    val edges: List<GraphEdge<NodeInfo, EdgeInfo>> get() = mEdges

    /**
     * Create a node in the Graph
     * @param info NodeInfo object of the Node
     * @return GraphNode the created node
     */
    fun createNode(id: Int, info: NodeInfo): GraphNode<NodeInfo, EdgeInfo> {
        if (nodes.find { it.id == id } != null)
            throw IllegalArgumentException("A node with this id already exists")

        val node = GraphNode<NodeInfo, EdgeInfo>(id, info)
        mNodes.add(node)
        return node
    }

    /**
     * Create a vertex in the Graph
     * @param node0 initial extremity
     * @param node1 terminal extremity
     * @return GraphEdge the vertex between node0 and node1
     */
    fun createEdge(node0: GraphNode<NodeInfo, EdgeInfo>, node1: GraphNode<NodeInfo, EdgeInfo>, info: EdgeInfo): GraphEdge<NodeInfo, EdgeInfo> {
        val edge = GraphEdge<NodeInfo, EdgeInfo>(node0, node1, info)
        mEdges.add(edge)
        node0.edges.add(edge)
        node1.edges.add(edge)
        return edge
    }

    /**
     * Delete a node in the Graph
     * @param node the node nodeTo be deleted
     */
    fun deleteNode(node: GraphNode<NodeInfo, EdgeInfo>) {
        if (!mNodes.contains(node))
            throw IllegalArgumentException("This node is not in this Graph")

        mNodes.remove(node)
        while (node.edges.isNotEmpty()) {
            deleteEdge(node.edges[0])
        }
    }

    /**
     * Delete a edge in the Graph
     * @param edge the edge nodeTo be deleted
     */
    fun deleteEdge(edge: GraphEdge<NodeInfo, EdgeInfo>) {
        if (!mEdges.contains(edge)) throw IllegalArgumentException("This edge is not in this Graph.")
        if (!edge.nodeFrom.edges.contains(edge)) throw IllegalStateException()
        if (!edge.nodeTo.edges.contains(edge)) throw IllegalStateException()

        mEdges.remove(edge)
        edge.nodeFrom.edges.remove(edge)
        edge.nodeTo.edges.remove(edge)
    }

    /**
     * Normalize the Graph items' ids so that they start at 0 and are not fragmented
     */
    private fun normalizeIds() {
        if (nodes.isNotEmpty()) {
            val minVertexId = nodes.fold(Int.MAX_VALUE) { acc, it -> min(acc, it.id) }
            if (minVertexId != 0) {
                for (v in nodes) {
                    v.id -= minVertexId
                }
            }
        }
    }

    fun toJsonGraph() : JsonGraph<NodeInfo, EdgeInfo>
            = JsonGraph.fromGraph(this)

    fun <NewNodeInfo> mapNodes(block: (NodeInfo) -> NewNodeInfo) : Graph<NewNodeInfo, EdgeInfo>
            = toJsonGraph().mapNodes(block).toGraph()

    fun <NewEdgeInfo> mapEdges(block: (EdgeInfo) -> NewEdgeInfo) : Graph<NodeInfo, NewEdgeInfo>
            = toJsonGraph().mapEdges(block).toGraph()

    /**
     * Save this Graph into a file
     * @param file: The file to save this Graph to
     */
    fun save(file: File) = file.writeText(toJsonGraph().toJson().toString())

    //
    // Static

    companion object {
        /**
         * Load a Graph from a file
         * @param file: The file to read
         * @return Result.ok({newly created Graph}) or
         *         Result.err({cause})
         */
        fun <NodeInfo, EdgeInfo> load(file: File): Graph<NodeInfo, EdgeInfo>? =
            JsonGraph.fromJson<NodeInfo, EdgeInfo>(file.readText()).toGraph()
    }

}