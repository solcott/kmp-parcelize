import com.ncorti.ktfmt.gradle.KtfmtExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.android.multiplatform.library) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.parcelize) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.google.devtools.ksp) apply false
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.sort.dependencies)
  alias(libs.plugins.publish) apply false
}

val ktfmtPlugin: String = libs.plugins.ktfmt.get().pluginId
val sortDependenciesPlugin: String = libs.plugins.sort.dependencies.get().pluginId

allprojects {
  apply(plugin = ktfmtPlugin)
  extensions.configure<KtfmtExtension> {
    googleStyle()
    removeUnusedImports = true
  }
}
