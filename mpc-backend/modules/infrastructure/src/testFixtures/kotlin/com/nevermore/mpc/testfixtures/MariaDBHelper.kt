package com.nevermore.mpc.testfixtures

import com.nevermore.mpc.infra.utils.KtLazyHelper.orNull
import org.springframework.test.context.DynamicPropertyRegistry
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.utility.DockerImageName
import org.testcontainers.utility.MountableFile
import java.lang.Runtime.getRuntime


/**
 * @author nevermore
 * @since
 */
object MariaDBHelper {

    @JvmStatic
    val mariaDBContainer: MariaDBContainer<out MariaDBContainer<*>> by lazy(::crateMariaDBContainer)

    private fun crateMariaDBContainer() =
        MariaDBContainer(DockerImageName.parse("mariadb:11.4")).also { it.withDatabaseName("mpc") }
            .also {
                it.withCopyToContainer(
                    MountableFile.forHostPath(
                        System.getProperty("sql.file.path")
                    ), "/docker-entrypoint-initdb.d/"
                )
            }
            .also { it.start() }
            .also { getRuntime().addShutdownHook(Thread { ::mariaDBContainer.orNull?.stop() }); }

    @JvmStatic
    fun configureDatasourceProperties(registry: DynamicPropertyRegistry) {
        registry.add("spring.datasource.url") { mariaDBContainer.jdbcUrl }
        registry.add("spring.datasource.username") { mariaDBContainer.username }
        registry.add("spring.datasource.password") { mariaDBContainer.password }
    }
}
