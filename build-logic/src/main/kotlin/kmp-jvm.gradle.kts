import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins { id("kotlin-jvm") }

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")
val toolchainVersion = libs.findVersion("jvm-toolchain").get().requiredVersion.toInt()

extensions.configure<KotlinMultiplatformExtension> { jvmToolchain(toolchainVersion) }
