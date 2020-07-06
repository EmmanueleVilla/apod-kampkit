val kotlinVersion = "1.3.71"
val serializationVersion = "0.20.0"
val ktorVersion = "1.3.2"

plugins {
    application
    kotlin("jvm")
    id("kotlinx-serialization")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":shared"))
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation(kotlin("stdlib", kotlinVersion)) // or "stdlib-jdk8"
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion") // JVM dependency
    implementation("io.ktor:ktor-websockets:$ktorVersion")
    implementation("org.litote.kmongo:kmongo-coroutine-serialization:3.12.2")
    implementation("io.ktor:ktor-serialization:$ktorVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:$serializationVersion") // JVM dependency
    implementation(kotlin("stdlib", Versions.kotlin))
    implementation(Deps.kermitJvm)
    implementation(Deps.Ktor.jvmCore)
    implementation(Deps.Ktor.jvmJson)
    implementation(Deps.Ktor.jvmLogging)
    implementation(Deps.Coroutines.jdk)
    implementation(Deps.Ktor.androidSerialization)
    implementation(Deps.Ktor.androidCore)
}

application {
    mainClassName = "com.shadowings.apodkmp.MainKt"
}

tasks.getByName<JavaExec>("run") {
    classpath(tasks.getByName<Jar>("jar")) // so that the JS artifacts generated by `jvmJar` can be found and served
}

tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}
