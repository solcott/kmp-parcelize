@file:Suppress("UnstableApiUsage")

plugins {
  `kotlin-dsl`
  alias(libs.plugins.ktfmt)
}

kotlin { jvmToolchain(libs.versions.jvm.toolchain.get().toInt()) }

tasks.validatePlugins { enableStricterValidation = true }

ktfmt {
  googleStyle()
  removeUnusedImports = true
}
