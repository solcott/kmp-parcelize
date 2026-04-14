plugins {
  `kotlin-dsl`
  id("java-gradle-plugin")
  alias(libs.plugins.publish)
  alias(libs.plugins.plugin.publish)
  alias(libs.plugins.gmazzo.buildconfig)
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.sort.dependencies)
}

group = checkNotNull(project.gradle.extra["group"]) { "group is required" }

version = checkNotNull(project.gradle.extra["version"]) { "version is required" }

repositories {
  google()
  mavenCentral()
  gradlePluginPortal()
}

buildConfig {
  packageName.set("io.github.solcott.kmp.parcelize.plugin")
  buildConfigField("String", "kmpParcelizeVersion", "\"${project.version}\"")
  buildConfigField("String", "kmpParcelizeGroup", "\"${project.group}\"")
}

dependencies {
  compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
}

gradlePlugin {
  website.set("https://github.com/solcott/kmp-parcelize")
  vcsUrl.set("https://github.com/solcott/kmp-parcelize.git")

  plugins {
    create("kmpParcelize") {
      id = "io.github.solcott.kmp.parcelize"
      implementationClass = "io.github.solcott.kmp.parcelize.plugin.KmpParcelizePlugin"
      displayName = "KMP Parcelize Plugin"
      description = "A Gradle plugin to configure Kotlin parcelize for KMP"
      tags.set(listOf("kotlin", "kmp", "multiplatform", "parcelize", "android"))
    }
  }
}

mavenPublishing {
  publishToMavenCentral()

  signAllPublications()

  coordinates(group.toString(), "kmp-parcelize-plugin", version.toString())
  pom {
    name.set("KMP Parcelize Plugin")
    description.set("A Gradle plugin to configure Kotlin parcelize for KMP")
    url.set("https://github.com/solcott/kmp-parcelize")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
    developers {
      developer {
        id.set("solcott")
        name.set("Scott Olcott")
      }
    }
    scm {
      connection.set("scm:git:git://github.com/solcott/kmp-parcelize.git")
      developerConnection.set("scm:git:ssh://github.com/solcott/kmp-parcelize.git")
      url.set("https://github.com/solcott/kmp-parcelize")
    }
  }
}

ktfmt {
  googleStyle()
  removeUnusedImports = true
}
