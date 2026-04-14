@file:Suppress("UnstableApiUsage")

pluginManagement { repositories { configureRepositories { gradlePluginPortal() } } }

dependencyResolutionManagement {
  repositories { configureRepositories() }

  versionCatalogs {
    create("libs") {
      if (!file("gradle/libs.versions.toml").exists() && file("../gradle/libs.versions.toml").exists()) {
        from(files("../gradle/libs.versions.toml"))
      }
    }
  }
}

private fun RepositoryHandler.configureRepositories(configure: RepositoryHandler.() -> Unit = {}) {
  // Google repository should go first as it has a content filter that handles all Android dependencies
  // before trying to resolve them via other repositories
  google {
    content {
      includeGroupAndSubgroups("androidx")
      includeGroupAndSubgroups("com.google")
      includeGroupAndSubgroups("com.android")
    }
  }
  configure()
  mavenCentral()
  mavenLocal()
}
