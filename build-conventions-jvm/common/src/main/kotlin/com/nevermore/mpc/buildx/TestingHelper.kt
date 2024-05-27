package com.nevermore.mpc.buildx

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.named

/**
 * @author nevermore
 * @since
 */
fun Project.configureTestingFramework() {
    dependencies {
        add("testImplementation", fromVersionCatalog("junitJupiter"))
        add("testImplementation", fromVersionCatalog("junitPlatformLauncher"))
    }

    tasks.named<Test>("test") {
        useJUnitPlatform()

        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

