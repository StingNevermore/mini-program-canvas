plugins {
    id("java-platform")
}

group = "mpc-build.platform"

javaPlatform.allowDependencies()

dependencies {
    api(platform(versions.spring.boot.dependencies))
    constraints {
        api(versions.druid)
        api(versions.mybatis.plus)
        api(versions.kotlinx)
        api(versions.mysql)
        api(versions.guava)

        api(versions.jackson.core)
        api(versions.jackson.annotations)
        api(versions.jackson.databind)
        api(versions.jackson.datatype.jsr310)
        api(versions.jackson.datatype.guava)
        api(versions.jackson.datatype.jdk8)
        api(versions.jackson.dataformat.yaml)
        api(versions.jackson.module.kotlin)
        api(versions.jackson.module.parameter.names)
    }
}
