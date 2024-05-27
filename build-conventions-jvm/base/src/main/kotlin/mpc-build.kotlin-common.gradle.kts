import com.nevermore.mpc.buildx.fromVersionCatalog

plugins {
    id("mpc-build.java-base")
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))

    implementation(fromVersionCatalog("kotlinxCoroutinesCore"))
}


kotlin {
    compilerOptions {
        javaParameters = true
    }
}
