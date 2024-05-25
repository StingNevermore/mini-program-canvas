import com.nevermore.mpc.buildx.BaseVersionsPropertiesBuildService

plugins {
    `lifecycle-base`
}

repositories {
    mavenCentral()
}

val serviceProvider: Provider<BaseVersionsPropertiesBuildService> =
    project.gradle.sharedServices.registerIfAbsent("versions", BaseVersionsPropertiesBuildService::class.java) {
        parameters.getWorkSpacePath().set(locateWorkspace(gradle))
    }

project.extensions.add("baseVersions", serviceProvider.get().properties)

group = "com.nevermore"
version = serviceProvider.get().properties.getProperty("mpc-version")
description = "canvas for mini program, based on uniapp"
