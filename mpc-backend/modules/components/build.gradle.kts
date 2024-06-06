plugins {
    id("mpc-build.spring-boot-library")
    id("mpc-build.kotlin-common")
}

dependencies {
    implementation(project(":infrastructure"))
}
