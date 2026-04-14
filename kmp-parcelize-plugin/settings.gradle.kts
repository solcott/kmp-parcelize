pluginManagement {
  // Add repositories required for build-settings-logic
  repositories { gradlePluginPortal() }

  includeBuild("../build-settings-logic")
}

plugins { id("kmpparcelizesettings") }

rootProject.name = "kmp-parcelize-plugin"

val customPropsFile = file("../gradle.properties")

if (customPropsFile.exists()) {
  val props = java.util.Properties()
  customPropsFile.inputStream().use { props.load(it) }
  props.forEach { (key, value) -> gradle.extra[key.toString()] = value }
} else {
  println("../gradle.properties not found")
}
