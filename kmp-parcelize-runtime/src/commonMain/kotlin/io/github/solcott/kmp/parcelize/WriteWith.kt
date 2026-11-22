package io.github.solcott.kmp.parcelize

/**
 * Common declaration for Android's [kotlinx.parcelize.WriteWith]. Specifies which [Parceler] (`P`)
 * should be used for the annotated type, e.g. `val url: @WriteWith<UrlParceler> Url`. On other
 * platforms, it's an empty annotation.
 */
@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
expect annotation class WriteWith<P : Parceler<*>>()
