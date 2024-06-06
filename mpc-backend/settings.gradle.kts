@file:Suppress("UnstableApiUsage")

rootProject.name = "mpc-backend"
include("operation-api")
include("templates-engine-api")
include("components")
include("web-commons")
include("infrastructure")

project(":infrastructure").projectDir = file("modules/infrastructure")
project(":components").projectDir = file("modules/components")
project(":web-commons").projectDir = file("modules/web-commons")
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
