package com.nevermore.mpc.buildx

/**
 * @author nevermore
 * @since
 */
fun springBoot(artifact: String) = "org.springframework.boot:$artifact"

fun officialStarter(artifact: String) = "org.springframework.boot:spring-boot-starter-${artifact}"

fun cloudStarter(artifact: String) = "org.springframework.cloud:spring-cloud-starter-${artifact}"
