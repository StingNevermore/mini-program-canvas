import com.nevermore.mpc.buildx.configureTestingFramework
import com.nevermore.mpc.buildx.officialStarter

plugins {
    id("java")
    id("mpc-build.spring-boot-base")
}

dependencies {
    implementation(officialStarter("actuator"))
    implementation(officialStarter("web"))
}

configureTestingFramework()
