pluginManagement {
  // Add repositories required for build-settings-logic
  repositories { gradlePluginPortal() }

  includeBuild("../build-settings-logic")
}

plugins { id("kmpparcelizesettings") }

dependencyResolutionManagement {
  // Additional repositories for build-logic
  @Suppress("UnstableApiUsage") repositories { gradlePluginPortal() }
}

rootProject.name = "build-logic"
