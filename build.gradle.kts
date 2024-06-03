import java.nio.file.Files

plugins {
    base
    java
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

fun registerLifecycleTaskDependencies(taskName: String) {
    tasks.getByName(taskName) {
        dependsOn(gradle.includedBuild("mpc-backend").task(":$taskName"))
        dependsOn(gradle.includedBuild("intellij-plugins").task(":$taskName"))
    }
}

registerLifecycleTaskDependencies("clean")
registerLifecycleTaskDependencies("build")
registerLifecycleTaskDependencies("assemble")

val backendTopLevelApps = listOf("apps/operation-api", "apps/templates-engine-api")

tasks.register<Copy>("distribution") {
    val paths = backendTopLevelApps.map {
        gradle.includedBuild("mpc-backend").projectDir.resolve("$it/build/libs")
    }

    paths.forEach {
        from(files(it)) {
            exclude("*-plain.jar")
        }
        into(layout.buildDirectory.dir("archive/backend-apps"))
    }
    dependsOn("assemble")
}

val localDockerDevEnvStorageFilesEnsure = tasks.register<Copy>("localDockerDevEnvStorageFilesEnsure") {
    val mysqlStorageDir = layout.buildDirectory.dir("localStorage/mysql").get().asFile
    if (!mysqlStorageDir.exists()) {
        Files.createDirectories(mysqlStorageDir.toPath())
    }
}

tasks.register<Exec>("localDockerComposeDevEnv") {
    executable = "docker"
    workingDir = projectDir.resolve("dev-tools/docker/compose")
    args = listOf("compose", "-f", "local-dev-env.yml", "up", "-d")
    dependsOn(localDockerDevEnvStorageFilesEnsure)
}

tasks.register<Exec>("shutdownLocalDockerComposeDevEnv") {
    executable = "docker"
    workingDir = projectDir.resolve("dev-tools/docker/compose")
    args = listOf("compose", "-f", "local-dev-env.yml", "down")
}

tasks.getByName<Delete>("clean") {
    layout.buildDirectory
        .asFile
        .orNull
        ?.listFiles()
        ?.filterNot { it.name.equals("localStorage") }
        ?.toSet()
        ?.let { delete = it }
}

tasks.register<Delete>("cleanLocalStorage") {
    delete = setOf(layout.buildDirectory.dir("localStorage"))
}
