import com.nevermore.mpc.buildx.configureTestingFramework
import com.nevermore.mpc.buildx.officialStarter

plugins {
    id("java")
    id("mpc-build.spring-boot-library")
}

dependencies {
    implementation(officialStarter("actuator"))
    implementation(officialStarter("web"))
}

configureTestingFramework()
