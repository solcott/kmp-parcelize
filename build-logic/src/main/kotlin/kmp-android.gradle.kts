import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("com.android.kotlin.multiplatform.library")
}

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")

extensions.configure<KotlinMultiplatformExtension> {
  android {
    compileSdk = libs.findVersion("androidCompileSdk").get().requiredVersion.toInt()
    minSdk = libs.findVersion("androidMinSdk").get().requiredVersion.toInt()
    compilerOptions {
      jvmTarget = JvmTarget.fromTarget(libs.findVersion("jvm-compat").get().requiredVersion)
    }
  }
}
