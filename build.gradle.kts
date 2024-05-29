plugins {
    `lifecycle-base`
}

fun registerLifecycleTaskDependencies(taskName: String) {
    tasks.getByName(taskName) {
        dependsOn(gradle.includedBuild("mpc-backend").task(":apps:operation-api:$taskName"))
        dependsOn(gradle.includedBuild("mpc-backend").task(":apps:templates-engine-api:$taskName"))
    }
}

registerLifecycleTaskDependencies("clean")
registerLifecycleTaskDependencies("build")
