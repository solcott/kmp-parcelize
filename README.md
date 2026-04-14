# kmp-parcelize

[![Maven Central](https://img.shields.io/maven-central/v/io.github.solcott/kmp-parcelize-runtime)](https://central.sonatype.com/artifact/io.github.solcott/kmp-parcelize-runtime)
[![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/io.github.solcott.kmp.parcelize)](https://plugins.gradle.org/plugin/io.github.solcott.kmp.parcelize)
[![License](https://img.shields.io/github/license/solcott/kmp-parcelize)](LICENSE)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0%2B-blue.svg?logo=kotlin)](https://kotlinlang.org)

Use `@Parcelize` and `Parcelable` in Kotlin Multiplatform **common code**. On Android they produce real `android.os.Parcelable` implementations via the official [Kotlin Parcelize](https://developer.android.com/kotlin/parcelize) compiler plugin — on every other platform the annotations and interfaces are harmless no-ops.

## Features

- **Single annotation in common code** — use `@Parcelize` and `Parcelable` from `commonMain` without any platform-specific wiring.
- **Zero boilerplate** — the Gradle plugin automatically applies `kotlin-parcelize`, adds the runtime dependency, and configures the compiler.
- **Full target support** — Android, JVM, JS, Wasm, iOS, macOS, watchOS, tvOS, Linux, Windows, and Android Native.

## Getting Started

### 1. Apply the Gradle plugin

In your KMP module's `build.gradle.kts`:

```kotlin
plugins {
    id("org.jetbrains.kotlin.multiplatform") version "<kotlin-version>"
    id("io.github.solcott.kmp.parcelize") version "<version>"
}
```

> [!NOTE]
> The plugin is published to both the [Gradle Plugin Portal](https://plugins.gradle.org/plugin/io.github.solcott.kmp.parcelize) and [Maven Central](https://central.sonatype.com/artifact/io.github.solcott/kmp-parcelize-plugin). If you use `pluginManagement` repositories in your `settings.gradle.kts`, make sure either `gradlePluginPortal()` or `mavenCentral()` is included.

That's it — the plugin automatically:
- Applies `org.jetbrains.kotlin.plugin.parcelize`
- Adds [`kmp-parcelize-runtime`](https://central.sonatype.com/artifact/io.github.solcott/kmp-parcelize-runtime) as an `api` dependency to `commonMain`
- Configures the Parcelize compiler plugin to recognize the common `@Parcelize` annotation on Android targets

### 2. Annotate your common classes

```kotlin
import io.github.solcott.kmp.parcelize.Parcelable
import io.github.solcott.kmp.parcelize.Parcelize

@Parcelize
data class UserData(
    val name: String,
    val age: Int,
) : Parcelable
```

On Android this generates a full `android.os.Parcelable` implementation. On all other platforms `Parcelable` is an empty interface and `@Parcelize` is a no-op annotation.

### `@IgnoreOnParcel`

Exclude a property from parceling with `@IgnoreOnParcel`:

```kotlin
import io.github.solcott.kmp.parcelize.IgnoreOnParcel
import io.github.solcott.kmp.parcelize.Parcelable
import io.github.solcott.kmp.parcelize.Parcelize

@Parcelize
data class UserData(
    val name: String,
    @IgnoreOnParcel val cached: String = "",
) : Parcelable
```

On Android this maps to `kotlinx.parcelize.IgnoredOnParcel`. On other platforms it is a no-op.

## How It Works

The project has two artifacts:

| Artifact | Coordinates | Purpose |
|---|---|---|
| **Gradle plugin** | `io.github.solcott.kmp.parcelize` | Wires up the Parcelize compiler plugin and adds the runtime dependency |
| **Runtime library** | `io.github.solcott:kmp-parcelize-runtime` | Provides the `expect`/`actual` declarations used in common code |

The runtime uses Kotlin's `expect`/`actual` mechanism:

- **`commonMain`** declares `expect interface Parcelable`, `@Parcelize`, and `@IgnoreOnParcel`.
- **`androidMain`** maps them to the real Android types via `actual typealias`:
  - `Parcelable` → `android.os.Parcelable`
  - `IgnoreOnParcel` → `kotlinx.parcelize.IgnoredOnParcel`
- **All other platforms** provide empty `actual` implementations (no-ops).

The Gradle plugin also passes `-P plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=io.github.solcott.kmp.parcelize.Parcelize` to the Kotlin compiler on Android targets, so the Parcelize compiler plugin recognizes the common `@Parcelize` annotation.

## Artifacts

### Gradle plugin (recommended)

Applying the Gradle plugin is the easiest way to use kmp-parcelize — it handles all configuration for you.

```kotlin
// build.gradle.kts
plugins {
    id("io.github.solcott.kmp.parcelize") version "<version>"
}
```

### Runtime only

If you prefer to configure the Parcelize compiler plugin yourself, you can depend on the runtime library directly:

```kotlin
// build.gradle.kts
kotlin {
    sourceSets {
        commonMain.dependencies {
            api("io.github.solcott:kmp-parcelize-runtime:<version>")
        }
    }
}
```

You will also need to apply `org.jetbrains.kotlin.plugin.parcelize` and configure the `additionalAnnotation` compiler argument on your Android targets manually.

## Supported Platforms

| Platform | Targets |
|---|---|
| Android | via `com.android.kotlin.multiplatform.library` or `com.android.library` |
| JVM | `jvm` |
| JS | `js(IR)` — browser, Node.js |
| Wasm | `wasmJs` — browser, Node.js |
| Apple | `iosArm64`, `iosSimulatorArm64`, `macosArm64`, `watchosArm32`, `watchosArm64`, `watchosSimulatorArm64`, `watchosDeviceArm64`, `tvosArm64`, `tvosSimulatorArm64` |
| Linux | `linuxX64`, `linuxArm64` |
| Windows | `mingwX64` |
| Android Native | `androidNativeArm32`, `androidNativeArm64`, `androidNativeX64`, `androidNativeX86` |

## Sample

A complete working example lives in the [`sample/`](sample/) directory, showing a shared KMP module consumed by an Android app.

## Requirements

- Kotlin 2.0+
- Gradle 8.0+

## License

```
Copyright 2024 Scott Olcott

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
