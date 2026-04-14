@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  id("kmp-jvm")
  id("kmp-android")
  id("kmp-targets")
  alias(libs.plugins.compose.multiplatform)
  alias(libs.plugins.kotlin.compose)
  id("io.github.solcott.kmp.parcelize")
  alias(libs.plugins.sort.dependencies)
}

kotlin {
  android { namespace = "io.github.solcott.kmp.parcelize.sample.shared" }

  sourceSets {
    commonMain.dependencies {
      implementation(libs.compose.runtime)
      implementation(libs.compose.foundation)
      implementation(libs.compose.material3)
      implementation(libs.compose.ui)
      implementation(libs.compose.components.resources)
      implementation(libs.compose.components.uiToolingPreview)
      implementation(libs.compose.materialIconsExtended)
    }
  }
}
