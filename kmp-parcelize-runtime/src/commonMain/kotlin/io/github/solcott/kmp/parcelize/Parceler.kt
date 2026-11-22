package io.github.solcott.kmp.parcelize

/**
 * Common declaration for Android's [kotlinx.parcelize.Parceler]. Implement it to customize how a
 * type is written to and read from a [Parcel], then reference it with [TypeParceler] or
 * [WriteWith]. On Android, this maps to the actual [kotlinx.parcelize.Parceler] interface. On other
 * platforms, it is never called.
 */
expect interface Parceler<T> {
  /** Writes the [T] instance state to the [parcel]. */
  fun T.write(parcel: Parcel, flags: Int)

  /**
   * Reads the [T] instance state from the [parcel], constructs the new [T] instance and returns it.
   */
  fun create(parcel: Parcel): T
}
