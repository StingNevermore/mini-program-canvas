plugins {
    id("mpc-build.spring-boot-library")
    id("mpc-build.kotlin-common")
}
repositories {
    mavenCentral()
    google()
}

dependencies {
    implementation("com.baomidou:mybatis-plus-boot-starter")
    implementation("com.alibaba:druid-spring-boot-starter")
    implementation("com.mysql:mysql-connector-j")

    implementation("com.google.guava:guava:33.1.0-jre")

    mybatisTestingDependencies()
    jacksonDependencies()
}
