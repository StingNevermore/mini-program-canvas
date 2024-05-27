package com.nevermore.mpc.infra.configuration

import com.alibaba.druid.pool.DruidDataSource
import com.baomidou.mybatisplus.autoconfigure.SpringBootVFS
import com.baomidou.mybatisplus.autoconfigure.SqlSessionFactoryBeanCustomizer
import org.mybatis.spring.annotation.MapperScan
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
class MybatisConfiguration {

    @Bean
    fun dataSource(): DataSource {
        return DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:3306/mpc")
            .username("root")
            .password("123456")
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

