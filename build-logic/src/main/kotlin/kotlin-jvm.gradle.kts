import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

val libs: VersionCatalog = the<VersionCatalogsExtension>().named("libs")
val compatVersion: String = libs.findVersion("jvm-compat").get().requiredVersion

tasks.withType<KotlinJvmCompile>().configureEach {
  compilerOptions {
    jvmTarget.set(JvmTarget.fromTarget(compatVersion))

    // Highly recommended: Prevents you from accidentally using standard
    // library APIs from Java 18+ that don't exist in Java 17.
    freeCompilerArgs.add("-Xjdk-release=$compatVersion")
  }
}

// For pure Java source files in the module
tasks.withType<JavaCompile>().configureEach {
  // 'release' is the modern replacement for sourceCompatibility/targetCompatibility.
  // It guarantees both bytecode version AND standard library API bounds.
//  options.release.set(compatVersion.toInt())
  sourceCompatibility = compatVersion
  targetCompatibility = compatVersion
}
