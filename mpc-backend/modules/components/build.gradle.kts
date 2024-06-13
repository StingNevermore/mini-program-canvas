plugins {
    id("mpc-build.spring-boot-library")
    id("mpc-build.kotlin-common")
}

dependencies {
    implementation(project(":infrastructure"))

    implementation(libs.mybatisPlusStarter)
    implementation(libs.mybatisSpring)

    testImplementation(libs.mybatisPlusTestStarter)
    testImplementation(testFixtures(project(":infrastructure")))
    runtimeOnly(libs.h2)
}
