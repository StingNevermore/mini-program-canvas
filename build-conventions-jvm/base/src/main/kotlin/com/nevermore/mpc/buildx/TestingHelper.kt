package com.nevermore.mpc.buildx

import mpcbuild.basic.repoRoot
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

        dependsOn(rootProject.tasks.named("generateSqlAggregationFile"))
        systemProperty("sql.file.path", "${repoRoot()}/mpc-backend/build/sql")

        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}

