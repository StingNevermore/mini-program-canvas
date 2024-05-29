package com.nevermore.mpc.infra.utils

import java.io.File
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule

/**
 * @author nevermore
 * @since
 */
object ObjectMapperUtils {
    @JvmStatic
    private val jsonMapper = ObjectMapper().apply { registerCommonModules() }

    @JvmStatic
    private val yamlMapper = ObjectMapper(YAMLFactory()).apply { registerCommonModules() }

    @JvmStatic
    fun toJson(obj: Any): String = jsonMapper.writeValueAsString(obj)

    @JvmStatic
    fun <T> fromJson(str: String, klass: Class<T>): T = jsonMapper.readValue(str, klass)

    @JvmStatic
    fun <T> fromPrefixYaml(prefix: String, str: String, klass: Class<T>): T? {
        val rootNode = yamlMapper.readTree(str)
        return fromPrefixYaml(prefix, rootNode, klass)
    }

    @JvmStatic
    fun <T> fromPrefixYaml(prefix: String, file: File, klass: Class<T>): T? {
        val rootNode = yamlMapper.readTree(file)
        return fromPrefixYaml(prefix, rootNode, klass)
    }

    @JvmStatic
    fun <T> fromYaml(str: String, klass: Class<T>): T? = yamlMapper.readValue(str, klass)

    private fun <T> fromPrefixYaml(prefix: String, rootNode: JsonNode, klass: Class<T>): T? {
        val prefixes = prefix.trim().takeIf { it.isNotEmpty() }
            ?.split(".")
            ?.map { it.trim() }
            ?.filter { it.isNotBlank() }

        var jsonNode = rootNode
        prefixes?.forEach {
            jsonNode = jsonNode.path(it)
            if (jsonNode.isMissingNode) {
                return@fromPrefixYaml null
            }
        }

        return yamlMapper.treeToValue(jsonNode, klass)
    }

    private fun ObjectMapper.registerCommonModules() {
        registerModule(JavaTimeModule())
        registerModule(
            KotlinModule.Builder()
                .configure(KotlinFeature.NullToEmptyCollection, true)
                .configure(KotlinFeature.NullToEmptyMap, true)
                .build()
        )
    }

}
