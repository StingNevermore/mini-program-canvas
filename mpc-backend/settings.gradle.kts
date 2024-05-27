@file:Suppress("UnstableApiUsage")

rootProject.name = "mpc-backend"
include("apps:operation-api")
include("apps:templates-engine-api")
include("modules:components")
include("modules:infrastructure")

project(":modules:infrastructure").projectDir = file("modules/infrastructure")
project(":modules:components").projectDir = file("modules/components")
project(":apps:operation-api").projectDir = file("apps/operation-api")
project(":apps:templates-engine-api").projectDir = file("apps/templates-engine-api")

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
