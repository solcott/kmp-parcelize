package io.github.solcott.kmp.parcelize

/**
 * Common expect declaration for Android's [android.os.Parcelable]. On Android, this will map to the
 * actual [android.os.Parcelable] interface. On other platforms, it maps to an empty interface.
 */
expect interface Parcelable
