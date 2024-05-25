plugins {
    `lifecycle-base`
}

tasks.named("clean") {
    dependsOn(gradle.includedBuild("apps").task(":clean"))
}

repositories {
    mavenCentral()
}
