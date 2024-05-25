plugins {
    id("mpc-build.java-common")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(platform("mpc-build.platform:product-platform"))

    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    testImplementation(kotlin("test"))
}

kotlin {
    compilerOptions {
        javaParameters = true
    }
}
