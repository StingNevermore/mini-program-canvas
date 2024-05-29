package com.nevermore.mpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author nevermore
 * @since 0.0.1
 */
const val mybatisPlusAutoConfiguration = "com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration"

@SpringBootApplication(excludeName = [mybatisPlusAutoConfiguration])
class OperationApplication

fun main(args: Array<String>) {
    runApplication<OperationApplication>(*args)
}

