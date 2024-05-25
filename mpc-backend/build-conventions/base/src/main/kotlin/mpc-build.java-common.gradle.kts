import org.gradle.jvm.toolchain.JvmVendorSpec.ADOPTIUM

plugins {
    id("mpc-build.base")
    `java-base`
    idea
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
        vendor = ADOPTIUM
    }
}

idea {
    module {
        isDownloadSources = true
    }
}

tasks.withType<JavaCompile> {
    options.apply {
        release = 17
        compilerArgs.add("-parameters")
    }
}


