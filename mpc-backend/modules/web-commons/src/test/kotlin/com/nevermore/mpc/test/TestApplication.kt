package com.nevermore.mpc.test

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author nevermore
 * @since
 */
@SpringBootApplication(scanBasePackages = ["com.nevermore.mpc"])
class TestApplication

fun main() {
    runApplication<TestApplication>()
}
