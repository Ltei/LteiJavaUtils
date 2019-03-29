package com.ltei.ljuutils

interface IModelSaver<Model> {

    val currentVersionId: Int

    fun getVersion(id: Int): Version<Model>
    fun getSavedVersion(): Version<Model>?
    fun getCurrentVersion(): Version<Model> = getVersion(currentVersionId)

    fun save(model: Model) = getCurrentVersion().save(model)
    fun load(): Model? = getSavedVersion()?.load()

    interface Version<Model> {
        fun load(): Model?
        fun save(model: Model)
    }

}

class TestModel

object TestSaver : IModelSaver<TestModel> {

    override val currentVersionId: Int = 1

    private val allVersions = mapOf(
        Pair(1, V1)
    )

    override fun getVersion(id: Int): IModelSaver.Version<TestModel> = allVersions.getValue(id)

    override fun getSavedVersion(): IModelSaver.Version<TestModel>? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    object V1 : IModelSaver.Version<TestModel> {
        override fun load(): TestModel? {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun save(model: TestModel) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

}