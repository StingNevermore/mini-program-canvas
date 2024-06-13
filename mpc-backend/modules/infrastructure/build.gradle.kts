import com.nevermore.mpc.buildx.officialStarter

plugins {
    id("mpc-build.spring-boot-library")
    id("mpc-build.kotlin-common")
    `java-test-fixtures`
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
    implementation(libs.commonsIo)
    implementation(libs.commonsLang3)


    testFixturesApi(libs.mybatisPlusTestStarter)
//    runtimeOnly(libs.h2)
    testFixturesApi(libs.testContainersMariaDB)
    testFixturesApi(libs.mariaDBDriver)
    testFixturesImplementation(officialStarter("test"))

    implementation(officialStarter("webflux"))

    implementation(libs.curatorRecipes)
    testImplementation(libs.curatorTest)
}
