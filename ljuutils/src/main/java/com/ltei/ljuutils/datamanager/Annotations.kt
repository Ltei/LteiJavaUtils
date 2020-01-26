package com.ltei.ljuutils.datamanager

import kotlin.reflect.KClass

@Target(AnnotationTarget.PROPERTY)
annotation class ForceManaged

@Target(AnnotationTarget.PROPERTY)
annotation class NotManaged

@Target(AnnotationTarget.PROPERTY)
annotation class ManagedByClass(val managerClass: KClass<*>)

@Target(AnnotationTarget.PROPERTY)
annotation class ManagedByProperty(val propertyName: String)

@Target(AnnotationTarget.PROPERTY)
annotation class ManagedByCompanionProperty(val propertyName: String)