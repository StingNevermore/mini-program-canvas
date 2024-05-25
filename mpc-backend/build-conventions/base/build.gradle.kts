plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(project(":common"))

    implementation(platform("mpc-build.platform:plugin-platform"))
    implementation("org.jetbrains.kotlin.jvm:org.jetbrains.kotlin.jvm.gradle.plugin")
    implementation("gradle.plugin.com.github.spotbugs.snom:spotbugs-gradle-plugin:4.7.5")

}
