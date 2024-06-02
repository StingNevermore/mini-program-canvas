plugins {
    base
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

    paths.forEach  {
        from(files(it)) {
            exclude("*-plain.jar")
        }
        into(layout.buildDirectory.dir("archive/backend-apps"))
    }
    dependsOn("assemble")
}
