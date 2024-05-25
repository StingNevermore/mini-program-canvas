@file:Suppress("UnstableApiUsage")

rootProject.name = "build-conventions"

include("base")
include("common")
include("spring-boot")

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}
