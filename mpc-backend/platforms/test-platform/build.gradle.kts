plugins {
    id("java-platform")
}

group = "mpc-build.platform"

javaPlatform.allowDependencies()

dependencies {
    api(platform(versions.junit.bom))
    constraints {
        api(versions.mybatis.plus.test)
        api(versions.h2)
    }
}
