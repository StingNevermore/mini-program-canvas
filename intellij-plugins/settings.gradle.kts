@file:Suppress("UnstableApiUsage")

rootProject.name = "intellij-plugins"

include("uniapp-helper")

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
