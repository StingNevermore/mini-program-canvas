@file:Suppress("UnstableApiUsage")

import com.nevermore.mpc.buildx.configureTestingFramework
import com.nevermore.mpc.buildx.fromVersionCatalog
import com.nevermore.mpc.buildx.officialStarter


plugins {
    id("java-library")
    id("mpc-build.java-base")
    id("org.springframework.boot")
    id("org.jetbrains.kotlin.plugin.spring")
}

dependencies {
    implementation(platform(fromVersionCatalog("springCloudDependencies")))
    implementation(platform(fromVersionCatalog("springBootDependencies")))
    annotationProcessor(platform(fromVersionCatalog("springBootDependencies")))
    developmentOnly(platform(fromVersionCatalog("springBootDependencies")))

    implementation(com.nevermore.mpc.buildx.springBoot("spring-boot-starter"))

    developmentOnly(com.nevermore.mpc.buildx.springBoot("spring-boot-devtools"))
    annotationProcessor(com.nevermore.mpc.buildx.springBoot("spring-boot-configuration-processor"))
    testImplementation(officialStarter("test"))
}

configureTestingFramework()

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
}
