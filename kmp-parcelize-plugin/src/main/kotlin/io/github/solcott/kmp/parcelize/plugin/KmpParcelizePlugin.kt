package io.github.solcott.kmp.parcelize.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.kotlin.dsl.project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

class KmpParcelizePlugin : Plugin<Project> {
  override fun apply(project: Project) {
    // Apply the Kotlin Parcelize plugin
    project.plugins.apply("org.jetbrains.kotlin.plugin.parcelize")

    // Wait until the multiplatform plugin is applied (if it isn't already)
    project.plugins.withId("org.jetbrains.kotlin.multiplatform") {
      val kotlin = project.extensions.findByType<KotlinMultiplatformExtension>()
      if (kotlin != null) {
        // Add the runtime to commonMain dependencies
        val sourceSets = kotlin.sourceSets
        sourceSets.getByName("commonMain").dependencies {
          // Try to find the runtime as a project in the same build
          val runtimeProject =
            project.rootProject.allprojects.find { it.name == "kmp-parcelize-runtime" }
          if (runtimeProject != null) {
            api(project.dependencies.project(runtimeProject.path))
          } else {
            api(
              "${BuildConfig.kmpParcelizeGroup}:kmp-parcelize-runtime:${BuildConfig.kmpParcelizeVersion}"
            )
          }
        }

        kotlin.targets
          .matching { it.platformType == KotlinPlatformType.androidJvm }
          .configureEach {
            compilations.configureEach {
              compileTaskProvider.configure {
                compilerOptions {
                  freeCompilerArgs.addAll(
                    "-P",
                    "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=io.github.solcott.kmp.parcelize.Parcelize",
                  )
                }
              }
            }
          }
      }
    }
  }
}
