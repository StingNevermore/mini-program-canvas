package com.nevermore.mpc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * @author nevermore
 * @since
 */
@SpringBootApplication(excludeName = ["com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration"])
class OperationApplication

fun main(args: Array<String>) {
    runApplication<OperationApplication>(*args)
}

