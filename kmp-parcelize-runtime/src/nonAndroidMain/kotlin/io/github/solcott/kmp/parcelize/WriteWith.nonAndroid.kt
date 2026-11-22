package io.github.solcott.kmp.parcelize

@Target(AnnotationTarget.TYPE)
@Retention(AnnotationRetention.SOURCE)
actual annotation class WriteWith<P : Parceler<*>> actual constructor()
