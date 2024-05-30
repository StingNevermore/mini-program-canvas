@file:Suppress("UnstableApiUsage")

rootProject.name = "mpc-backend"
include("operation-api")
include("templates-engine-api")
include("components")
include("infrastructure")

project(":infrastructure").projectDir = file("modules/infrastructure")
project(":components").projectDir = file("modules/components")
project(":operation-api").projectDir = file("apps/operation-api")
project(":templates-engine-api").projectDir = file("apps/templates-engine-api")

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    includeBuild("../build-conventions-jvm")
}

includeBuild("../build-conventions-jvm")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
