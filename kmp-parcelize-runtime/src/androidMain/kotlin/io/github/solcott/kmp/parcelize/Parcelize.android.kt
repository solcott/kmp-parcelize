package io.github.solcott.kmp.parcelize

/**
 * On Android, this maps directly to [kotlinx.parcelize.Parcelize] so that Android Lint and the IDE
 * recognize annotated classes as Parcelize classes (e.g. no false-positive `ParcelCreator` errors).
 */
actual typealias Parcelize = kotlinx.parcelize.Parcelize
