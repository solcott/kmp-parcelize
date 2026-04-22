import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("com.android.kotlin.multiplatform.library")
}

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")

kotlin {
  android {
    compileSdk = libs.findVersion("androidCompileSdk").get().requiredVersion.toInt()
    minSdk = libs.findVersion("androidMinSdk").get().requiredVersion.toInt()
    compilerOptions {
      jvmTarget = JvmTarget.fromTarget(libs.findVersion("jvm-compat").get().requiredVersion)
    }
  }
}
