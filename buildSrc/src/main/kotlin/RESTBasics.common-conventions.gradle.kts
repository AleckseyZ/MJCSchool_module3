plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate:hibernate-core:${Versions.hibernateVer}")
    implementation("org.springframework:spring-core:${Versions.springVer}")
    implementation("org.springframework:spring-beans:${Versions.springVer}")
    implementation("org.springframework:spring-context:${Versions.springVer}")
    implementation("org.springframework.hateoas:spring-hateoas:${Versions.springHateoasVer}")
    testImplementation("org.junit.jupiter:junit-jupiter:${Versions.junitVer}")
}

tasks.test {
    useJUnitPlatform()
}