package com.nevermore.mpc.buildx

import java.io.File
import java.io.FileInputStream
import java.util.Properties
import javax.inject.Inject
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.services.BuildService
import org.gradle.api.services.BuildServiceParameters

/**
 * @author nevermore
 * @since
 */
abstract class BaseVersionsPropertiesBuildService @Inject constructor() :
    BuildService<BaseVersionsPropertiesBuildService.Params>, AutoCloseable {
    val properties: Properties

    init {
        val props = Properties()
        val workSpacePath = parameters.getWorkSpacePath().asFile.get()
        val propertiesFile = File(workSpacePath, "build-conventions/base-versions.properties")
        props.load(FileInputStream(propertiesFile))
        properties = props
    }

    interface Params : BuildServiceParameters {
        fun getWorkSpacePath(): RegularFileProperty
    }

    override fun close() {
    }
}

