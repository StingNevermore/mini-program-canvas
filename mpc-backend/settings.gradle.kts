rootProject.name = "mpc-backend"

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

includeBuild("build-conventions")
includeBuild("platforms")
includeBuild("apps")
