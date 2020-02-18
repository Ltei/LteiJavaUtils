package com.ltei.ljuutils.datamanager

import com.ltei.ljubase.utils.contains
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaType

class ObjectManagerFactory(
    val propertyManagers: MutableMap<Class<*>, PropertyManager<*>> = mutableMapOf()
) {

    inline fun <reified T : Any> create() = create(T::class)

    fun <T : Any> create(clazz: KClass<T>): ObjectManager<T> {
        val managedFields = getManagedFields(clazz)
        val fieldsInfo: List<FieldInfo<T, Any?>> = managedFields.map { field ->
            createFieldInfo(clazz, field) as FieldInfo<T, Any?>
        }

        // Create manager
        return object : ObjectManager<T> {
            override fun isEmpty(obj: T): Boolean {
                return fieldsInfo.all {
                    val manager = it.getPropertyManager(obj)
                    val fieldValue = it.property.get(obj)
                    manager.isEmpty(fieldValue)
                }
            }

            override fun clear(obj: T) {
                for (it in fieldsInfo) {
                    it.property.set(obj, null)
                }
            }

            override fun normalize(obj: T) {
                for (it in fieldsInfo) {
                    val manager = it.getPropertyManager(obj)
                    val fieldValue = it.property.get(obj)
                    val normalizedField = manager.normalize(fieldValue)
                    it.property.set(obj, normalizedField)
                }
            }

            override fun updateWith(objToUpdate: T, newObj: T?) {
                if (newObj == null) return
                for (it in fieldsInfo) {
                    val previousField = it.property.get(objToUpdate)
                    val newField = it.property.get(newObj)
                    val manager = it.getPropertyManager(objToUpdate)
                    val updatedField = manager.merge(previousField, newField)
                    it.property.set(objToUpdate, updatedField)
                }
            }
        }
    }

    // Private misc

    private fun <T : Any> getManagedFields(clazz: KClass<T>): List<KMutableProperty1<T, *>> {
        return clazz.memberProperties.filter { field ->
            val fieldJavaClass = field.javaField!!.type
            when {
                field.annotations.contains { it is NotManaged } -> false
                PropertyManager::class.java.isAssignableFrom(fieldJavaClass)
                        || ObjectManager::class.java.isAssignableFrom(fieldJavaClass) ->
                    field.annotations.contains { it is ForceManaged }
                else -> true
            }
        }.map {
            if (it !is KMutableProperty1<T, *>) throw IllegalArgumentException("Field ${it.name} isn't mutable")
            if (!it.returnType.isMarkedNullable) throw IllegalArgumentException("Field ${it.name} isn't nullable")
            it as KMutableProperty1<T, *>
        }
    }

    private fun <R, T> createFieldInfo(clazz: KClass<*>, field: KMutableProperty1<R, T>): FieldInfo<R, T> {
        val javaField = field.javaField!!

        var propertyManagerProvider: PropertyManagerProvider? = null

        fun assertManagerNull() {
            if (propertyManagerProvider != null)
                throw IllegalArgumentException("Cannot use multiple @ManagedBy annotations on same property")
        }

        field.findAnnotation<ManagedByClass>()?.let { annotation ->
            assertManagerNull()
            val managerConstructor = annotation.managerClass.primaryConstructor
                ?: throw IllegalArgumentException("Field ${field.name} manager call (${annotation.managerClass.simpleName}) doesn't have primary constructor")
            val manager = managerConstructor.call() as PropertyManager<Any>
            propertyManagerProvider = PropertyManagerProvider.Simple(manager)
        }

        field.findAnnotation<ManagedByProperty>()?.let { annotation ->
            assertManagerNull()
            val property = clazz.memberProperties.find { it.name == annotation.propertyName }
            propertyManagerProvider = PropertyManagerProvider.FromKProperty(property!!)
        }

        field.findAnnotation<ManagedByCompanionProperty>()?.let { annotation ->
            assertManagerNull()
            val property = clazz.companionObject!!.memberProperties.find { it.name == annotation.propertyName }
            propertyManagerProvider = PropertyManagerProvider.FromCompanionKProperty(property!!)
        }

        if (propertyManagerProvider == null) {
            val manager = inferPropertyManager(javaField.type, field.returnType) as PropertyManager<Any>
            propertyManagerProvider = PropertyManagerProvider.Simple(manager)
        }

        return FieldInfo(
            property = field,
            field = javaField,
            propertyManagerProvider = propertyManagerProvider!!
        )
    }

    private fun <T> inferPropertyManager(clazz: Class<T>, type: KType): PropertyManager<T> {
        val mapped = propertyManagers[clazz]
        if (mapped != null) return mapped as PropertyManager<T>
        return createDefaultPropertyManager(clazz, type)
    }

    private fun <T> createDefaultPropertyManager(clazz: Class<T>, type: KType): PropertyManager<T> {
        return when {
            List::class.java.isAssignableFrom(clazz) -> {
                val typeParam = type.arguments.first().type!!
                val typePropertyManager =
                    inferPropertyManager(typeParam.javaType as Class<*>, typeParam)
                DefaultListPropertyManager(typePropertyManager) as PropertyManager<T>
            }
            else -> DefaultPropertyManager()
        }
    }

    private data class FieldInfo<R, T>(
        val property: KMutableProperty1<R, T>,
        val field: Field,
        private val propertyManagerProvider: PropertyManagerProvider
    ) {
        private var propertyManager: PropertyManager<Any>? = null
        fun getPropertyManager(obj: Any): PropertyManager<Any> {
            if (propertyManager == null) {
                propertyManager = propertyManagerProvider.getManager(obj)
            }
            return propertyManager!!
        }
    }

    private interface PropertyManagerProvider {
        fun getManager(obj: Any): PropertyManager<Any>

        data class Simple(val manager: PropertyManager<Any>) : PropertyManagerProvider {
            override fun getManager(obj: Any): PropertyManager<Any> = manager
        }

        data class FromKProperty<T>(val property: KProperty1<T, *>) : PropertyManagerProvider {
            override fun getManager(obj: Any): PropertyManager<Any> {
                return property.get(obj as T) as PropertyManager<Any>
            }
        }

        data class FromCompanionKProperty<T>(val property: KProperty1<T, *>) : PropertyManagerProvider {
            override fun getManager(obj: Any): PropertyManager<Any> {
                val companionObjectInstance = obj.javaClass.kotlin.companionObjectInstance!!
                return property.get(companionObjectInstance as T) as PropertyManager<Any>
            }
        }
    }

}