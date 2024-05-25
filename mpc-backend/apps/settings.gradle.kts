@file:Suppress("UnstableApiUsage")

rootProject.name = "apps"

include("operation-api")
include("templates-engine-api")
include("modules")
include("modules:infrastructure")
include("modules:components")

includeBuild("../build-conventions")
includeBuild("../platforms")

pluginManagement {
    includeBuild("../build-conventions")
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("versions") {
            from(files("../gradle/dep-versions.toml"))
        }
    }
}
