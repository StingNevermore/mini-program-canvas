import org.gradle.kotlin.dsl.DependencyHandlerScope

/**
 * @author nevermore
 * @since
 */
fun DependencyHandlerScope.commonDependencies() {
    add("implementation", platform("mpc-build.platform:product-platform"))
    add("annotationProcessor", platform("mpc-build.platform:product-platform"))
    add("testImplementation", platform("mpc-build.platform:test-platform"))

    add("testImplementation", "org.junit.jupiter:junit-jupiter")
    add("testImplementation", "org.junit.platform:junit-platform-launcher")
}

fun DependencyHandlerScope.jacksonDependencies() {
    add("implementation", "com.fasterxml.jackson.core:jackson-core")
    add("implementation", "com.fasterxml.jackson.core:jackson-annotations")
    add("implementation", "com.fasterxml.jackson.core:jackson-databind")

    add("implementation", "com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    add("implementation", "com.fasterxml.jackson.datatype:jackson-datatype-guava")
    add("implementation", "com.fasterxml.jackson.datatype:jackson-datatype-jdk8")

    add("implementation", "com.fasterxml.jackson.dataformat:jackson-dataformat-yaml")
    add("implementation", "com.fasterxml.jackson.module:jackson-module-parameter-names")
    add("implementation", "com.fasterxml.jackson.module:jackson-module-kotlin")
    
}
