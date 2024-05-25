plugins {
    id("java-platform")
}

group = "mpc-build.platform"

dependencies {
    constraints {
        api(versions.kotlin.plugin)
        api(versions.spring.boot.plugin)
        api(versions.kotlin.spring.plugin)
    }
}
