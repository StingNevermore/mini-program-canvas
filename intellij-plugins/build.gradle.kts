plugins {
    `lifecycle-base`
}
configureLifecycleTasks()

fun configureLifecycleTasks() {
    val lifecycleTasks = arrayOf("clean", "build", "assemble")

    lifecycleTasks.forEach { configureLifecycleTask(it) }
}

private fun configureLifecycleTask(taskName: String) {
    tasks.getByName(taskName) {
        subprojects.forEach { subproject ->
            subproject.afterEvaluate {
                tasks.findByName(taskName)?.let { dependsOn(it) }
            }
        }
    }
}
