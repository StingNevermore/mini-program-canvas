plugins {
    `kotlin-dsl`
}


dependencies {
    implementation(project(":base"))
    implementation(project(":common"))

    implementation(libs.springBootPlugin)
    implementation(libs.kotlinSpringPlugin)
}
