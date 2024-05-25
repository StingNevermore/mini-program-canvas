plugins {
    id("mpc-build.java-application")
    id("mpc-build.spring-boot-library")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
}
