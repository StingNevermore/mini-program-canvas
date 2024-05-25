import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named

/**
 * @author nevermore
 * @since
 */
fun Project.configureTestingFramework() {
    tasks.named<Test>("test") {
        useJUnitPlatform()

        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

fun Project.mybatisTestingDependencies() {
    dependencies {
        add("runtimeOnly", "com.h2database:h2")
        add("testImplementation", "com.baomidou:mybatis-plus-boot-starter-test")
    }
}
