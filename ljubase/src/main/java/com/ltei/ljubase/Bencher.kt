package com.ltei.ljubase

import kotlin.random.Random

object Bencher {

    class Result(var splitTime: Long = 0, var iterTime: Long = 0) {
        fun add(result: Result) {
            splitTime += result.splitTime
            iterTime += result.iterTime
        }

        override fun toString(): String = "{splitTime=$splitTime, iterTime=$iterTime}"
    }

    private class BlockResult(val blockIndex: Int) {
        var result: Long = 0
    }

    fun multiBench(iterationCount: Int = 10000, blocks: List<Pair<String, () -> Unit>>): List<Long> {
        var t0: Long
        var t1: Long
        var blockResult: BlockResult
        var block: () -> Unit

        val random = Random(2254)
        val blockResults = blocks.withIndex().map { BlockResult(it.index) }

        val indices = blockResults.indices.shuffled(random)
        for (index in indices) {
            blockResult = blockResults[index]
            block = blocks[blockResult.blockIndex].second

            t0 = System.nanoTime()
            for (iter in 0 until iterationCount) {
                block.invoke()
            }
            t1 = System.nanoTime()

            blockResult.result += t1 - t0
        }

        println("Final results = ")
        for (r in blockResults) {
            println("- ${pad(blocks[r.blockIndex].first)}${r.result}")
        }

        return blockResults.map { it.result }
    }

    fun multiBenchSplit(iterationCount: Int = 10000, printEvery: Int = 100, blocks: List<Pair<String, () -> Unit>>): List<Long> {
        var t0: Long
        var t1: Long
        var blockResult: BlockResult
        var block: () -> Unit

        val random = Random(2254)
        val blockResults = blocks.withIndex().map { BlockResult(it.index) }

        for (iter in 0 until iterationCount) {
            val indices = blockResults.indices.shuffled(random)
            for (index in indices) {
                blockResult = blockResults[index]
                block = blocks[blockResult.blockIndex].second

                t0 = System.nanoTime()
                block.invoke()
                t1 = System.nanoTime()

                blockResult.result += t1 - t0

                if (iter % printEvery == 0) {
                    println("Iteration $iter/$iterationCount, Results = ")
                    for (r in blockResults) {
                        println("- ${pad(blocks[r.blockIndex].first)}${r.result}")
                    }
                }
            }
        }

        println("Final results = ")
        for (r in blockResults) {
            println("- ${pad(blocks[r.blockIndex].first)}${r.result}")
        }

        return blockResults.map { it.result }
    }

    private fun pad(tag: String, size: Int = 30): String = tag.padEnd(size, ' ')

}