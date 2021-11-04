plugins {
    id("RESTBasics.common-conventions")
}

dependencies {
    testImplementation("org.mockito:mockito-core:${Versions.mockitoVer}")
    testImplementation("org.mockito:mockito-junit-jupiter:${Versions.mockitoVer}")
    implementation(project(":dataAccess"))
}