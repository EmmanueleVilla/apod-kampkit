// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
        jcenter()
        google()
        maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    }
    dependencies {
        classpath(Deps.android_gradle_plugin)
        classpath(Deps.cocoapodsext)
        classpath("org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}")
        classpath(kotlin("gradle-plugin", Versions.kotlin))
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven(url = "https://kotlin.bintray.com/kotlinx")
        maven(url = "https://dl.bintray.com/ekito/koin")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://kotlin.bintray.com/kotlin-js-wrappers/")
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
    }
}
