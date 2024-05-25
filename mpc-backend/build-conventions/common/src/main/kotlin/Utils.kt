import java.io.File
import org.gradle.api.invocation.Gradle

/**
 * @author nevermore
 * @since
 */
fun locateWorkspace(gradle: Gradle): File {
    return if (gradle.parent == null && gradle.rootProject.name == "mpc-backend") {
        gradle.rootProject.rootDir
    } else if (gradle.parent == null && gradle.rootProject.name != "mpc-backend") {
        gradle.rootProject.rootDir.parentFile
    } else {
        locateWorkspace(gradle.parent!!)
    }
}
