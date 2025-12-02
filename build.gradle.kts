plugins {
    kotlin("jvm") version "2.3.0-RC2"
    kotlin("plugin.spring") version "2.3.0-RC2"
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.sonarqube") version "6.3.1.5724"
}

group = "panomete.project"
version = "0.0.1-SNAPSHOT"
description = "spb3kotlin"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {

    // monitoring
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // api lib
    implementation("org.springframework.boot:spring-boot-starter-web")

    // kotlin support
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // openapi lib
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${project.findProperty("springdocVersion")?.toString()}")

    // dev lib
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // service discovery client
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client:5.0.0")

    // test lib
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

sonar {
    properties {
        property("sonar.projectKey", project.findProperty("sonarProjectKey")?.toString() ?: "")
        property("sonar.host.url", project.findProperty("sonarHost")?.toString() ?: "")
        property("sonar.token", project.findProperty("sonarToken")?.toString() ?: "")
    }
}