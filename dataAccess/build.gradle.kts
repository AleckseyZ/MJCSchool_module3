plugins {
    id("RESTBasics.common-conventions")
}

dependencies {
    implementation("org.springframework:spring-jdbc:${Versions.springVer}")
    implementation("org.postgresql:postgresql:${Versions.postgreVer}")
    implementation("org.springframework:spring-orm:${Versions.springVer}")
    testImplementation("org.springframework:spring-test:${Versions.springVer}")
    testImplementation("io.zonky.test:embedded-postgres:${Versions.zonkyPostgres}")
    compileOnly("org.projectlombok:lombok:${Versions.lombokVer}")
	annotationProcessor("org.projectlombok:lombok:${Versions.lombokVer}")
	testCompileOnly("org.projectlombok:lombok:${Versions.lombokVer}")
	testAnnotationProcessor("org.projectlombok:lombok:${Versions.lombokVer}")
}