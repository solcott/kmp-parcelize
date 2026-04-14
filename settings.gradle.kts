@file:Suppress("UnstableApiUsage")

pluginManagement {
  includeBuild("build-settings-logic")
  includeBuild("kmp-parcelize-plugin")
  includeBuild("build-logic")
}

plugins {
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
  id("kmpparcelizesettings")
}

rootProject.name = "kmp-parcelize"

include(":kmp-parcelize-runtime")

include(":sample:shared")

include(":sample:androidApp")

// Substitute the Maven coordinate used by the plugin with the local project
gradle.beforeProject {
  configurations.configureEach {
    resolutionStrategy.dependencySubstitution {
      substitute(module("io.github.solcott:kmp-parcelize-runtime"))
        .using(project(":kmp-parcelize-runtime"))
    }
  }
}
