package io.github.solcott.kmp.parcelize.sample

import io.github.solcott.kmp.parcelize.Parcelable
import io.github.solcott.kmp.parcelize.Parcelize

@Suppress("ParcelCreator")
@Parcelize
data class UserData(
  val name: String,
  val age: String,
  val category: String,
  val favoriteObject: FavoriteObject?,
) : Parcelable

@Suppress("ParcelCreator")
@Parcelize
data class FavoriteObject(
  val id: String,
  val name: String,
  val description: String,
  val colorStartHex: Long,
  val colorEndHex: Long,
) : Parcelable
