package io.github.solcott.kmp.parcelize

/**
 * Common declaration for Android's [kotlinx.parcelize.TypeParceler]. Specifies which [Parceler]
 * (`P`) should be used for a type (`T`). Apply it to a `@Parcelize` class to affect all its
 * properties, or to a single property. On other platforms, it's an empty annotation.
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@Repeatable
expect annotation class TypeParceler<T, P : Parceler<in T>>()
