@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  id("kmp-jvm")
  id("kmp-android")
  id("kmp-targets")
  alias(libs.plugins.kotlin.parcelize)
  alias(libs.plugins.publish)
}

kotlin {
  android { namespace = "io.github.solcott.kmp.parcelize.runtime" }

  // These targets are not supported by compose multiplatform so define here instead of
  // "kmp-targets"

  watchosArm32()
  watchosArm64()
  watchosSimulatorArm64()
  watchosDeviceArm64()
  tvosArm64()
  tvosSimulatorArm64()

  mingwX64()
  linuxX64()
  linuxArm64()

  androidNativeArm32()
  androidNativeArm64()
  androidNativeX64()
  androidNativeX86()

  sourceSets {
    sourceSets {
      configureEach { compilerOptions { freeCompilerArgs.add("-Xexpect-actual-classes") } }
    }
    commonTest.dependencies {
      implementation(libs.kotlinx.coroutines.test)
      implementation(libs.junit)
    }

    all {
      languageSettings.optIn("kotlin.ExperimentalMultiplatform")
      languageSettings.optIn("kotlin.RequiresOptIn")
    }
  }

  compilerOptions { freeCompilerArgs.add("-Xexpect-actual-classes") }
}

mavenPublishing {
  publishToMavenCentral()

  signAllPublications()

  coordinates(group.toString(), "kmp-parcelize-runtime", version.toString())
  pom {
    name.set("KMP Parcelize Runtime")
    description.set("Runtime annotations and interfaces for KMP Parcelize")
    url.set("https://github.com/solcott/kmp-parcelize")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
    developers {
      developer {
        id.set("solcott")
        name.set("Scott Olcott")
      }
    }
    scm {
      connection.set("scm:git:git://github.com/solcott/kmp-parcelize.git")
      developerConnection.set("scm:git:ssh://github.com/solcott/kmp-parcelize.git")
      url.set("https://github.com/solcott/kmp-parcelize")
    }
  }
}

publishing { publications.withType<MavenPublication>().configureEach {} }
