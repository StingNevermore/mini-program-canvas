plugins {
    id("java")
    id("mpc-build.spring-boot-base")
}

tasks {
    bootRun {
        enabled = false
    }
    bootBuildImage {
        enabled = false
    }
    bootJar {
        enabled = false
    }
    jar {
        enabled = true
    }
}
