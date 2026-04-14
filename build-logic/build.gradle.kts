plugins {
  `kotlin-dsl`
  alias(libs.plugins.ktfmt)
}

// Should be synced with gradle/gradle-daemon-jvm.properties
kotlin { jvmToolchain(libs.versions.jvm.toolchain.get().toInt()) }

tasks.validatePlugins { enableStricterValidation = true }

dependencies {
  implementation(libs.android.gradle.plugin)
  implementation(libs.kotlin.gradle.plugin)
}

ktfmt {
  googleStyle()
  removeUnusedImports = true
}
