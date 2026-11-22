package io.github.solcott.kmp.parcelize

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

/**
 * Runs on every target. On non-Android targets the annotations are no-ops, so annotated classes
 * must behave like ordinary classes.
 */
class CommonApiTest {
  @Test
  fun annotatedClassesBehaveLikePlainClasses() {
    val value =
      ClassLevelTypeParceler(
        name = "sunset",
        color = Rgb(255, 128, 0),
        optionalColor = null,
        palette = listOf(Rgb(1, 2, 3)),
      )

    assertIs<Parcelable>(value)
    assertEquals(value, value.copy())
    assertEquals(Rgb(9, 9, 9), WriteWithType(Rgb(9, 9, 9)).color)
    assertEquals(Rgb(4, 5, 6), PropertyLevelTypeParceler("p", Rgb(4, 5, 6)).color)
  }

  @Test
  fun parcelersCanBeReferencedFromCommonCode() {
    val parcelers: List<Parceler<*>> = listOf(RgbParceler, AllTypesParceler)
    assertEquals(2, parcelers.size)
  }
}
