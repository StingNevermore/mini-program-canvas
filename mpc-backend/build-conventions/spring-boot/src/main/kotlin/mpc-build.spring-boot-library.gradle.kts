@file:Suppress("UnstableApiUsage")

plugins {
    id("mpc-build.java-library")
    id("org.springframework.boot")
    id("org.jetbrains.kotlin.plugin.spring")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")

    developmentOnly(platform("mpc-build.platform:product-platform"))

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

val profile: String? by project

tasks {
    processResources {
        expand("activeProfile" to (profile ?: "dev"))
    }

    disable(bootJar, bootRun, bootBuildImage, bootTestRun)
    enable(jar)
}

fun disable(vararg tasks: TaskProvider<out Task>) {
    tasks.forEach { it.get().enabled = false }
}

fun enable(vararg tasks: TaskProvider<out Task>) {
    tasks.forEach { it.get().enabled = true }
}
