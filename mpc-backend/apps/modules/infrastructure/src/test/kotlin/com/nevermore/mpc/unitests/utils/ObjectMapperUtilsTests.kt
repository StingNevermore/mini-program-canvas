package com.nevermore.mpc.unitests.utils

import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import org.junit.jupiter.api.Test
import com.nevermore.mpc.infra.utils.ObjectMapperUtils.fromJson
import com.nevermore.mpc.infra.utils.ObjectMapperUtils.fromPrefixYaml
import com.nevermore.mpc.infra.utils.ObjectMapperUtils.toJson

/**
 * @author nevermore
 * @since
 */
class ObjectMapperUtilsTests {

    @Test
    fun testToJson() {
        val data = mapOf("name" to "nevermore", "age" to 18, "gender" to "")
        assertEquals("{\"name\":\"nevermore\",\"age\":18,\"gender\":\"\"}", toJson(data))
    }

    @Test
    fun testFromJson() {
        val data = "{\"name\":\"nevermore\",\"age\":18,\"gender\":\"\"}"
        val map = fromJson(data, Map::class.java)
        assertEquals("nevermore", map["name"])
        assertEquals(18, map["age"])
        assertEquals("", map["gender"])
    }

    @Test
    fun testFromPrefixYaml() {
        val data = """
spring:        
    app:
        name: MyApp
        version: 1.0.0
    database:
        url: jdbc:mysql://localhost:3306/mydb
        user: root
        password: password
"""
        val map = fromPrefixYaml("spring.database", data, Map::class.java)
        assertNotNull(map?.get("user"))
        assertNotNull(map?.get("url"))
        assertNotNull(map?.get("password"))
        assertNull(map?.get("whatever"))

        assertNull(fromPrefixYaml("spring.whatever", data, Map::class.java))
    }

    @Test
    fun testFromPrefixYamlClassed() {
        val data = """
spring:        
    app:
        name: MyApp
        version: 1.0.0
    database:
        url: jdbc:mysql://localhost:3306/mydb
        user: root
        password: password
"""

        data class DataBaseConfig(val url: String?, val user: String?, val password: String?, val whatever: String?)

        val dataBaseConfig = fromPrefixYaml("spring.database", data, DataBaseConfig::class.java)
        assertNotNull(dataBaseConfig)
        assertNotNull(dataBaseConfig.url)
        assertNotNull(dataBaseConfig.user)
        assertNotNull(dataBaseConfig.password)
        assertNull(dataBaseConfig.whatever)
    }

}
