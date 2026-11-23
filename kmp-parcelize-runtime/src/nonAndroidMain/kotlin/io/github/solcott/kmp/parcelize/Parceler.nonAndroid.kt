package io.github.solcott.kmp.parcelize

actual interface Parceler<T> {
  actual fun T.write(parcel: Parcel, flags: Int)

  actual fun create(parcel: Parcel): T
}
