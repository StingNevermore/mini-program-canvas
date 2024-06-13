package com.nevermore.mpc.infra.configuration

import com.alibaba.druid.pool.DruidDataSource
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS
import com.baomidou.mybatisplus.autoconfigure.SqlSessionFactoryBeanCustomizer
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import javax.sql.DataSource

/**
 * @author nevermore
 * @since
 */
@Configuration
@Suppress("SpringComponentScan")
@MapperScan("com.nevermore.mpc.**.dao")
class MybatisConfiguration(
    @Value("\${spring.datasource.url:#{null}}") val datasourceUrl: String?,
    @Value("\${spring.datasource.username:#{null}}") val datasourceUsername: String?,
    @Value("\${spring.datasource.password:#{null}}") val datasourcePassword: String?,
) {


    @Bean
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
            .url(datasourceUrl ?: "jdbc:mysql://localhost:3306/mpc")
            .username(datasourceUsername ?: "root")
            .password(datasourcePassword ?: "123456")
            .type(DruidDataSource::class.java)
            .build()
    }

    @Bean
    fun sqlSessionFactoryBeanCustomizer() = SqlSessionFactoryBeanCustomizer {
        val mapperLocations = PathMatchingResourcePatternResolver()
            .getResources("classpath*:/mappers/**/*.xml")
        it.addMapperLocations(*mapperLocations)
        it.vfs = SpringBootVFS::class.java
    }
}

