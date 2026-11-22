package io.github.solcott.kmp.parcelize

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
@Retention(AnnotationRetention.SOURCE)
@Repeatable
actual annotation class TypeParceler<T, P : Parceler<in T>> actual constructor()
