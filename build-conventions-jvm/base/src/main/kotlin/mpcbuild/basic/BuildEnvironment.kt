package mpcbuild.basic

import org.gradle.api.Project
import org.gradle.api.file.Directory

/**
 * @author nevermore
 * @since 0.0.1
 */
fun Project.repoRoot() = layout.projectDirectory.parentOrRoot()

fun Directory.parentOrRoot(): Directory = if (this.file("version.txt").asFile.exists()) {
    this
} else {
    val parent = dir("..")
    when {
        parent.file("version.txt").asFile.exists() -> parent
        this == parent -> throw IllegalStateException("Cannot find 'version.txt' file in root of repository")
        else -> parent.parentOrRoot()
    }
}
