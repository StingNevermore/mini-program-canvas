import com.nevermore.mpc.buildx.officialStarter

plugins {
    id("mpc-build.spring-boot-api")
    id("mpc-build.kotlin-common")
}

dependencies {
    implementation(project(":infrastructure"))
    implementation(project(":components"))

    implementation(officialStarter("validation"))
}
