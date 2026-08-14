# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

KMP-Parcelize is a Gradle plugin + runtime library that enables Kotlin's official Parcelize plugin for Kotlin Multiplatform (KMP) projects. On Android, classes annotated with `@Parcelize` get full `Parcelable` serialization. On all other targets (JVM, JS, WASM, Native), the annotations and interface are no-ops/stubs.

- Group: `io.github.solcott`
- Version: `1.0.0`
- Kotlin: 2.3.20 / Java toolchain: 24 / Java compatibility: 17

## Build Commands

```bash
./gradlew build           # Build all targets and run tests
./gradlew assemble        # Build without tests
./gradlew test            # Run tests only
./gradlew ktfmtFormat     # Format all Kotlin/Gradle sources (Google style)
./gradlew ktfmtCheck      # Validate formatting
./gradlew checkSortDependencies  # Validate dependency ordering in build files
./gradlew publishToMavenLocal    # Publish to local Maven repo for manual testing
```

## Architecture

The project has two publishable modules and a sample app:

### `kmp-parcelize-plugin`
A Gradle plugin (`io.github.solcott.kmp-parcelize`) that, when applied to a KMP project:
1. Applies the official `org.jetbrains.kotlin.plugin.parcelize` compiler plugin
2. Adds the `kmp-parcelize-runtime` dependency to `commonMain`
3. For Android JVM targets, injects the compiler argument `-P plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=io.github.solcott.kmp.parcelize.Parcelize` so the Kotlin parcelize plugin recognizes the custom `@Parcelize` annotation

### `kmp-parcelize-runtime`
A KMP library with three source sets:

| Source set | Purpose |
|---|---|
| `commonMain` | Expect declarations for `Parcelable`, `@Parcelize`, `@IgnoreOnParcel` |
| `androidMain` | Typealiases to `android.os.Parcelable` and `kotlinx.parcelize.IgnoredOnParcel` |
| `nonAndroidMain` | Empty interface/annotation stubs for all other targets |

### `build-logic`
Convention plugins consumed by this repo's own modules:
- `kmp-targets.gradle.kts` — Configures all KMP targets (JVM, JS, WASM, iOS/macOS/Linux/Windows native, Android native)
- `kmp-android.gradle.kts` — Android SDK config (compileSdk 36, minSdk 26)
- `kmp-jvm.gradle.kts` — JVM toolchain (Java 24)

### `sample`
Demonstrates usage: `sample/shared` is a KMP module applying the plugin, with `UserData.kt` showing `@Parcelize`-annotated data classes. `sample/androidApp` consumes the shared module.

## Key Conventions

- **Dependency substitution:** `settings.gradle.kts` substitutes `io.github.solcott:kmp-parcelize-runtime` with the local project so local builds use the source, not a published artifact.
- **Code style:** ktfmt (Google style). Run `ktfmtFormat` before committing.
- **Dependency ordering:** Dependencies in build files must be alphabetically sorted (`checkSortDependencies`).
- **Configuration cache** is enabled (`gradle.properties`).
