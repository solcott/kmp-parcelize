@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import com.android.build.api.withAndroid
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins { id("org.jetbrains.kotlin.multiplatform") }

kotlin {
  jvm()
  js(IR) {
    outputModuleName = project.name
    browser()
    nodejs()
  }
  @OptIn(ExperimentalWasmDsl::class)
  wasmJs {
    outputModuleName = "${project.name}-wasm"
    browser()
    nodejs()
  }

  macosArm64()
  iosArm64()
  iosSimulatorArm64()

  sourceSets {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
      common {
        group("browserCommon") {
          withJs()
          withWasmJs()
        }
      }
    }

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyDefaultHierarchyTemplate {
      common {
        group("commonJvm") {
          withJvm()
          @Suppress("UnstableApiUsage") withAndroid()
        }
      }
    }

    applyDefaultHierarchyTemplate {
      common {
        group("nonAndroid") {
          withJvm()
          withJs()
          withWasmJs()
          withNative()
        }
      }
    }
  }
}
