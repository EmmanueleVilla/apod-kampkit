import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
    id("co.touchlab.native.cocoapods")
    id("kotlinx-serialization")
    id("com.android.library")
}

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(Versions.min_sdk)
        targetSdkVersion(Versions.target_sdk)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

kotlin {
    android()
    jvm()
    js {
        browser {
            useCommonJs()
        }
    }
    // Revert to just ios() when gradle plugin can properly resolve it
    val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
    if (onPhone) {
        iosArm64("ios")
    } else {
        iosX64("ios")
    }

    targets.getByName<KotlinNativeTarget>("ios").compilations["main"].kotlinOptions.freeCompilerArgs +=
        listOf("-Xobjc-generics", "-Xg0")

    version = "1.1"

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
            }
        }
    }

    sourceSets["commonMain"].dependencies {
        implementation(kotlin("stdlib-common", Versions.kotlin))
        implementation(Deps.Ktor.commonCore)
        implementation(Deps.Ktor.commonJson)
        implementation(Deps.Ktor.commonLogging)
        implementation(Deps.Coroutines.common)
        implementation(Deps.stately)
        implementation(Deps.multiplatformSettings)
        implementation(Deps.koinCore)
        implementation(Deps.Ktor.commonSerialization)
        implementation(Deps.Redux.core)
        api(Deps.kermit)
    }

    sourceSets["commonTest"].dependencies {
        implementation(Deps.multiplatformSettingsTest)
        implementation(Deps.KotlinTest.common)
        implementation(Deps.KotlinTest.annotations)
        implementation(Deps.koinTest)
        // Karmok is an experimental library which helps with mocking interfaces
        implementation(Deps.karmok)
        implementation(Deps.Ktor.mock)
    }

    sourceSets["androidMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.Ktor.jvmCore)
        implementation(Deps.Ktor.jvmJson)
        implementation(Deps.Ktor.jvmLogging)
        implementation(Deps.Coroutines.jdk)
        implementation(Deps.Coroutines.android)
        implementation(Deps.Ktor.androidSerialization)
        implementation(Deps.Ktor.androidCore)
    }

    sourceSets["jvmMain"].dependencies {
        implementation(kotlin("stdlib", Versions.kotlin))
        implementation(Deps.kermitJvm)
        implementation(Deps.Ktor.jvmCore)
        implementation(Deps.Ktor.jvmJson)
        implementation(Deps.Ktor.jvmLogging)
        implementation(Deps.Coroutines.jdk)
        implementation(Deps.Ktor.androidSerialization)
        implementation(Deps.Ktor.androidCore)
    }

    sourceSets["androidTest"].dependencies {
        implementation(Deps.KotlinTest.jvm)
        implementation(Deps.KotlinTest.junit)
        implementation(Deps.AndroidXTest.core)
        implementation(Deps.AndroidXTest.junit)
        implementation(Deps.AndroidXTest.runner)
        implementation(Deps.AndroidXTest.rules)
        implementation(Deps.Coroutines.test)
        implementation(Deps.Ktor.jvmMock)
        implementation(Deps.RoboEletric.droid)
    }

    sourceSets["iosMain"].dependencies {
        implementation(Deps.Ktor.ios)
        implementation(Deps.Ktor.iosCore)
        implementation(Deps.Ktor.iosJson)
        implementation(Deps.Ktor.iosLogging)
        implementation(Deps.Coroutines.native) {
            version {
                strictly("1.3.5-native-mt")
            }
        }
        implementation(Deps.Ktor.iosSerialization)
        implementation(Deps.koinCore)
    }

    sourceSets["jsMain"].dependencies {
        implementation(Deps.Ktor.js)
        implementation(Deps.Ktor.jsCore)
        implementation(Deps.Ktor.jsJson)
        implementation(Deps.Ktor.jsLogging)
        implementation(Deps.Ktor.jsSerialization)
        implementation(Deps.Coroutines.js)
        implementation(Deps.koinCoreJS)
        api(npm("text-encoding"))
        api(npm("bufferutil"))
        api(npm("utf-8-validate"))
        api(npm("abort-controller"))
        api(npm("fs"))
        implementation(npm("styled-components"))
        implementation(npm("inline-style-prefixer"))
        implementation(npm("react-router-dom", "5.1.2"))
        implementation(kotlin("stdlib-js"))
        implementation(npm("react", "16.13.0"))
        implementation(npm("react-is", "16.13.0"))
        implementation(npm("react-dom", "16.13.0"))

        // version 94 is the last one that works (108 and 109 are bugged)
        implementation("org.jetbrains:kotlin-react:16.13.0-pre.94-kotlin-1.3.70")
        implementation("org.jetbrains:kotlin-react-dom:16.13.0-pre.94-kotlin-1.3.70")
        implementation("org.jetbrains:kotlin-styled:1.0.0-pre.94-kotlin-1.3.70")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.5")
        // implementation("org.jetbrains:kotlin-react-router-dom:5.1.2-pre.107-kotlin-1.3.72")
    }

    cocoapodsext {
        summary = "Common library for the KaMP starter kit"
        homepage = "https://github.com/touchlab/KaMPStarter"
        framework {
            isStatic = false
            export(Deps.kermit)
            transitiveExport = true
        }
    }
}
