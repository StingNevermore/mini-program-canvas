plugins {
    id("mpc-build.spring-boot-api")
    id("mpc-build.kotlin-common")
}

dependencies {
    implementation(project(":modules:infrastructure"))
    implementation(project(":modules:components"))

}
