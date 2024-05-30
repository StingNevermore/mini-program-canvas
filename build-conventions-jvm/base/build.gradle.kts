plugins {
    `kotlin-dsl`
}

group = "mpcbuild"

dependencies {
    implementation(project(":common"))

    implementation(libs.kotlinGradlePlugin)
}
