package io.github.solcott.kmp.parcelize

// Fixtures are declared in common code on purpose: that is how library users write them.

/** A plain type that is not [Parcelable], so Parcelize can only write it through a [Parceler]. */
data class Rgb(val r: Int, val g: Int, val b: Int)

object RgbParceler : Parceler<Rgb> {
  override fun create(parcel: Parcel): Rgb =
    Rgb(parcel.readInt(), parcel.readInt(), parcel.readInt())

  override fun Rgb.write(parcel: Parcel, flags: Int) {
    parcel.writeInt(r)
    parcel.writeInt(g)
    parcel.writeInt(b)
  }
}

/** Holds one value of every type the common [Parcel] can read and write. */
class AllTypes(
  val string: String?,
  val int: Int,
  val long: Long,
  val float: Float,
  val double: Double,
  val byte: Byte,
  val bytes: ByteArray?,
)

object AllTypesParceler : Parceler<AllTypes> {
  override fun create(parcel: Parcel): AllTypes =
    AllTypes(
      string = parcel.readString(),
      int = parcel.readInt(),
      long = parcel.readLong(),
      float = parcel.readFloat(),
      double = parcel.readDouble(),
      byte = parcel.readByte(),
      bytes = parcel.createByteArray(),
    )

  override fun AllTypes.write(parcel: Parcel, flags: Int) {
    parcel.writeString(string)
    parcel.writeInt(int)
    parcel.writeLong(long)
    parcel.writeFloat(float)
    parcel.writeDouble(double)
    parcel.writeByte(byte)
    parcel.writeByteArray(bytes)
  }
}

@Parcelize
@TypeParceler<Rgb, RgbParceler>
data class ClassLevelTypeParceler(
  val name: String,
  val color: Rgb,
  val optionalColor: Rgb?,
  val palette: List<Rgb>,
) : Parcelable

@Parcelize
data class PropertyLevelTypeParceler(
  val name: String,
  @TypeParceler<Rgb, RgbParceler> val color: Rgb,
) : Parcelable

@Parcelize data class WriteWithType(val color: @WriteWith<RgbParceler> Rgb) : Parcelable

@Parcelize
@TypeParceler<AllTypes, AllTypesParceler>
data class AllTypesHolder(val value: AllTypes) : Parcelable

@Parcelize
data class WithIgnoredProperty(val name: String) : Parcelable {
  @IgnoreOnParcel var cached: String = "default"
}
