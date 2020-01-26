package com.ltei.ljuutils.datamanager

import com.ltei.ljubase.debug.Logger
import com.ltei.ljubase.utils.contains
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.KType
import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.javaType

class ObjectManagerFactory {

//    val propertyManagers = mutableMapOf<Class<*>, PropertyManager<*>>()

    inline fun <reified T : Any> create() = create(T::class)

    fun <T : Any> create(clazz: KClass<T>): ObjectManager<T> {
        val managedFields = getManagedFields(clazz)
        val fieldsInfo = managedFields.map { field -> createFieldInfo(clazz, field) }

        // Create manager
        return object : ObjectManager<T> {
            override fun isEmpty(obj: T): Boolean {
                return fieldsInfo.all {
                    val manager = it.propertyManagerProvider.getManager(obj)
                    propertyManagerMethods.isEmpty.invoke(manager, it.property.get(obj)) as Boolean
                }
            }

            override fun clear(obj: T) {
                for (it in fieldsInfo) {
                    kMutableProperty1Methods.set.invoke(it.property, obj, null)
                }
            }

            override fun normalize(obj: T) {
                for (it in fieldsInfo) {
                    val manager = it.propertyManagerProvider.getManager(obj)
                    val fieldValue = it.property.get(obj)
                    val normalizedField = propertyManagerMethods.normalize.invoke(manager, fieldValue)
                    kMutableProperty1Methods.set.invoke(it.property, obj, normalizedField)
                }
            }

            override fun updateWith(objToUpdate: T, newObj: T?) {
                if (newObj == null) return
                for (it in fieldsInfo) {
                    val previousField = it.property.get(objToUpdate)
                    val newField = it.property.get(newObj)
                    val manager = it.propertyManagerProvider.getManager(objToUpdate)
                    val updatedField = propertyManagerMethods.merge.invoke(manager, previousField, newField)
                    kMutableProperty1Methods.set.invoke(it.property, objToUpdate, updatedField)
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
            val manager = managerConstructor.call()
            propertyManagerProvider = PropertyManagerProvider.Simple(manager as PropertyManager<*>)
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
            val manager = inferPropertyManager(javaField.type, field.returnType)
            propertyManagerProvider = PropertyManagerProvider.Simple(manager as PropertyManager<*>)
        }

        return FieldInfo(
            property = field,
            field = javaField,
            propertyManagerProvider = propertyManagerProvider!!
        )
    }

    private fun <T> inferPropertyManager(clazz: Class<T>, type: KType): PropertyManager<T> {
//        val mapped = propertyManagers[clazz] // TODO Infer from ObjectManagerFactory data
//        if (mapped != null) return mapped as PropertyManager<T>
        return createDefaultPropertyManager(clazz, type)
    }

    private fun <T> createDefaultPropertyManager(clazz: Class<T>, type: KType): PropertyManager<T> {
        return when {
            List::class.java.isAssignableFrom(clazz) -> {
                val typeParam = type.arguments.first().type!!
                val typePropertyManager =
                    inferPropertyManager(typeParam.javaType as Class<*>, typeParam) as IdentifiablePropertyManager<*>
                defaultListPropertyManagerMethods.constructor.newInstance(typePropertyManager) as PropertyManager<T>
            }
            type == String::class -> DefaultIdentifiablePropertyManager()
//            else -> create(clazz)
            else -> DefaultIdentifiablePropertyManager()
        }
    }

    // Static

    companion object {
        val logger = Logger(ObjectManagerFactory::class.java)

        private val objectClass = Object::class.java

        private val propertyManagerMethods = PropertyManagerMethods()
        private val defaultListPropertyManagerMethods = DefaultListPropertyManagerMethods()
        private val kMutableProperty1Methods = KMutableProperty1Methods()
    }

    private data class FieldInfo<R, T>(
        val property: KMutableProperty1<R, T>,
        val field: Field,
        val propertyManagerProvider: PropertyManagerProvider
    )

    private class PropertyManagerMethods {
        val isEmpty = PropertyManager::class.java.getMethod("isEmpty", objectClass)!!
        val normalize = PropertyManager::class.java.getMethod("normalize", objectClass)!!
        val merge = PropertyManager::class.java.getMethod("merge", objectClass, objectClass)!!
    }

    private class DefaultListPropertyManagerMethods {
        val constructor = DefaultListPropertyManager::class.java.constructors.first()!!
    }

    private class KMutableProperty1Methods {
        val set = KMutableProperty1::class.java.getMethod("set", objectClass, objectClass)!!
    }

    private interface PropertyManagerProvider {
        fun getManager(receiver: Any): PropertyManager<*>

        data class Simple(val manager: PropertyManager<*>) : PropertyManagerProvider {
            override fun getManager(receiver: Any): PropertyManager<*> = manager
        }

        data class FromKProperty<T>(val property: KProperty1<T, *>) : PropertyManagerProvider {
            override fun getManager(receiver: Any): PropertyManager<*> {
                return property.get(receiver as T) as PropertyManager<*>
            }
        }

        data class FromCompanionKProperty<T>(val property: KProperty1<T, *>) : PropertyManagerProvider {
            override fun getManager(receiver: Any): PropertyManager<*> {
                val receiverKClass = receiver.javaClass.kotlin
                val companionObjectInstance = receiverKClass.companionObjectInstance!!
                return property.get(companionObjectInstance as T) as PropertyManager<*>
            }
        }
    }

}