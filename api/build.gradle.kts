plugins {
    java
    id("org.springframework.boot") version "2.5.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.hibernate:hibernate-core:${Versions.hibernateVer}")
    implementation ("org.springframework.boot:spring-boot-starter-hateoas")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")
    implementation(project(":service"))
    implementation(project(":dataAccess"))
    compileOnly("org.projectlombok:lombok:${Versions.lombokVer}")
	annotationProcessor("org.projectlombok:lombok:${Versions.lombokVer}")
}