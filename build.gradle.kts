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
