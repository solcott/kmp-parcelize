package io.github.solcott.kmp.parcelize

/**
 * Common declaration for Android's [@kotlinx.parcelize.Parcelize] annotation. When applied on a
 * class, it generates Parcelable implementation on Android. On other platforms, it's an empty
 * annotation.
 */
@Target(AnnotationTarget.CLASS) @Retention(AnnotationRetention.BINARY) annotation class Parcelize()
