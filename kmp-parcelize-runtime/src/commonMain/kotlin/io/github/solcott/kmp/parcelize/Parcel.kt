package io.github.solcott.kmp.parcelize

/**
 * Common declaration for Android's [android.os.Parcel]. On Android, this maps to the actual
 * [android.os.Parcel] class. On other platforms, it is a class that can never be instantiated.
 *
 * Only a subset of [android.os.Parcel]'s methods is exposed here, enough to write a [Parceler] in
 * common code. For anything else, write the [Parceler] in `androidMain`.
 */
expect class Parcel {
  fun readString(): String?

  fun writeString(`val`: String?)

  fun readInt(): Int

  fun writeInt(`val`: Int)

  fun readLong(): Long

  fun writeLong(`val`: Long)

  fun readFloat(): Float

  fun writeFloat(`val`: Float)

  fun readDouble(): Double

  fun writeDouble(`val`: Double)

  fun readByte(): Byte

  fun writeByte(`val`: Byte)

  fun createByteArray(): ByteArray?

  fun writeByteArray(b: ByteArray?)

  fun dataPosition(): Int
}
