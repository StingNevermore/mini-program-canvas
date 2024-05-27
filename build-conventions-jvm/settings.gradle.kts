@file:Suppress("UnstableApiUsage")

rootProject.name = "build-conventions-jvm"

include(":base")
include(":common")
include(":spring-boot")

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
