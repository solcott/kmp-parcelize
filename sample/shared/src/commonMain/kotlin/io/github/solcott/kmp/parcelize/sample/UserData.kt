package io.github.solcott.kmp.parcelize.sample

import io.github.solcott.kmp.parcelize.Parcel
import io.github.solcott.kmp.parcelize.Parcelable
import io.github.solcott.kmp.parcelize.Parceler
import io.github.solcott.kmp.parcelize.Parcelize
import io.github.solcott.kmp.parcelize.TypeParceler
import io.github.solcott.kmp.parcelize.WriteWith

@Parcelize
data class UserData(
  val name: String,
  val age: String,
  val category: String,
  val favoriteObject: FavoriteObject?,
) : Parcelable

@Parcelize
@TypeParceler<Rgb, RgbParceler>
data class FavoriteObject(
  val id: String,
  val name: String,
  val description: String,
  val colorStartHex: Long,
  val colorEndHex: Long,
  val accent: Rgb = Rgb(0, 0, 0),
  val highlight: @WriteWith<RgbParceler> Rgb = Rgb(255, 255, 255),
) : Parcelable

/** A plain common type that is not [Parcelable]; [RgbParceler] tells Parcelize how to write it. */
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
