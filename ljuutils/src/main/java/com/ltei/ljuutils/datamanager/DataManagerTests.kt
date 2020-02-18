package com.ltei.ljuutils.datamanager

object DataManagerTests {

    class TestClass(
        @ManagedByClass(DefaultPropertyManager::class)
        var string: String? = null,

        @ManagedByClass(StringListManager::class)
        var stringList: List<String>? = null,

        @ManagedByProperty("innerClassListManager")
        var innerList: List<TestInnerClass>? = null,

        @ManagedByProperty("innerClassManager")
        var inner: TestInnerClass? = null
    ) {
        class StringListManager : DefaultListPropertyManager<String>(DefaultPropertyManager())

        @NotManaged
        val innerClassManager = ObjectManagerFactory().create<TestInnerClass>().toPropertyManager { a, b ->
            a.string == b.string
        }

        @NotManaged
        val innerClassListManager = DefaultListPropertyManager(innerClassManager)

    }

    class TestInnerClass(
        @ManagedByProperty("stringManager")
        var string: String? = null
    ) {
        @NotManaged
        val stringManager = object : DefaultPropertyManager<String>() {
            override fun isEmptyImpl(data: String): Boolean = data.isBlank()
        }
    }

}

fun main() {
    val objectManager = ObjectManagerFactory().create<DataManagerTests.TestClass>()

    val obj1 = DataManagerTests.TestClass()
    val obj2 = DataManagerTests.TestClass()

    run {
        // Test clear and isEmpty
        obj1.inner = DataManagerTests.TestInnerClass(string = "")
        if (!objectManager.isEmpty(obj1)) throw IllegalStateException()
        obj1.inner!!.string = "a"
        if (objectManager.isEmpty(obj1)) throw IllegalStateException()
        objectManager.clear(obj1)
        if (obj1.inner != null) throw IllegalStateException()
        if (!objectManager.isEmpty(obj1)) throw IllegalStateException()
    }

    run {
        // Test normalize
        obj1.inner = DataManagerTests.TestInnerClass(string = "")
        objectManager.normalize(obj1)
        if (obj1.inner != null) throw IllegalStateException()
    }

    run {
        // Test update
        objectManager.clear(obj1)
        objectManager.clear(obj2)
        obj1.stringList = listOf("salut!!", "ça va?")
        obj2.stringList = listOf("salut", "ça va?")
        objectManager.updateWith(obj1, obj2)
        if (!obj1.stringList!!.let { it[0] == "salut!!" && it[1] == "ça va?" && it[2] == "salut" })
            throw IllegalStateException()
    }

}