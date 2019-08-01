package com.ltei.ljugraph

import com.google.gson.Gson
import com.ltei.ljugson.fromJson

class JsonGraph<NodeInfo, EdgeInfo> private constructor(
    val nodes: List<Node<NodeInfo>>,
    val edges: List<Edge<EdgeInfo>>
) {

    fun toJson() = gson.toJson(this)

    fun toGraph(): Graph<NodeInfo, EdgeInfo> {
        val graph = Graph<NodeInfo, EdgeInfo>()
        for (node in nodes) {
            graph.createNode(node.id, node.info)
        }
        for (edge in edges) {
            graph.createEdge(
                graph.nodes.find { it.id == edge.from }!!,
                graph.nodes.find { it.id == edge.to }!!,
                edge.info
            )
        }
        return graph
    }

    fun <NewNodeInfo> mapNodes(block: (NodeInfo) -> NewNodeInfo): JsonGraph<NewNodeInfo, EdgeInfo> {
        val newNodes = nodes.map { Node(it.id, block(it.info)) }
        return JsonGraph<NewNodeInfo, EdgeInfo>(newNodes, edges)
    }

    fun <NewEdgeInfo> mapEdges(block: (EdgeInfo) -> NewEdgeInfo): JsonGraph<NodeInfo, NewEdgeInfo> {
        val newEdges = edges.map { Edge(it.from, it.to, block(it.info)) }
        return JsonGraph<NodeInfo, NewEdgeInfo>(nodes, newEdges)
    }

    companion object {
        private val gson = Gson()

        fun <NodeInfo, EdgeInfo> fromJson(json: String) = gson.fromJson<JsonGraph<NodeInfo, EdgeInfo>>(json)

        fun <NodeInfo, EdgeInfo> fromGraph(graph: Graph<NodeInfo, EdgeInfo>): JsonGraph<NodeInfo, EdgeInfo> {
            val nodes = graph.nodes.map { Node(it.id, it.info) }
            val edges = graph.edges.map { Edge(it.nodeFrom.id, it.nodeTo.id, it.info) }
            return JsonGraph(nodes, edges)
        }
    }

    class Node<Info>(val id: Int, val info: Info)
    class Edge<Info>(val from: Int, val to: Int, val info: Info)

}