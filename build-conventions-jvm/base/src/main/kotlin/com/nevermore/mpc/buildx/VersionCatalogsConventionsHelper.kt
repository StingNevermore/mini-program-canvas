package com.nevermore.mpc.buildx

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.get

/**
 * @author nevermore
 * @since
 */
fun Project.fromVersionCatalog(name: String): Provider<MinimalExternalModuleDependency> {
    val versionCatalogs = extensions["versionCatalogs"] as VersionCatalogsExtension
    val dependency = versionCatalogs.named("libs")
        .findLibrary(name)
    return dependency.orElseThrow { GradleException("Could not find library $name") }
}
