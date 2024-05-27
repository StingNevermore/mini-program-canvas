plugins {
    id("mpc-build.spring-boot-library")
    id("mpc-build.kotlin-common")
}

dependencies {
    implementation(libs.jacksonCore)
    implementation(libs.jacksonAnnotations)
    implementation(libs.jacksonDatabind)
    implementation(libs.jacksonDatatypeJsr310)
    implementation(libs.jacksonDatatypeGuava)
    implementation(libs.jacksonDatatypeJdk8)
    implementation(libs.jacksonDataformatYaml)
    implementation(libs.jacksonModuleParameterNames)
    implementation(libs.jacksonModuleKotlin)

    implementation(libs.guava)
    implementation(libs.mysql)
    implementation(libs.druid)
    implementation(libs.mybatisPlusStarter)
    implementation(libs.mybatisSpring)

    testImplementation(libs.mybatisPlusTestStarter)
    runtimeOnly(libs.h2)
}
