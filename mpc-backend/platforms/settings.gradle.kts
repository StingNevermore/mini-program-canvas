rootProject.name = "platforms"

include("test-platform")
include("product-platform")
include("plugin-platform")

dependencyResolutionManagement {
    versionCatalogs {
        create("versions") {
            from(files("../gradle/dep-versions.toml"))
        }
    }
}
