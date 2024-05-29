package com.nevermore.mpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author nevermore
 * @since
 */
const val excludePackage = "com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration"

@SpringBootApplication(excludeName = [excludePackage])
class OperationApplication

fun main(args: Array<String>) {
    runApplication<OperationApplication>(*args)
}

