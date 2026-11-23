package io.github.solcott.kmp.parcelize

import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/** Writes the common fixtures to a real [android.os.Parcel] and reads them back. */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ParcelRoundTripTest {

  @Test
  fun classLevelTypeParcelerAppliesToEveryUsageOfTheType() {
    val original =
      ClassLevelTypeParceler(
        name = "sunset",
        color = Rgb(255, 128, 0),
        optionalColor = Rgb(10, 20, 30),
        palette = listOf(Rgb(1, 2, 3), Rgb(4, 5, 6)),
      )

    assertEquals(original, original.roundTrip())
  }

  @Test
  fun classLevelTypeParcelerHandlesNull() {
    val original =
      ClassLevelTypeParceler(
        name = "empty",
        color = Rgb(0, 0, 0),
        optionalColor = null,
        palette = emptyList(),
      )

    assertEquals(original, original.roundTrip())
  }

  @Test
  fun propertyLevelTypeParceler() {
    val original = PropertyLevelTypeParceler("accent", Rgb(7, 8, 9))

    assertEquals(original, original.roundTrip())
  }

  @Test
  fun writeWith() {
    val original = WriteWithType(Rgb(100, 150, 200))

    assertEquals(original, original.roundTrip())
  }

  @Test
  fun everyCommonParcelMethodRoundTrips() {
    val original =
      AllTypes(
        string = "hello",
        int = Int.MIN_VALUE,
        long = Long.MAX_VALUE,
        float = 1.5f,
        double = -2.25,
        byte = 0x7f,
        bytes = byteArrayOf(1, 2, 3),
      )

    val restored = AllTypesHolder(original).roundTrip().value

    assertEquals(original.string, restored.string)
    assertEquals(original.int, restored.int)
    assertEquals(original.long, restored.long)
    assertEquals(original.float, restored.float)
    assertEquals(original.double, restored.double)
    assertEquals(original.byte, restored.byte)
    assertContentEquals(original.bytes, restored.bytes)
  }

  @Test
  fun commonParcelMethodsHandleNulls() {
    val restored =
      AllTypesHolder(AllTypes(null, 0, 0L, 0f, 0.0, 0, null)).roundTrip().value

    assertNull(restored.string)
    assertNull(restored.bytes)
  }

  @Test
  fun ignoreOnParcelIsNotWritten() {
    val original = WithIgnoredProperty("name").apply { cached = "changed" }

    val restored = original.roundTrip()

    assertEquals("name", restored.name)
    assertEquals("default", restored.cached)
  }

  private inline fun <reified T : Parcelable> T.roundTrip(): T {
    val parcel = Parcel.obtain()
    try {
      parcel.writeParcelable(this, 0)
      parcel.setDataPosition(0)
      @Suppress("DEPRECATION") return parcel.readParcelable(T::class.java.classLoader)!!
    } finally {
      parcel.recycle()
    }
  }
}
