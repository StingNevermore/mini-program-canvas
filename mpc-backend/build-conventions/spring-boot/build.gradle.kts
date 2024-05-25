plugins {
   `kotlin-dsl`
}

dependencies {
    implementation(platform("mpc-build.platform:plugin-platform"))
    implementation(project(":base"))
    implementation("org.springframework.boot:org.springframework.boot.gradle.plugin")
    implementation("org.jetbrains.kotlin.plugin.spring:org.jetbrains.kotlin.plugin.spring.gradle.plugin")
}
