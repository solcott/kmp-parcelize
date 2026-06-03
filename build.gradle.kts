import com.ncorti.ktfmt.gradle.KtfmtExtension
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

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

// The JS yarn.lock records `fsevents` (a macOS-only optional dep of chokidar) when generated
// on macOS, but Linux CI rewrites the lock to drop it. Report the mismatch as a warning instead
// of failing the build so the lock stays portable across macOS dev machines and Linux CI.
plugins.withType<YarnPlugin> {
  with(the<YarnRootExtension>()) { yarnLockMismatchReport = YarnLockMismatchReport.WARNING }
}

tasks.named<UpdateDaemonJvm>("updateDaemonJvm") {
  languageVersion = JavaLanguageVersion.of(libs.versions.jvm.toolchain.get())
  vendor.set(JvmVendorSpec.ADOPTIUM)
}
