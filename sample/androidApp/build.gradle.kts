plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.kotlin.compose)
  id("android-jvm")
  alias(libs.plugins.sort.dependencies)
}

kotlin {
  dependencies {
    implementation(project(":sample:shared"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.components.uiToolingPreview)
  }
}

android {
  compileOptions {
    val javaCompatVersion = JavaVersion.toVersion(libs.versions.jvm.compat.get())
    targetCompatibility = javaCompatVersion
    sourceCompatibility = javaCompatVersion
  }
  namespace = "io.github.solcott.kmp.parcelize.sample.android"
  compileSdk = libs.versions.androidCompileSdk.get().toInt()

  defaultConfig {
    applicationId = "io.github.solcott.kmp.parcelize.sample"
    minSdk = libs.versions.androidMinSdk.get().toInt()
    targetSdk = libs.versions.androidTargetSdk.get().toInt()
    versionCode = 1
    versionName = version.toString()
  }

  buildFeatures { compose = true }
}
