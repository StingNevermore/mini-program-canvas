import mpcbuild.basic.repoRoot

plugins {
    `lifecycle-base`
}

group = "com.nevermore"
version = trimmedContentsOfFile("version.txt")

fun Project.trimmedContentsOfFile(path: String): String =
    providers.fileContents(repoRoot().file(path)).asText.get().trim()
