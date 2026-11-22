package io.github.solcott.kmp.parcelize

/** Parcels only exist on Android. This class can never be instantiated on other platforms. */
actual class Parcel private constructor() {
  actual fun readString(): String? = unsupported()

  actual fun writeString(`val`: String?): Unit = unsupported()

  actual fun readInt(): Int = unsupported()

  actual fun writeInt(`val`: Int): Unit = unsupported()

  actual fun readLong(): Long = unsupported()

  actual fun writeLong(`val`: Long): Unit = unsupported()

  actual fun readFloat(): Float = unsupported()

  actual fun writeFloat(`val`: Float): Unit = unsupported()

  actual fun readDouble(): Double = unsupported()

  actual fun writeDouble(`val`: Double): Unit = unsupported()

  actual fun readByte(): Byte = unsupported()

  actual fun writeByte(`val`: Byte): Unit = unsupported()

  actual fun createByteArray(): ByteArray? = unsupported()

  actual fun writeByteArray(b: ByteArray?): Unit = unsupported()

  actual fun dataPosition(): Int = unsupported()

  private fun unsupported(): Nothing =
    throw UnsupportedOperationException("Parcel is only supported on Android")
}
