@file:OptIn(ExperimentalKotlinGradlePluginApi::class, ExperimentalWasmDsl::class)

import com.android.build.api.withAndroid
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
  id("org.jetbrains.kotlin.multiplatform")
  id("project-config")
}

kotlin {
  jvm()
  js {
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
        group("commonJvm") {
          withJvm()
          @Suppress("UnstableApiUsage") withAndroid()
        }
        group("nonAndroid") {
          withJvm()
          withJs()
          withWasmJs()
          withWasmWasi()
          withNative()
        }
      }
    }
  }
}
